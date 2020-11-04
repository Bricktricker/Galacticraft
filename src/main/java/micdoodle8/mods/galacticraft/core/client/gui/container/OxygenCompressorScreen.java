package micdoodle8.mods.galacticraft.core.client.gui.container;

import com.mojang.blaze3d.systems.RenderSystem;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.inventory.OxygenCompressorContainer;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import micdoodle8.mods.galacticraft.core.tile.OxygenCompressorTileEntity;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class OxygenCompressorScreen extends GuiContainerGC<OxygenCompressorContainer> {
	private static final ResourceLocation compressorTexture = new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/compressor.png");

	private final GuiElementInfoRegion oxygenInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 112, (this.height - this.ySize) / 2 + 24, 56, 9, new ArrayList<>(), this.width,
			this.height, this);
	private final GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 112, (this.height - this.ySize) / 2 + 37, 56, 9, new ArrayList<>(), this.width,
			this.height, this);

	public OxygenCompressorScreen(OxygenCompressorContainer container, PlayerInventory playerInv, ITextComponent title) {
		super(container, playerInv, title);
		this.ySize = 180;
	}

	@Override
	protected void init() {
		super.init();
		List<ITextComponent> batterySlotDesc = new ArrayList<>();
		batterySlotDesc.add(new TranslationTextComponent("gui.battery_slot.desc.0"));
		batterySlotDesc.add(new TranslationTextComponent("gui.battery_slot.desc.1"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 46, (this.height - this.ySize) / 2 + 26, 18, 18, batterySlotDesc, this.width, this.height, this));
		List<ITextComponent> oxygenSlotDesc = new ArrayList<>();
		oxygenSlotDesc.add(new TranslationTextComponent("gui.oxygen_slot.desc.0"));
		oxygenSlotDesc.add(new TranslationTextComponent("gui.oxygen_slot.desc.1"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 16, (this.height - this.ySize) / 2 + 26, 18, 18, oxygenSlotDesc, this.width, this.height, this));
		List<ITextComponent> compressorSlotDesc = new ArrayList<>();
		compressorSlotDesc.add(new TranslationTextComponent("gui.oxygen_compressor.slot.desc.0"));
		compressorSlotDesc.add(new TranslationTextComponent("gui.oxygen_compressor.slot.desc.1"));
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 132, (this.height - this.ySize) / 2 + 70, 18, 18, compressorSlotDesc, this.width, this.height, this));
		List<ITextComponent> oxygenDesc = new ArrayList<>();
		oxygenDesc.add(new TranslationTextComponent("gui.oxygen_storage.desc.0"));
		oxygenDesc.add(new TranslationTextComponent("gui.oxygen_storage.desc.1", (int) Math.floor(this.container.getStoredOxygen()), (int) Math.floor(this.container.getMaxOxygen()))
				.applyTextStyle(TextFormatting.YELLOW));
		this.oxygenInfoRegion.tooltips = oxygenDesc;
		this.oxygenInfoRegion.xPosition = (this.width - this.xSize) / 2 + 112;
		this.oxygenInfoRegion.yPosition = (this.height - this.ySize) / 2 + 24;
		this.oxygenInfoRegion.parentWidth = this.width;
		this.oxygenInfoRegion.parentHeight = this.height;
		this.infoRegions.add(this.oxygenInfoRegion);
		List<ITextComponent> electricityDesc = new ArrayList<>();
		electricityDesc.add(new TranslationTextComponent("gui.energy_storage.desc.0"));
		electricityDesc.add(new TranslationTextComponent("gui.energy_storage.desc.1", (int) Math.floor(this.container.getStoredEnergy()), (int) Math.floor(this.container.getMaxEnergy()))
				.applyTextStyle(TextFormatting.YELLOW));
		this.electricInfoRegion.tooltips = electricityDesc;
		this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 112;
		this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 37;
		this.electricInfoRegion.parentWidth = this.width;
		this.electricInfoRegion.parentHeight = this.height;
		this.infoRegions.add(this.electricInfoRegion);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.font.drawString(this.title.getFormattedText(), 8, 10, 4210752);
		GCCoreUtil.drawStringRightAligned(I18n.format("gui.message.in.name") + ":", 99, 26, 4210752, this.font);
		GCCoreUtil.drawStringRightAligned(I18n.format("gui.message.in.name") + ":", 99, 38, 4210752, this.font);
		String status = new TranslationTextComponent("gui.message.status.name").appendSibling(this.getStatus()).getFormattedText();
		this.font.drawString(status, this.xSize / 2 - this.font.getStringWidth(status) / 2, 50, 4210752);
		status = I18n.format("gui.oxygen_use.desc") + ": " + OxygenCompressorTileEntity.TANK_TRANSFER_SPEED * 20 + GCCoreUtil.translate("gui.per_second");
		this.font.drawString(status, this.xSize / 2 - this.font.getStringWidth(status) / 2, 60, 4210752);
//        status = ElectricityDisplay.getDisplay(this.compressor.ueWattsPerTick * 20, ElectricUnit.WATT);
//        this.font.drawString(status, this.xSize / 2 - this.font.getStringWidth(status) / 2, 70, 4210752);
//        status = ElectricityDisplay.getDisplay(this.compressor.getVoltage(), ElectricUnit.VOLTAGE);
//        this.font.drawString(status, this.xSize / 2 - this.font.getStringWidth(status) / 2, 80, 4210752);
		this.font.drawString(I18n.format("container.inventory"), 8, this.ySize - 104 + 17, 4210752);
	}

	private ITextComponent getStatus() {
		if(!(this.container.getInventory().get(0).getItem() instanceof ItemOxygenTank)) {
			return new TranslationTextComponent("gui.status.missingtank.name").applyTextStyle(TextFormatting.DARK_RED);
		}

		if(this.container.getInventory().get(0).getDamage() == 0) {
			return new TranslationTextComponent("gui.status.fulltank.name").applyTextStyle(TextFormatting.DARK_RED);
		}

		if(this.container.getStoredOxygen() < 1.0D) {
			return new TranslationTextComponent("gui.status.missingoxygen.name").applyTextStyle(TextFormatting.DARK_RED);
		}

		return new TranslationTextComponent("gui.oxygen_compressor.status");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(OxygenCompressorScreen.compressorTexture);
		final int var5 = (this.width - this.xSize) / 2;
		final int var6 = (this.height - this.ySize) / 2;
		this.blit(var5, var6 + 5, 0, 0, this.xSize, 181);

		int scale = this.getCappedScaledOxygenLevel(54);
		this.blit(var5 + 113, var6 + 25, 197, 7, Math.min(scale, 54), 7);
		scale = (int) Math.floor(this.container.getStoredEnergy() * 54f / this.container.getMaxEnergy());
		this.blit(var5 + 113, var6 + 38, 197, 0, Math.min(scale, 54), 7);

		if(this.container.getStoredEnergy() > 0) {
			this.blit(var5 + 99, var6 + 37, 176, 0, 11, 10);
		}

		if(this.container.getStoredOxygen() > 0) {
			this.blit(var5 + 100, var6 + 24, 187, 0, 10, 10);
		}

		List<ITextComponent> oxygenDesc = new ArrayList<>();
		oxygenDesc.add(new TranslationTextComponent("gui.oxygen_storage.desc.0"));
		oxygenDesc.add(new TranslationTextComponent("gui.oxygen_storage.desc.1", (int) Math.floor(this.container.getStoredOxygen()), (int) Math.floor(this.container.getMaxOxygen()))
				.applyTextStyle(TextFormatting.YELLOW));
		this.oxygenInfoRegion.tooltips = oxygenDesc;

		List<ITextComponent> electricityDesc = new ArrayList<>();
		electricityDesc.add(new TranslationTextComponent("gui.energy_storage.desc.0"));
		EnergyDisplayHelper.getEnergyDisplayTooltip(this.container.getStoredEnergy(), this.container.getMaxEnergy(), electricityDesc);
//			electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.compressor.getEnergyStoredGC()) + " / " + (int) Math.floor(this.compressor.getMaxEnergyStoredGC())));
		this.electricInfoRegion.tooltips = electricityDesc;
	}

	private int getCappedScaledOxygenLevel(int scale) {
		return (int) Math.max(Math.min(Math.floor((double) this.container.getStoredOxygen() / (double) this.container.getMaxOxygen() * scale), scale), 0);
	}
}
