
package hawksmachinery;

import java.util.Random;
import net.minecraft.src.World;

/**
 * 
 * Implement this if you would like your machine to require maintenance.
 * 
 * @author Elusivehawk
 */
public interface IHawkRepairable
{
	int machineHealth = 0;
	int maxMachineHP = 0;
	
	public boolean randomlyDamageSelf();
	
	public boolean attemptRepair(World world, int x, int y, int z, int repairAmount);
	
}
