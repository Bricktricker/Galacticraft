package micdoodle8.mods.galacticraft.core.client.gui.container;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.inventory.CargoUnloaderContainer;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.tile.CargoUnloaderTileEntity;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class CargoUnloaderScreen extends GuiContainerGC<CargoUnloaderContainer> {
	public static final ResourceLocation loaderTexture = new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/cargo_loader.png");

	private final CargoUnloaderTileEntity cargoLoader;

	private Button buttonLoadItems;
	private final GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 107, (this.height - this.ySize) / 2 + 101, 56, 9, new ArrayList<>(), this.width,
			this.height, this);

	public CargoUnloaderScreen(CargoUnloaderContainer container, PlayerInventory playerInv, ITextComponent title) {
		super(container, playerInv, title);
		this.cargoLoader = (CargoUnloaderTileEntity) container.getCargoTile();
		this.ySize = 201;
	}

	@Override
	protected void init() {
		super.init();
		List<String> electricityDesc = new ArrayList<>();
		electricityDesc.add(GCCoreUtil.translate("gui.energy_storage.desc.0"));
		electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energy_storage.desc.1")
				+ ((int) Math.floor(this.container.getStoredEnergy()) + " / " + (int) Math.floor(this.cargoLoader.getMaxEnergy())));
		this.electricInfoRegion.tooltipStrings = electricityDesc;
		this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 107;
		this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 101;
		this.electricInfoRegion.parentWidth = this.width;
		this.electricInfoRegion.parentHeight = this.height;
		this.infoRegions.add(this.electricInfoRegion);
		List<String> batterySlotDesc = new ArrayList<>();
		batterySlotDesc.add(GCCoreUtil.translate("gui.battery_slot.desc.0"));
		batterySlotDesc.add(GCCoreUtil.translate("gui.battery_slot.desc.1"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 9, (this.height - this.ySize) / 2 + 26, 18, 18, batterySlotDesc, this.width, this.height, this));
		this.buttons.add(this.buttonLoadItems = new Button(this.width / 2 - 1, this.height / 2 - 23, 76, 20, GCCoreUtil.translate("gui.button.loaditems.name"),
				(button) -> GalacticraftCore.packetPipeline.sendToServer(
						new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, GCCoreUtil.getDimensionType(this.cargoLoader.getWorld()), new Object[] { this.cargoLoader.getPos(), 0 }))));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		int offsetX = -17;
		int offsetY = 45;
		this.font.drawString(this.title.getFormattedText(), 60, 12, 4210752);
		this.buttonLoadItems.active = true; // this.cargoLoader.disableCooldown == 0; TODO: what is disableCooldown?
		this.buttonLoadItems.setMessage(!true ? GCCoreUtil.translate("gui.button.stoploading.name") : GCCoreUtil.translate("gui.button.loaditems.name"));
		this.font.drawString(new TranslationTextComponent("gui.message.status.name").appendSibling(this.getStatus()).getFormattedText(), 28 + offsetX, 45 + 23 - 46 + offsetY, 4210752);
		this.font.drawString(I18n.format("container.inventory"), 8, this.ySize - 90, 4210752);
	}

	private ITextComponent getStatus() {
		if(!this.container.hasTarget()) {
			return new TranslationTextComponent("gui.status.notargetload.name").applyTextStyle(TextFormatting.DARK_RED);
		}
		
		if(this.container.isTargetEmpty()) {
			return new TranslationTextComponent("gui.status.targetempty.name").applyTextStyle(TextFormatting.DARK_RED);
		}

		return new TranslationTextComponent("galacticraftcore.cargo_loader_screen.guistatus");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(CargoLoaderScreen.loaderTexture);
		final int var5 = (this.width - this.xSize) / 2;
		final int var6 = (this.height - this.ySize) / 2;
		this.blit(var5, var6 + 5, 0, 0, this.xSize, this.ySize);

		List<String> electricityDesc = new ArrayList<>();
		electricityDesc.add(GCCoreUtil.translate("gui.energy_storage.desc.0"));
		EnergyDisplayHelper.getEnergyDisplayTooltip(this.container.getStoredEnergy(), this.cargoLoader.getMaxEnergy(), electricityDesc);
//		electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.cargoLoader.getEnergyStoredGC()) + " / " + (int) Math.floor(this.cargoLoader.getMaxEnergyStoredGC())));
		this.electricInfoRegion.tooltipStrings = electricityDesc;

		if(this.container.getStoredEnergy() > 0) {
			this.blit(var5 + 94, var6 + 101, 176, 0, 11, 10);
		}

		int tmp = (int) Math.floor(this.container.getStoredEnergy() * 54 / this.cargoLoader.getMaxEnergy());
		this.blit(var5 + 108, var6 + 102, 187, 0, Math.min(tmp, 54), 7);
	}

}
