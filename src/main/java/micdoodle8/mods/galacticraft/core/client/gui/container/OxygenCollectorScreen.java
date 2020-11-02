package micdoodle8.mods.galacticraft.core.client.gui.container;

import com.mojang.blaze3d.systems.RenderSystem;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.inventory.OxygenCollectorContainer;
import micdoodle8.mods.galacticraft.core.tile.OxygenCollectorTileEntity;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class OxygenCollectorScreen extends GuiContainerGC<OxygenCollectorContainer>
{
    private static final ResourceLocation collectorTexture = new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/oxygen.png");

    private final OxygenCollectorTileEntity collector;

    private final GuiElementInfoRegion oxygenInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 112, (this.height - this.ySize) / 2 + 24, 56, 9, new ArrayList<>(), this.width, this.height, this);
    private final GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 112, (this.height - this.ySize) / 2 + 37, 56, 9, new ArrayList<>(), this.width, this.height, this);

    public OxygenCollectorScreen(OxygenCollectorContainer container, PlayerInventory playerInv, ITextComponent title)
    {
        super(container, playerInv, title);
//        super(new ContainerOxygenCollector(playerInv, collector), playerInv, new TranslationTextComponent("container.oxygencollector.name"));
        this.collector = container.getCollector();
        this.ySize = 180;
    }

    @Override
    protected void init()
    {
        super.init();
        List<ITextComponent> batterySlotDesc = new ArrayList<>();
        batterySlotDesc.add(new TranslationTextComponent("gui.battery_slot.desc.0"));
        batterySlotDesc.add(new TranslationTextComponent("gui.battery_slot.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 31, (this.height - this.ySize) / 2 + 26, 18, 18, batterySlotDesc, this.width, this.height, this));
        this.oxygenInfoRegion.xPosition = (this.width - this.xSize) / 2 + 112;
        this.oxygenInfoRegion.yPosition = (this.height - this.ySize) / 2 + 24;
        this.oxygenInfoRegion.parentWidth = this.width;
        this.oxygenInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.oxygenInfoRegion);
        this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 112;
        this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 37;
        this.electricInfoRegion.parentWidth = this.width;
        this.electricInfoRegion.parentHeight = this.height;
        this.infoRegions.add(this.electricInfoRegion);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.font.drawString(this.title.getFormattedText(), 8, 10, 4210752);
        GCCoreUtil.drawStringRightAligned(GCCoreUtil.translate("gui.message.out.name") + ":", 99, 25, 4210752, this.font);
        GCCoreUtil.drawStringRightAligned(GCCoreUtil.translate("gui.message.in.name") + ":", 99, 37, 4210752, this.font);
        GCCoreUtil.drawStringCentered(GCCoreUtil.translate("gui.message.status.name") + ": " + this.getStatus(), this.xSize / 2, 50, 4210752, this.font);
        String status = GCCoreUtil.translate("gui.status.collecting.name") + ": " + (int) (0.5F + Math.min(this.collector.getLastOxygenCollected() * 20F, OxygenCollectorTileEntity.OUTPUT_PER_TICK * 20F)) + GCCoreUtil.translate("gui.per_second");
        GCCoreUtil.drawStringCentered(status, this.xSize / 2, 60, 4210752, this.font);
        this.font.drawString(GCCoreUtil.translate("container.inventory"), 8, this.ySize - 90 + 2, 4210752);
    }

    private String getStatus()
    {
        String returnValue = "gui.galacticraftcore.oxygen_collector.gui_status";

        if (returnValue.equals(EnumColor.DARK_GREEN + GCCoreUtil.translate("gui.status.active.name")) && this.collector.getLastOxygenCollected() <= 0.0F)
        {
            return EnumColor.DARK_RED + GCCoreUtil.translate("gui.status.missingleaves.name");
        }

        return returnValue;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(OxygenCollectorScreen.collectorTexture);
        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.blit(var5, var6 + 5, 0, 0, this.xSize, 181);

        if (this.collector != null)
        {
            int scale = this.collector.getCappedScaledOxygenLevel(54);
            this.blit(var5 + 113, var6 + 25, 197, 7, Math.min(scale, 54), 7);
            scale = this.collector.getScaledElecticalLevel(54);
            this.blit(var5 + 113, var6 + 38, 197, 0, Math.min(scale, 54), 7);

            if (this.collector.getStoredEnergy() > 0)
            {
                this.blit(var5 + 99, var6 + 37, 176, 0, 11, 10);
            }

            if (this.collector.getOxygenStored() > 0)
            {
                this.blit(var5 + 100, var6 + 24, 187, 0, 10, 10);
            }

            List<ITextComponent> oxygenDesc = new ArrayList<>();
            oxygenDesc.add(new TranslationTextComponent("gui.oxygen_storage.desc.0"));
            oxygenDesc.add(new TranslationTextComponent("gui.oxygen_storage.desc.1", (int) Math.floor(this.collector.getOxygenStored()), (int) Math.floor(this.collector.getMaxOxygenStored())).applyTextStyle(TextFormatting.YELLOW));
            this.oxygenInfoRegion.tooltips = oxygenDesc;

            List<ITextComponent> electricityDesc = new ArrayList<>();
            electricityDesc.add(new TranslationTextComponent("gui.energy_storage.desc.0"));
            EnergyDisplayHelper.getEnergyDisplayTooltip(this.collector.getStoredEnergy(), this.collector.getMaxEnergy(), electricityDesc);
//			electricityDesc.add(EnumColor.YELLOW + GCCoreUtil.translate("gui.energy_storage.desc.1") + ((int) Math.floor(this.collector.getEnergyStoredGC()) + " / " + (int) Math.floor(this.collector.getMaxEnergyStoredGC())));
            this.electricInfoRegion.tooltips = electricityDesc;
        }
    }
}
