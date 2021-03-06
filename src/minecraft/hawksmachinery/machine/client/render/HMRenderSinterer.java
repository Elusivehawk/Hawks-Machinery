
package hawksmachinery.machine.client.render;

import hawksmachinery.core.common.HMCore;
import hawksmachinery.machine.client.model.HMModelSinterer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import universalelectricity.prefab.implement.IRotatable;

/**
 *
 *
 *
 * @author Elusivehawk
 */
public class HMRenderSinterer extends TileEntitySpecialRenderer
{
	private HMModelSinterer model;
	
	public HMRenderSinterer()
	{
		this.model = new HMModelSinterer();
		
	}
	
	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var3, double var4, float var5)
	{
		this.bindTextureByName(HMCore.TEXTURE_PATH + "/Sinterer.png");
		GL11.glPushMatrix();
		GL11.glTranslatef((float) var2 + 0.5F, (float) var3 + 1.5F, (float) var4 + 0.5F);
		switch (((IRotatable)var1).getDirection().ordinal())
		{
			case 2: GL11.glRotatef(180, 0.0F, 1.0F, 0.0F); break;
			case 3: GL11.glRotatef(0, 0.0F, 1.0F, 0.0F); break;
			case 4: GL11.glRotatef(270, 0.0F, 1.0F, 0.0F); break;
			case 5: GL11.glRotatef(90, 0.0F, 1.0F, 0.0F); break;
			
		}
		
		GL11.glScalef(1.0F, -1F, -1F);
		this.model.render(null, 0, 0, 0, 0, 0, 0.0625F);
		GL11.glPopMatrix();
		
	}
	
}
