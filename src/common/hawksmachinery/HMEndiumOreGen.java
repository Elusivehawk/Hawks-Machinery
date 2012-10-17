
package hawksmachinery;

import java.util.Random;
import net.minecraft.src.ChunkProviderEnd;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import universalelectricity.ore.OreGenReplace;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HMEndiumOreGen extends OreGenReplace
{
	public static HawksMachinery BASEMOD;
	
	public HMEndiumOreGen()
	{
		super("Endium Ore", "oreEndium", new ItemStack(BASEMOD.endiumOre), 121, 12, 64, 8, 8, "pickaxe", 3);
		this.generateEnd = true;
		this.shouldGenerate = BASEMOD.MANAGER.generateEndium;
		
	}
	
}