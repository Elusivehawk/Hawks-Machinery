package universalelectricity.basiccomponents;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import universalelectricity.electricity.ElectricityManager;
import universalelectricity.electricity.TileEntityElectricUnit;
import universalelectricity.extend.IItemElectric;
import universalelectricity.network.IPacketReceiver;
import universalelectricity.network.PacketManager;

import com.google.common.io.ByteArrayDataInput;

public class TileEntityElectricFurnace extends TileEntityElectricUnit implements IInventory, ISidedInventory,  IPacketReceiver
{
    //The amount of ticks requried to smelt this item
    public final int smeltingTimeRequired = 180;

    //How many ticks has this item been smelting for?
    public int smeltingTicks = 0;

    public float electricityStored = 0;

    //Per Tick
    public final float electricityRequired = 4;

    /**
    * The ItemStacks that hold the items currently being used in the battery box
    */
    private ItemStack[] containingItems = new ItemStack[3];
    
    private boolean sendPacketToClients = false;

    @Override
    public float electricityRequest()
    {
        if (!this.isDisabled() && this.canSmelt())
        {
            return Math.max(0, this.electricityRequired - this.electricityStored);
        }

        return 0;
    }

    public boolean canReceiveFromSide(ForgeDirection side)
    {
        return side == ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
    }

    @Override
    public void onUpdate(float watts, float voltage, ForgeDirection side)
    {
        super.onUpdate(watts, voltage, side);

        if (voltage > this.getVoltage())
        {
            this.worldObj.createExplosion((Entity)null, this.xCoord, this.yCoord, this.zCoord, 1F);
        }

        //The bottom slot is for portable batteries
        if (this.containingItems[0] != null)
        {
            if (this.containingItems[0].getItem() instanceof IItemElectric)
            {
                IItemElectric electricItem = (IItemElectric)this.containingItems[0].getItem();

                if (electricItem.canProduceElectricity())
                {
                    double receivedElectricity = electricItem.onUseElectricity(electricItem.getTransferRate(), this.containingItems[0]);
                    this.electricityStored += receivedElectricity;
                }
            }
        }

        this.electricityStored += watts;

        if (this.electricityStored >= this.electricityRequired && !this.isDisabled())
        {
            //The left slot contains the item to be smelted
            if (this.containingItems[1] != null && this.canSmelt() && this.smeltingTicks == 0)
            {
                this.smeltingTicks = this.smeltingTimeRequired;
            }

            //Checks if the item can be smelted and if the smelting time left is greater than 0, if so, then smelt the item.
            if (this.canSmelt() && this.smeltingTicks > 0)
            {
                this.smeltingTicks -= this.getTickInterval();

                //When the item is finished smelting
                if (this.smeltingTicks < 1 * this.getTickInterval())
                {
                    this.smeltItem();
                    this.smeltingTicks = 0;
                }
            }
            else
            {
                this.smeltingTicks = 0;
            }

            this.electricityStored = 0;
        }
        
        PacketManager.sendTileEntityPacketWithRange(this, "BasicComponents", 15, (int)1,this.smeltingTicks, this.disabledTicks);
    }
    
    @Override
	public void handlePacketData(NetworkManager network, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) 
	{
		try
        {
  			int ID = dataStream.readInt();
			
  			if(ID == -1)
			{
				this.sendPacketToClients = dataStream.readBoolean();
			}
			else if(ID == 1)
			{
				this.smeltingTicks = dataStream.readInt();
	            this.disabledTicks = dataStream.readInt();
			}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}

    //Check all conditions and see if we can start smelting
    public boolean canSmelt()
    {
        if (FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[1]) == null)
        {
            return false;
        }

        if (this.containingItems[1] == null)
        {
            return false;
        }

        if (this.containingItems[2] != null)
        {
            if (!this.containingItems[2].isItemEqual(FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[1])))
            {
                return false;
            }

            if (this.containingItems[2].stackSize + 1 > 64)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack resultItemStack = FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[1]);

            if (this.containingItems[2] == null)
            {
                this.containingItems[2] = resultItemStack.copy();
            }
            else if (this.containingItems[2].isItemEqual(resultItemStack))
            {
                this.containingItems[2].stackSize ++;
            }

            this.containingItems[1].stackSize --;

            if (this.containingItems[1].stackSize <= 0)
            {
                this.containingItems[1] = null;
            }
        }
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.smeltingTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.containingItems = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.containingItems.length)
            {
                this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }
    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.smeltingTicks);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.containingItems.length; ++var3)
        {
            if (this.containingItems[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.containingItems[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }

    @Override
    public int getStartInventorySide(ForgeDirection side)
    {
        if(side == side.DOWN || side == side.UP)
        {
            return side.ordinal();
        }

        return 2;
    }
    
    @Override
    public int getSizeInventorySide(ForgeDirection side)
    {
        return 1;
    }
    
    @Override
    public int getSizeInventory()
    {
        return this.containingItems.length;
    }
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.containingItems[par1];
    }
    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.containingItems[par1] != null)
        {
            ItemStack var3;

            if (this.containingItems[par1].stackSize <= par2)
            {
                var3 = this.containingItems[par1];
                this.containingItems[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.containingItems[par1].splitStack(par2);

                if (this.containingItems[par1].stackSize == 0)
                {
                    this.containingItems[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.containingItems[par1] != null)
        {
            ItemStack var2 = this.containingItems[par1];
            this.containingItems[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.containingItems[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName()
    {
        return "Electric Furnace";
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openChest() { }

    @Override
    public void closeChest() { }

    @Override
    public float getVoltage()
    {
        return 120F;
    }

    @Override
    public int getTickInterval()
    {
    	if(!this.worldObj.isRemote)
    	{
            return 3;
    	}
    	
        return 0;
    }
}
