
package hawksmachinery;

import org.lwjgl.opengl.GL11;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;

/**
 * 
 * 
 * 
 * @author Elusivehawk
 */
public class HawkRenderCrusher extends TileEntitySpecialRenderer
{
	public static HawksMachinery BASEMOD;
	private HawkModelCrusher model;
	
	public HawkRenderCrusher()
	{
		this.model = new HawkModelCrusher();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity var1, double var2, double var3, double var4, float var5)
	{
		bindTextureByName(BASEMOD.TEXTURE_PATH + "/Crusher.png");
		GL11.glPushMatrix();
        GL11.glTranslatef((float) var2 + 0.5F, (float) var3 + 1.5F, (float) var4 + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        GL11.glPopMatrix();
        
	}
	
}
