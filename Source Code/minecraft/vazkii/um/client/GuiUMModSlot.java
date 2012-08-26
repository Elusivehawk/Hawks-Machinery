package vazkii.um.client;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Tessellator;
import vazkii.um.common.UpdateManager;
import vazkii.um.common.UpdateManagerMod;
import cpw.mods.fml.client.GuiScrollingList;

/**
 * @author Vazkii, cpw
 */
public class GuiUMModSlot extends GuiScrollingList {

	public GuiUMModSlot(GuiScreen parentGui, int listWidth) {
		super(ModLoader.getMinecraftInstance(), listWidth, parentGui.height, 32, parentGui.height - 65 + 4, 10, 25);
		parent = (GuiModList) parentGui;
	}

	private GuiModList parent;

	@Override
	protected void drawSlot(int listIndex, int var2, int var3, int var4, Tessellator var5) {
		FontRenderer fr = ModLoader.getMinecraftInstance().fontRenderer;
		UpdateManagerMod mod = parent.mods.get(listIndex);
		fr.drawString(fr.trimStringToWidth(mod.getModName(), listWidth - 11), left + 3, var3 + 2, mod.getModType().getHex());
		fr.drawString(fr.trimStringToWidth(mod.disableChecks() || !UpdateManager.online ? "Not Checking" : ThreadDownloadMod.downloadings.contains(mod.getModName()) ? "§aDownloading..." : UpdateManager.isModUpdated(mod) ? "§aUpdated" : "§cOutdated", listWidth - 11), left + 3, var3 + 12, 0xFFFFFF);
	}

	@Override
	protected int getSize() {
		return UpdateManager.loadedModsSet.size();
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
		parent.selectModIndex(var1);
	}

	@Override
	protected boolean isSelected(int var1) {
		return parent.isIndexSelected(var1);
	}

	@Override
	protected void drawBackground() {
		parent.drawBackground(0);
	}

}
