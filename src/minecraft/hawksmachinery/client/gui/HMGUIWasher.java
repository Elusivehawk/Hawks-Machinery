
package hawksmachinery.client.gui;

import hawksmachinery.common.HawksMachinery;
import hawksmachinery.common.container.HMContainerWasher;
import hawksmachinery.common.tileentity.HMTileEntityWasher;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HMGUIWasher extends GuiContainer
{
	private HMTileEntityWasher tileEntity;
	
	private int containerWidth;
	private int containerHeight;
	
	public HMGUIWasher(InventoryPlayer playerInv, HMTileEntityWasher tileEntity)
	{
		super(new HMContainerWasher(playerInv, tileEntity));
		this.tileEntity = tileEntity;
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		
	}
	
	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture(HawksMachinery.GUI_PATH + "/Washer.png"));
		this.containerWidth = (this.width - this.xSize) / 2;
		this.containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(this.containerWidth, this.containerHeight, 0, 0, this.xSize, this.ySize);
		
		if (this.tileEntity.workTicks > 0)
		{
			int scale = this.tileEntity.getWashingStatus(this.tileEntity.TICKS_REQUIRED);
			this.drawTexturedModalRect(containerWidth + 52, containerHeight + 28, 176, 0, 52 - scale, 20);
		}
		
		if (this.tileEntity.waterUnits > 0)
		{
			double percent = (double)this.tileEntity.waterUnits / (double)this.tileEntity.WATER_LIMIT;
			int waterLvl = (int)(percent * 71) + 1;
			this.drawTexturedModalRect(containerWidth + 8, containerHeight + 78 - waterLvl, 178, 94 - waterLvl, 17, waterLvl);
			
		}
		
	}
	
}
