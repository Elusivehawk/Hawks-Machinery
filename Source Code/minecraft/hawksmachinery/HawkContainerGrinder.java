/**
 * 
 */
package hawksmachinery;

import net.minecraft.src.*;
import net.minecraft.src.universalelectricity.*;
import net.minecraft.src.universalelectricity.components.*;

/**
 * @author Elusivehawk
 *
 */
public class HawkContainerGrinder extends Container
{
	private static HawkTileEntityGrinder tileEntity;
	
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}
	
    public HawkContainerGrinder(InventoryPlayer par1InventoryPlayer, HawkTileEntityGrinder tileEntity)
    {
        this.tileEntity = tileEntity;
        this.addSlot(new SlotElectricItem(tileEntity, 0, 55, 49));
        this.addSlot(new Slot(tileEntity, 1, 55, 25));
        this.addSlot(new SlotProcessorsOutput(par1InventoryPlayer.player, tileEntity, 2, 108, 25));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlot(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlot(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }
    
    public static TileEntity getGrinderContainer()
    {
    	return tileEntity;
    }
    
    public ItemStack transferStackInSlot(int par1)
    {
		return null;
    }

}
