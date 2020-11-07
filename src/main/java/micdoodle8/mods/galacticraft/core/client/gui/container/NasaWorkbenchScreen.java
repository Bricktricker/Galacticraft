package micdoodle8.mods.galacticraft.core.client.gui.container;

import com.mojang.blaze3d.systems.RenderSystem;

import micdoodle8.mods.galacticraft.core.inventory.NasaWorkbenchContainer;
import micdoodle8.mods.galacticraft.core.networking.NetworkHandler;
import micdoodle8.mods.galacticraft.core.networking.SwitchPagePacket;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.ITextComponent;

public class NasaWorkbenchScreen extends GuiContainerGC<NasaWorkbenchContainer> {

	private ResourceLocation guiTexture;
	private Button nextButton;
	private Button prevbutton;

	public NasaWorkbenchScreen(NasaWorkbenchContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		Vec2f screenSize = container.getCurrentSchematic().getScreenSize();
		this.xSize = Math.round(screenSize.x);
		this.ySize = Math.round(screenSize.y);
		this.guiTexture = container.getCurrentSchematic().getScreenTexture();
	}

	@Override
	protected void init() {
		super.init();
		this.nextButton = addButton(new Button(this.guiLeft - 52, this.guiTop + 2, 50, 20, "weiter", b -> switchPage(true)));
		this.prevbutton = addButton(new Button(this.guiLeft - 52, this.guiTop + 24, 50, 20, "zurück", b -> switchPage(false)));
		this.nextButton.active = container.hasNextPage();
		this.prevbutton.active = container.hasPrevPage();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(this.guiTexture);
		this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = container.getCurrentSchematic().getTitle().getFormattedText();
		this.font.drawString(s, 6f, 6.0F, 4210752); //(float) (this.xSize / 2 - this.font.getStringWidth(s) / 2)
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 4210752);
	}
	
	private void switchPage(boolean forward) {
		NetworkHandler.INSTANCE.sendToServer(new SwitchPagePacket(forward));
		this.container.nextPage(forward);
		
		Vec2f screenSize = container.getCurrentSchematic().getScreenSize();
		this.xSize = Math.round(screenSize.x);
		this.ySize = Math.round(screenSize.y);
		this.guiTexture = container.getCurrentSchematic().getScreenTexture();
		this.nextButton.active = container.hasNextPage();
		this.prevbutton.active = container.hasPrevPage();
	}

}
