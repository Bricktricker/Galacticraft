package micdoodle8.mods.galacticraft.core.client.gui.container;

import com.mojang.blaze3d.systems.RenderSystem;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.inventory.FuelLoaderContainer;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.tile.FuelLoaderTileEntity;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class GuiFuelLoader extends GuiContainerGC<FuelLoaderContainer>
{
    private static final ResourceLocation fuelLoaderTexture = new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/fuel_loader.png");

    private final FuelLoaderTileEntity fuelLoader;

    private Button buttonLoadFuel;
    private final GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 112, (this.height - this.ySize) / 2 + 65, 56, 9, new ArrayList<>(), this.width, this.height, this);

    public GuiFuelLoader(FuelLoaderContainer container, PlayerInventory playerInv, ITextComponent title)
    {
        super(container, playerInv, title);
//        super(new ContainerFuelLoader(playerInv, fuelLoader), playerInv, new TranslationTextComponent("container.fuelloader.name"));
        this.fuelLoader = container.getFuelLoader();
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
        electricityDesc.add(new TranslationTextComponent("gui.energy_storage.desc.1", (int) Math.floor(this.fuelLoader.getStoredEnergy()), (int) Math.floor(this.fuelLoader.getMaxEnergy())).applyTextStyle(TextFormatting.YELLOW));
        this.electricInfoRegion.tooltips = electricityDesc;
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 112;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 65;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
        this.buttons.add(this.buttonLoadFuel = new Button(this.width / 2 + 2, this.height / 2 - 49, 76, 20, I18n.format("gui.button.loadfuel.name"), (button) ->
                GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, GCCoreUtil.getDimensionType(this.fuelLoader.getWorld()), new Object[]{this.fuelLoader.getPos(), 0}))));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.font.drawString(this.title.getFormattedText(), 60, 10, 4210752);
        this.buttonLoadFuel.active = /*this.fuelLoader.disableCooldown == 0 &&*/ this.fuelLoader.fuelTank.getFluid() != FluidStack.EMPTY && this.fuelLoader.fuelTank.getFluid().getAmount() > 0;
        this.buttonLoadFuel.setMessage(!false ? I18n.format("gui.button.stoploading.name") : I18n.format("gui.button.loadfuel.name"));
        this.font.drawString(new TranslationTextComponent("gui.message.status.name").appendSibling(this.getStatus()).getFormattedText(), 28, 45 + 23 - 46, 4210752);
        //this.font.drawString("" + this.fuelLoader.storage.getMaxExtract(), 28, 56 + 23 - 46, 4210752);
        //		this.font.drawString(ElectricityDisplay.getDisplay(this.fuelLoader.getVoltage(), ElectricUnit.VOLTAGE), 28, 68 + 23 - 46, 4210752);
        this.font.drawString(I18n.format("container.inventory"), 8, this.ySize - 118 + 2 + 11, 4210752);
    }

    private ITextComponent getStatus()
    {
        if (this.fuelLoader.fuelTank.getFluid() == FluidStack.EMPTY || this.fuelLoader.fuelTank.getFluid().getAmount() == 0)
        {
            return new TranslationTextComponent("gui.status.nofuel.name").applyTextStyle(TextFormatting.DARK_RED);
        }

        return new TranslationTextComponent("gui.galacticraftcore.fuel_loader.gui_status");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GuiFuelLoader.fuelLoaderTexture);
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.blit(var5, var6 + 5, 0, 0, this.xSize, 181);

        final int fuelLevel = this.fuelLoader.getScaledFuelLevel(38);
        this.blit((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 17 + 54 - fuelLevel, 176, 38 - fuelLevel, 16, fuelLevel);

        List<ITextComponent> electricityDesc = new ArrayList<>();
        electricityDesc.add(new TranslationTextComponent("gui.energy_storage.desc.0"));
        EnergyDisplayHelper.getEnergyDisplayTooltip(this.fuelLoader.getStoredEnergy(), this.fuelLoader.getMaxEnergy(), electricityDesc);
//		electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.fuelLoader.getEnergyStoredGC()) + " / " + (int) Math.floor(this.fuelLoader.getMaxEnergyStoredGC())));
        this.electricInfoRegion.tooltips = electricityDesc;

        if (this.fuelLoader.getStoredEnergy() > 0)
        {
            this.blit(var5 + 99, var6 + 65, 192, 7, 11, 10);
        }

        this.blit(var5 + 113, var6 + 66, 192, 0, Math.min(this.fuelLoader.getScaledElecticalLevel(54), 54), 7);
    }
}
