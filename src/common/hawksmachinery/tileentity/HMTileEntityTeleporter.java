
package hawksmachinery.tileentity;

import hawksmachinery.HawksMachinery;
import hawksmachinery.api.HMEndiumTeleporterCoords;
import hawksmachinery.api.HMTeleportationHelper;
import net.minecraft.src.ChunkCoordIntPair;
import net.minecraft.src.Entity;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.common.ForgeDirection;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HMTileEntityTeleporter extends HMTileEntityMachine
{
	public HMEndiumTeleporterCoords coords;
	public Ticket heldChunk;
	
	public int[] coordsArray;
	
	public HMTileEntityTeleporter()
	{
		ELECTRICITY_LIMIT = 100000;
		ELECTRICITY_REQUIRED = 200;
		voltage = 120;
		this.coordsArray = new int[3];
		
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if (this.isReadyToTeleport() && this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord) != 0 && this.getBlockMetadata() == 0)
		{
			WorldServer receivingDim = DimensionManager.getWorld(this.coords.dim());
			
			if (receivingDim.getBlockId(this.coords.x(), this.coords.y() + 1, this.coords.z()) == 0)
			{
				TileEntity potenTileEntity = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
				
				if (potenTileEntity != null)
				{
					if (!HMTeleportationHelper.instance().isTileEntityWhitelisted(potenTileEntity))
					{
						return;
					}
					
				}
				
				int blockID = this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord);
				int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord + 1, this.zCoord);
				
				receivingDim.setBlockAndMetadataWithUpdate(this.coords.x(), this.coords.y() + 1, this.coords.z(), blockID, meta, true);
				this.worldObj.setBlockAndMetadata(this.xCoord, this.yCoord + 1, this.zCoord, 0, 0);
				this.doTeleportationSpecialEffects();
				this.electricityStored = 0;
				--this.machineHP;
				
			}
			
		}
		
	}
	
	@Override
	public boolean isDisabled()
	{
		return this.isBeingSapped() || (this.machineHP == 0 && this.getMaxHP() > 0);
	}
	
	@Override
	public double wattRequest()
	{
		if (this.isDisabled())
		{
			return 0;
		}
		else
		{
			if (this.electricityStored + this.ELECTRICITY_REQUIRED <= this.ELECTRICITY_LIMIT)
			{
				return this.ELECTRICITY_REQUIRED;
			}
			else
			{
				if (this.ELECTRICITY_LIMIT != this.electricityStored)
				{
					if (this.electricityStored + this.ELECTRICITY_REQUIRED >= this.ELECTRICITY_LIMIT)
					{
						return this.ELECTRICITY_LIMIT - this.electricityStored;
					}
					else
					{
						return this.ELECTRICITY_REQUIRED;
					}
					
				}
				else
				{
					return 0;
				}
				
			}
			
		}
		
	}
	
	public boolean isReadyToTeleport()
	{
		if (this.getBlockMetadata() == 0)
		{
			return this.coords != null && !this.isDisabled();
			
		}
		else
		{
			return this.coords != null;
		}
		
	}
	
	public void tryTeleportEntity(Entity entity)
	{
		if (this.isReadyToTeleport() && this.worldObj.isBlockGettingPowered(this.xCoord, this.yCoord, this.zCoord))
		{
			HMTeleportationHelper.instance().teleportEntity(entity, this.coords);
			this.doTeleportationSpecialEffects();
			this.electricityStored = 0;
			--this.machineHP;
			
		}
		
	}
	
	public void updateCoords(int newCoord)
	{
		this.coordsArray[0] = this.coordsArray[1];
		this.coordsArray[1] = this.coordsArray[2];
		this.coordsArray[2] = newCoord;
		
	}
	
	public void wipeCoords()
	{
		this.coordsArray[0] = 0;
		this.coordsArray[1] = 0;
		this.coordsArray[2] = 0;
		this.coords = null;
		
	}
	
	public boolean registerCoords()
	{
		if (this.coordsArray[0] != 0 && this.coordsArray[1] != 0 && this.coordsArray[2] != 0)
		{
			if (this.getBlockMetadata() == 0)
			{
				HMEndiumTeleporterCoords newCoords = HMTeleportationHelper.instance().getCoordsFromSymbols(this.coordsArray[0], this.coordsArray[1], this.coordsArray[2]);
				
				if (newCoords != null)
				{
					this.coords = newCoords;
					return true;
				}
				
			}
			else if (this.getBlockMetadata() == 1)
			{
				HMEndiumTeleporterCoords newCoords = new HMEndiumTeleporterCoords(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getWorldInfo().getDimension(), this.coordsArray[0], this.coordsArray[1], this.coordsArray[2]);
				
				if (HMTeleportationHelper.instance().registerCoords(newCoords))
				{
					this.coords = newCoords;
					return true;
				}
				
			}
			
		}
		
		return false;
	}
	
	public void doTeleportationSpecialEffects()
	{
		//TODO Add special effects.
	}
	
	@Override
	public boolean canReceiveFromSide(ForgeDirection side)
	{
		return side == ForgeDirection.DOWN && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) == 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound NBTTag)
	{
		super.readFromNBT(NBTTag);
		
		this.coordsArray[0] = NBTTag.getInteger("coords1");
		this.coordsArray[1] = NBTTag.getInteger("coords2");
		this.coordsArray[2] = NBTTag.getInteger("coords3");
		this.registerCoords();
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound NBTTag)
	{
		super.writeToNBT(NBTTag);
		
		NBTTag.setInteger("coords1", this.coordsArray[0]);
		NBTTag.setInteger("coords2", this.coordsArray[1]);
		NBTTag.setInteger("coords3", this.coordsArray[2]);
		
	}
	
	@Override
	public void invalidate()
	{
		this.forceChunkLoading(null);
	}
	
	@Override
	public void validate()
	{
		this.forceChunkLoading(null);
	}
	
	public void forceChunkLoading(Ticket ticket)
	{
		if (ticket != null)
		{
			this.heldChunk = ticket;
			ForgeChunkManager.forceChunk(this.heldChunk, new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4));
			
		}
		else
		{
			if (this.heldChunk == null)
			{
				Ticket newTicket = ForgeChunkManager.requestTicket(HawksMachinery.instance(), this.worldObj, Type.NORMAL);
				newTicket.getModData().setInteger("xCoord", this.xCoord);
				newTicket.getModData().setInteger("yCoord", this.yCoord);
				newTicket.getModData().setInteger("zCoord", this.zCoord);
				newTicket.setChunkListDepth(HawksMachinery.MANAGER.maxChunksLoaded);
				this.heldChunk = newTicket;
				ForgeChunkManager.forceChunk(this.heldChunk, new ChunkCoordIntPair(this.xCoord >> 4, this.zCoord >> 4));
				
			}
			else
			{
				ForgeChunkManager.releaseTicket(this.heldChunk);
				this.heldChunk = null;
				
			}
			
		}
		
	}
	
}