
package hawksmachinery.items;

import java.util.List;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumRarity;
import net.minecraft.src.ItemStack;
import hawksmachinery.api.HMRepairInterfaces.IHMRivet;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HMItemRivets extends HMItem implements IHMRivet
{
	public HMItemRivets(int id)
	{
		super(id);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabMaterials);
		//this.en_USNames = new String[]{"Copper", "Bronze", "Iron", "Steel", "Gold", "Endium", "Cobalt"};
		
	}
	
	@Override
	public int getIconFromDamage(int dmg)
	{
		return 41 + dmg;
	}
	
	public int getRepairAmount(ItemStack rivet)
	{
		switch (rivet.getItemDamage())
		{
			case 0: return 2;
			case 1: return 4;
			case 2: return 4;
			case 3: return 5;
			case 4: return 3;
			case 5: return 10;
			case 6: return 6;
			default: return 0;
		}
		
	}
	
	@Override
	public String getItemNameIS(ItemStack item)
	{
		switch (item.getItemDamage())
		{
			case 0: return "copperRivet";
			case 1: return "bronzeRivet";
			case 2: return "ironRivet";
			case 3: return "steelRivet";
			case 4: return "goldRivet";
			case 5: return "endiumRivet";
			case 6: return "cobaltRivet";
			default: return null;
		}
		
	}
	
	@Override
	public boolean hasEffect(ItemStack item)
	{
		return item.getItemDamage() == 5;
	}

	@Override
	public EnumRarity getRarity(ItemStack item)
	{
		return item.getItemDamage() == 5 ? EnumRarity.rare : EnumRarity.common;
	}
	
}
