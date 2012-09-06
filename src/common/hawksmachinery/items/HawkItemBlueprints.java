
package hawksmachinery.items;

import hawksmachinery.HawksMachinery;
import java.util.List;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HawkItemBlueprints extends Item
{
	public static HawksMachinery BASEMOD;
	public static String[] en_USNames = {"Crusher", "Washer", "Compressor", "Cutter", 
										"Bottler", "Metal Lathe", "Drill Tank", "Mining Well", 
										"Induction Furnace", "Auto-Table", "Welding Torch", "HM-E2MM"};
	
	public HawkItemBlueprints(int id)
	{
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
		setContainerItem(this);
		setTabToDisplayOn(CreativeTabs.tabMisc);
		setMaxStackSize(1);
		
	}
	
	@Override
	public int getIconFromDamage(int dmg)
	{
		return 188;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack item)
	{
		if (item.getItemDamage() == 11)
		{
			return EnumRarity.rare;
		}
		
		return EnumRarity.uncommon;
	}
	
	@Override
	public boolean hasEffect(ItemStack item)
	{
		if (item.getItemDamage() == 11)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs tabs, List itemList)
	{
		for (int counter = 0; counter <= 11; ++counter)
		{
			itemList.add(new ItemStack(this, 1, counter));
		}
	}
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack item)
	{
		return false;
	}
	
	@Override
	public String getItemNameIS(ItemStack item)
	{
		return en_USNames[item.getItemDamage()].toLowerCase() + "Blueprint";
	}
	
}