package micdoodle8.mods.galacticraft.core.client.gui.container;

import com.mojang.blaze3d.systems.RenderSystem;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.inventory.FuelLoaderContainer;
import micdoodle8.mods.galacticraft.core.networking.NetworkHandler;
import micdoodle8.mods.galacticraft.core.networking.UpdateDisablePacket;
import micdoodle8.mods.galacticraft.core.tile.FuelLoaderTileEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class FuelLoaderScreen extends GuiContainerGC<FuelLoaderContainer>
{
    private static final ResourceLocation fuelLoaderTexture = new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/fuel_loader.png");

    private Button buttonLoadFuel;
    private final GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 112, (this.height - this.ySize) / 2 + 65, 56, 9, new ArrayList<>(), this.width, this.height, this);

    public FuelLoaderScreen(FuelLoaderContainer container, PlayerInventory playerInv, ITextComponent title)
    {
        super(container, playerInv, title);
        this.ySize = 180;
    }

    @Override
    protected void init()
    {
        super.init();
        List<ITextComponent> fuelTankDesc = new ArrayList<>();
        fuelTankDesc.add(new TranslationTextComponent("gui.fuel_tank.desc.2"));
        fuelTankDesc.add(new TranslationTextComponent("gui.fuel_tank.desc.3"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 33, 16, 38, fuelTankDesc, this.width, this.height, this));
        List<ITextComponent> batterySlotDesc = new ArrayList<>();
        batterySlotDesc.add(new TranslationTextComponent("gui.battery_slot.desc.0"));
        batterySlotDesc.add(new TranslationTextComponent("gui.battery_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 50, (this.height - this.ySize) / 2 + 54, 18, 18, batterySlotDesc, this.width, this.height, this));
        List<ITextComponent> electricityDesc = new ArrayList<>();
        electricityDesc.add(new TranslationTextComponent("gui.energy_storage.desc.0"));
        electricityDesc.add(new TranslationTextComponent("gui.energy_storage.desc.1", (int) Math.floor(this.container.getStoredEnergy()), (int) Math.floor(this.container.getMaxEnergy())).applyTextStyle(TextFormatting.YELLOW));
        this.electricInfoRegion.tooltips = electricityDesc;
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 112;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 65;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
		this.buttonLoadFuel = addButton(new Button(this.width / 2 - 1, this.height / 2 - 23, 76, 20, I18n.format("gui.button.loadfuel.name"), (b) -> {
			NetworkHandler.INSTANCE.sendToServer(new UpdateDisablePacket(this.container.getFuelLoader().getPos()));	
		}));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.font.drawString(this.title.getFormattedText(), 60, 10, 4210752);
        this.buttonLoadFuel.active = /*this.fuelLoader.disableCooldown == 0 &&*/ true; //this.fuelLoader.fuelTank.getFluid() != FluidStack.EMPTY && this.fuelLoader.fuelTank.getFluid().getAmount() > 0;
        this.buttonLoadFuel.setMessage(!this.container.isDisabled() ? I18n.format("gui.button.stoploading.name") : I18n.format("gui.button.loadfuel.name"));
        this.font.drawString(new TranslationTextComponent("gui.message.status.name").appendSibling(this.getStatus()).getFormattedText(), 28, 45 + 23 - 46, 4210752);
        //this.font.drawString("" + this.fuelLoader.storage.getMaxExtract(), 28, 56 + 23 - 46, 4210752);
        //		this.font.drawString(ElectricityDisplay.getDisplay(this.fuelLoader.getVoltage(), ElectricUnit.VOLTAGE), 28, 68 + 23 - 46, 4210752);
        this.font.drawString(I18n.format("container.inventory"), 8, this.ySize - 118 + 2 + 11, 4210752);
    }

    private ITextComponent getStatus()
    {
        if (this.container.getStoredFuel() == 0)
        {
            return new TranslationTextComponent("gui.status.nofuel.name").applyTextStyle(TextFormatting.DARK_RED);
        }

        return new TranslationTextComponent("gui.galacticraftcore.fuel_loader.gui_status");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(FuelLoaderScreen.fuelLoaderTexture);
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.blit(var5, var6 + 5, 0, 0, this.xSize, 181);

        final int fuelLevel = this.getScaled(this.container.getStoredFuel(), FuelLoaderTileEntity.TANK_CAPACITY, 38); // (int) (this.container.getStoredFuel() * 38 / (float)FuelLoaderTileEntity.TANK_CAPACITY);
        this.blit((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 17 + 54 - fuelLevel, 176, 38 - fuelLevel, 16, fuelLevel);

        List<ITextComponent> electricityDesc = new ArrayList<>();
        electricityDesc.add(new TranslationTextComponent("gui.energy_storage.desc.0"));
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.container.getStoredEnergy(), this.container.getMaxEnergy(), electricityDesc);
//		electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.fuelLoader.getEnergyStoredGC()) + " / " + (int) Math.floor(this.fuelLoader.getMaxEnergyStoredGC())));
        this.electricInfoRegion.tooltips = electricityDesc;

        if (this.container.getStoredEnergy() > 0)
        {
            this.blit(var5 + 99, var6 + 65, 192, 7, 11, 10);
        }

        int electricLevel = this.getScaled(this.container.getStoredEnergy(), this.container.getMaxEnergy(), 54);
        this.blit(var5 + 113, var6 + 66, 192, 0, Math.min(electricLevel, 54), 7);
    }
}
