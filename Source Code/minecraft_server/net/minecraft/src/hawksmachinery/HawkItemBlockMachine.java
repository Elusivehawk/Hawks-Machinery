
package net.minecraft.src.hawksmachinery;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

/**
 * @author Elusivehawk
 *
 */
public class HawkItemBlockMachine extends ItemBlock implements ITextureProvider
{
	public static mod_HawksMachinery BASEMOD;
	
	public HawkItemBlockMachine(int id)
    {
	    super(id);
	    setMaxDamage(0);
	    setHasSubtypes(true);
    }
	
	@Override
	public int getBlockID()
	{
		return BASEMOD.blockEmptyMachine.blockID;
	}
	
	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}
	
	@Override
	public String getTextureFile()
	{
		return HawkManager.BLOCK_TEXTURE_FILE;
	}
	
	@Override
	public void onCreated(ItemStack item, World world, EntityPlayer player)
	{
		int dmg = item.getItemDamage();
				
		if (dmg == 0)
		{
			player.addStat(HawkAchievements.shellOfAMachine, 1);
		}
		
		if (dmg <= 3 && dmg != 0)
		{
			player.addStat(HawkAchievements.buildABetterMachineBlock, 1);
		}
		
		if (dmg <= 6 && dmg > 3)
		{
			player.addStat(HawkAchievements.redstonedWithCare, 1);
		}
	}
	
}
