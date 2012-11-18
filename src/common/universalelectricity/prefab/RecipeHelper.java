package universalelectricity.prefab;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.CraftingManager;
import net.minecraft.src.IRecipe;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.Configuration;
import universalelectricity.core.UEConfig;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * This class is used to replace recipes that are
 * already added in the existing recipe pool for
 * crafting and smelting. All recipe functions
 * take account of the Forge Ore Dictionary. It
 * also includes some recipe helper functions to
 * shorten some of your function calls.
 * 
 * @author Calclavia
 * 
 */
public class RecipeHelper
{
	public static List<IRecipe> getRecipesByOutput(ItemStack output)
	{
		List<IRecipe> list = new ArrayList<IRecipe>();

		for (Object obj : CraftingManager.getInstance().getRecipeList())
		{
			if (obj instanceof IRecipe)
			{
				if (((IRecipe) obj).getRecipeOutput() == output)
				{
					list.add((IRecipe) obj);
				}
			}
		}

		return list;
	}

	/**
	 * Replaces a recipe with a new IRecipe.
	 * 
	 * @return True if successful
	 */
	public static boolean replaceRecipe(IRecipe recipe, IRecipe newRecipe)
	{
		for (Object obj : CraftingManager.getInstance().getRecipeList())
		{
			if (obj instanceof IRecipe)
			{
				if (((IRecipe) obj).equals(recipe) || obj == recipe)
				{
					CraftingManager.getInstance().getRecipeList().remove(obj);
					CraftingManager.getInstance().getRecipeList().add(newRecipe);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Replaces a recipe with the resulting
	 * ItemStack with a new IRecipe.
	 * 
	 * @return True if successful
	 */
	public static boolean replaceRecipe(ItemStack recipe, IRecipe newRecipe)
	{
		if (removeRecipe(recipe))
		{
			CraftingManager.getInstance().getRecipeList().add(newRecipe);
			return true;
		}

		return false;
	}

	/**
	 * Removes a recipe by its IRecipe class.
	 * 
	 * @return True if successful
	 */
	public static boolean removeRecipe(IRecipe recipe)
	{
		for (Object obj : CraftingManager.getInstance().getRecipeList())
		{
			if (obj instanceof IRecipe)
			{
				if (((IRecipe) obj).equals(recipe) || obj == recipe)
				{
					CraftingManager.getInstance().getRecipeList().remove(obj);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Removes a recipe by its output
	 * 
	 * @return True if successful
	 */
	public static boolean removeRecipe(ItemStack stack)
	{
		for (Object obj : CraftingManager.getInstance().getRecipeList())
		{
			if (obj instanceof IRecipe)
			{
				if (((IRecipe) obj).getRecipeOutput().equals(stack))
				{
					CraftingManager.getInstance().getRecipeList().remove(obj);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Use this function if you want to check if
	 * the recipe is allowed in the configuration
	 * file.
	 */
	public static void addRecipe(IRecipe recipe, String name, Configuration config, boolean defaultBoolean)
	{
		if (config != null)
		{
			if (UEConfig.getConfigData(config, "Allow " + name + " Crafting", defaultBoolean))
			{
				GameRegistry.addRecipe(recipe);
			}
		}
	}

	public static void addRecipe(IRecipe recipe, Configuration config, boolean defaultBoolean)
	{
		addRecipe(recipe, recipe.getRecipeOutput().getItemName(), config, defaultBoolean);
	}
}
