package universalelectricity.electricity;

import net.minecraft.src.Block;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.extend.IElectricUnit;

/**
 * An easier way to implement the methods with default values set.
 * @author Calclavia
 *
 */
public abstract class TileEntityElectricUnit extends TileEntity implements IElectricUnit
{
    protected int disabledTicks = 0;

    public TileEntityElectricUnit()
    {
        ElectricityManager.instance.registerElectricUnit(this);
    }

    @Override
    public void onDisable(int duration)
    {
        this.disabledTicks = duration;
    }

    @Override
    public boolean isDisabled()
    {
        return this.disabledTicks > 0;
    }

    /**
     * Called every tick. Super this!
     */
    @Override
    public void onUpdate(float watts, float voltage, ForgeDirection side)
    {
        if (this.disabledTicks > 0)
        {
            this.disabledTicks -= this.getTickInterval();
            return;
        }
    }

    @Override
    public boolean canConnect(ForgeDirection side)
    {
        return this.canReceiveFromSide(side);
    }

    @Override
    public float getVoltage()
    {
        return 120;
    }

    /**
     * How many world ticks there should be before this tile entity gets ticked?
     * E.x Returning "1" will make this tile entity tick every single tick.
     * @return - The tick intervals. Returns 0 if you wish it to not tick at all.
     */
    @Override
    public int getTickInterval()
    {
        return 1;
    }

    /**
     * Determines if this TileEntity requires update calls.
     * A UE TileEntity DOES NOT need update calls because the updates will be called
     * via the ElectricityManager reducing lag.
     * @return True if you want updateEntity() to be called, false if not
     */
    @Override
    public boolean canUpdate()
    {
        return false;
    }
    
    @Override
    public int getBlockMetadata()
    {
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }
    
    @Override
    public Block getBlockType()
    {
        if (this.blockType == null)
        {
            this.blockType = Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord)];
        }

        return this.blockType;
    }
}
