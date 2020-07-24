package micdoodle8.mods.galacticraft.core.client.gui.container;

import com.mojang.blaze3d.systems.RenderSystem;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.inventory.ContainerEnergyStorageModule;
import micdoodle8.mods.galacticraft.core.tile.TileEntityEnergyStorageModule;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiEnergyStorageModule extends GuiContainerGC<ContainerEnergyStorageModule>
{
    private static final ResourceLocation batteryBoxTexture = new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/energy_storage_module.png");

    private TileEntityEnergyStorageModule storageModule;

    public GuiEnergyStorageModule(ContainerEnergyStorageModule container, PlayerInventory playerInv, ITextComponent title)
    {
        super(container, playerInv, title);
//        super(new ContainerEnergyStorageModule(playerInv, storageModule), playerInv, new TranslationTextComponent(storageModule.getTierGC() == 1 ? "tile.machine.1.name" : "tile.machine.8.name"));
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of
     * the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.font.drawString(this.title.getFormattedText(), this.xSize / 2 - this.font.getStringWidth(this.title.getFormattedText()) / 2, 6, 4210752);
        float energy = this.storageModule.getEnergyStoredGC();
        if (energy + 49 > this.storageModule.getMaxEnergyStoredGC())
        {
            energy = this.storageModule.getMaxEnergyStoredGC();
        }
        String displayStr = EnergyDisplayHelper.getEnergyDisplayS(energy);
        this.font.drawString(displayStr, 122 - this.font.getStringWidth(displayStr) / 2, 25, 4210752);
        displayStr = GCCoreUtil.translate("gui.message.of.name") + " " + EnergyDisplayHelper.getEnergyDisplayS(this.storageModule.getMaxEnergyStoredGC());
        this.font.drawString(displayStr, 122 - this.font.getStringWidth(displayStr) / 2, 34, 4210752);
        displayStr = GCCoreUtil.translate("gui.max_output.desc") + ": " + EnergyDisplayHelper.getEnergyDisplayS(this.storageModule.storage.getMaxExtract()) + "/t";
        this.font.drawString(displayStr, 114 - this.font.getStringWidth(displayStr) / 2, 64, 4210752);
        this.font.drawString(GCCoreUtil.translate("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.minecraft.textureManager.bindTexture(GuiEnergyStorageModule.batteryBoxTexture);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
        int containerHeight = (this.height - this.ySize) / 2;
        // Background energy bar
        this.blit(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        // Foreground energy bar
        int scale = (int) ((this.storageModule.getEnergyStoredGC() + 49) / this.storageModule.getMaxEnergyStoredGC() * 72);
        this.blit(containerWidth + 87, containerHeight + 52, 176, 0, scale, 3);
    }
}
