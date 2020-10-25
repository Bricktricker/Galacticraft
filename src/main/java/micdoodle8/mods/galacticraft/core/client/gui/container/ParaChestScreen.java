package micdoodle8.mods.galacticraft.core.client.gui.container;

import com.mojang.blaze3d.systems.RenderSystem;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.entities.IScaleableFuelLevel;
import micdoodle8.mods.galacticraft.core.inventory.ParaChestContainer;
import micdoodle8.mods.galacticraft.core.tile.ParaChestTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ParaChestScreen extends GuiContainerGC<ParaChestContainer> {
	private static final ResourceLocation[] parachestTexture = new ResourceLocation[4];

	static {
		for(int i = 0; i < 4; i++) {
			ParaChestScreen.parachestTexture[i] = new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/chest_" + i * 18 + ".png");
		}
	}

	private final ParaChestTileEntity parachest;

	private int inventorySlots = 0;

	public ParaChestScreen(ParaChestContainer container, PlayerInventory playerInv, ITextComponent title) {
		super(container, playerInv, title);
		this.parachest = container.getParaChest();
		this.passEvents = false;
		this.inventorySlots = parachest.getSizeInventory();
		this.ySize = 146 + this.inventorySlots * 2;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.font.drawString(this.title.getFormattedText(), 8, 6, 4210752);
		this.font.drawString(this.title.getFormattedText(), 8, this.ySize - 103 + (this.inventorySlots == 3 ? 2 : 4), 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(ParaChestScreen.parachestTexture[(this.inventorySlots - 3) / 18]);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.blit(k, l, 0, 0, this.xSize, this.ySize);

		if(this.parachest != null) {
			int fuelLevel = ((IScaleableFuelLevel) this.parachest).getScaledFuelLevel(28);
			this.blit(k + 17, l + (this.inventorySlots == 3 ? 40 : 42) - fuelLevel + this.inventorySlots * 2, 176, 28 - fuelLevel, 34, fuelLevel);
		}
	}
}
