
package hawksmachinery;

import java.util.*;
import net.minecraft.src.*;


/**
 * @author Elusivehawk
 *
 * @notice PROCESSING TYPES:
 * 1 - GRINDING
 * 2 - WASHING
 */
public class HawkProcessingRecipes
{
	private static Map grinderRecipes = new HashMap();
	private static Map grinderExplosives = new HashMap();
	
	private static Map washerRecipes = new HashMap();
	private static Map washerSecondaries = new HashMap();
	private static Map washerRarities = new HashMap();
	
	/**
	 * Adds a processing recipe.
	 * 
	 * Args:
	 * 
	 * Input
	 * Output
	 * Processing Type
	 */
	public static void addHawkProcessingRecipe(int input, ItemStack output, int processingType)
	{
		switch (processingType)
		{
			case 1: grinderRecipes.put(Arrays.asList(input, 0), output);
			case 2: washerRecipes.put(Arrays.asList(input, 0), output);
		}
	}
	
	/**
	 * Metadata-sensitive processing function.
	 *
	  */
	
	public static void addHawkMetaProcessingRecipe(int input, int inputMetadata, ItemStack output, int processingType)
	{
		switch (processingType)
		{
			case 1: grinderRecipes.put(Arrays.asList(input, inputMetadata), output);
			case 2: washerRecipes.put(Arrays.asList(input, inputMetadata), output);
		}
	}
	
	/**
	 * Adds an explosive item that explodes a machine when processed in a certain way.
	 * 
	 * @param explosive The item/block that explodes once processed.
	 * @param processingType Determines what machine explodes when processing said item/block.
	 */
	public static void addHawkExplosive(int explosive, int processingType)
	{
		switch (processingType)
		{
			case 1: grinderExplosives.put(Arrays.asList(explosive, 0), new Object[]{});
		}
	}
	
	/**
	 * Same as above, but metadata sensitive in order to facilitate ICBM support.
	 * 
	 * @param explosive
	 * @param explosiveDmg
	 * @param processingType
	 */
	public static void addHawkMetaExplosive(int explosive, int explosiveDmg, int processingType)
	{
		switch (processingType)
		{
			case 1: grinderExplosives.put(Arrays.asList(explosive, explosiveDmg), new Object[]{});
		}
	}
	
	public static void addHawkWashingSecondary(int input, int inputMetadata, Object output, boolean isCommon)
	{
		if (isCommon)
		{
			washerSecondaries.put(Arrays.asList(input, inputMetadata), output);
		}
		else
		{
			washerRarities.put(Arrays.asList(input, inputMetadata), output);
		}
	}
	
	public static Map getGrindingList()
	{
		return grinderRecipes;
	}
	
	public static Map getGrindingExplosivesList()
	{
		return grinderExplosives;
	}
	
	public static void reportRecipes()
	{
		System.out.println(getGrindingList().size() + " Hawk's Machinery recipes");
		System.out.println(getGrindingExplosivesList().size() + " Hawk's Machinery explosives");
		
	}
	
	public static ItemStack getGrindingResult(ItemStack item)
	{
		if (item == null)
		{
			return null;
		}
		else
		{
			ItemStack ret = (ItemStack)grinderRecipes.get(Arrays.asList(item.itemID, item.getItemDamage()));
			
			if (ret != null) 
			{
				return ret;
			}
			
			return null;
		}
	}
	
	public static ItemStack getWashingResult(ItemStack item)
	{
		if (item == null)
		{
			return null;
		}
		else
		{
			ItemStack ret = (ItemStack)washerRecipes.get(Arrays.asList(item.itemID, item.getItemDamage()));
			
			if (ret != null) 
			{
				return ret;
			}
			
			return null;
		}
	}
	
	public static ItemStack getWashingSecondaryResult(ItemStack item, Random random)
	{
		int secondaryChance = random.nextInt(100);
		
		if (item == null)
		{
			return null;
		}
		else
		{
			if (secondaryChance < 5)
			{
				ItemStack ret = (ItemStack)washerRarities.get(Arrays.asList(item.itemID, item.getItemDamage()));
				
				if (ret != null) 
				{
					return ret;
				}
			}
			else if (secondaryChance < 10)
			{
				ItemStack ret = (ItemStack)washerSecondaries.get(Arrays.asList(item.itemID, item.getItemDamage()));
				
				if (ret != null) 
				{
					return ret;
				}
			}
			
			return null;
		}
	}
	
	
	public static ItemStack getGrindingExplosive(ItemStack item)
	{
		if (item == null)
		{
			return null;
		}
		else
		{
			ItemStack ret = (ItemStack)grinderExplosives.get(Arrays.asList(item.itemID, item.getItemDamage()));
			
			if (ret != null) 
			{
				return ret;
			}
			
			return null;
		}
	}
	
}
