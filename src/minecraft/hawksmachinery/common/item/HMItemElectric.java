
package hawksmachinery.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import universalelectricity.prefab.ItemElectric;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HMItemElectric extends ItemElectric
{
	public HMItemElectric(int id)
	{
		super(id);
		
	}
	
	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		super.onCreated(item, world, player);
		item.stackTagCompound = new NBTTagCompound();
		
	}
	
	@Override
	public double getMaxJoules(Object... data)
	{
		return 0;
	}
	
	@Override
	public double getVoltage()
	{
		return 120;
	}
	
}
