package universalelectricity.basiccomponents;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;

import org.lwjgl.opengl.GL11;

import universalelectricity.network.PacketManager;

public class GUIElectricFurnace extends GuiContainer
{
    private TileEntityElectricFurnace tileEntity;

    private int containerWidth;
    private int containerHeight;
    
    private long GUITicks = 0;

    public GUIElectricFurnace(InventoryPlayer par1InventoryPlayer, TileEntityElectricFurnace tileEntity)
    {
        super(new ContainerElectricFurnace(par1InventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }
    
    public void initGui()
    {
    	super.initGui();
    	PacketManager.sendTileEntityPacketToServer(this.tileEntity, "BasicComponents", (int)-1, true);
    }
    
    @Override
    public void onGuiClosed()
    {
    	super.onGuiClosed();
    	PacketManager.sendTileEntityPacketToServer(this.tileEntity, "BasicComponents", (int)-1, false);
    }
    
    public void updateScreen()
    {
    	super.updateScreen();
    	
    	if(GUITicks % 100 == 0)
    	{
    		PacketManager.sendTileEntityPacketToServer(this.tileEntity, "BasicComponents", (int)-1, true);
    	}
    	GUITicks ++;
    }

    /**
    * Draw the foreground layer for the GuiContainer (everything in front of the items)
    */
    @Override
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString("Electric Furnace", 60, 6, 4210752);
        this.fontRenderer.drawString("Smelting:", 10, 28, 4210752);
        this.fontRenderer.drawString("Battery:", 10, 53, 4210752);
        String displayText = "";

        if (this.tileEntity.isDisabled())
        {
            displayText = "Disabled!";
        }
        else if (this.tileEntity.smeltingTicks > 0)
        {
            displayText = "Ready";
        }
        else
        {
            displayText = "Idle";
        }

        this.fontRenderer.drawString("Status: " + displayText, 90, 48, 4210752);
        this.fontRenderer.drawString("Voltage: " + (int)this.tileEntity.getVoltage(), 89, 60, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
    * Draw the background layer for the GuiContainer (everything behind the items)
    */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int var4 = this.mc.renderEngine.getTexture(BasicComponents.FILE_PATH + "ElectricFurnace.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        containerWidth = (this.width - this.xSize) / 2;
        containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);

        if (this.tileEntity.smeltingTicks > 0)
        {
            int scale = (int)(((double)this.tileEntity.smeltingTicks / this.tileEntity.smeltingTimeRequired) * 23);
            this.drawTexturedModalRect(containerWidth + 77, containerHeight + 24, 176, 0, 23 - scale, 20);
        }
    }
}