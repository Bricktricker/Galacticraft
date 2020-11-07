package micdoodle8.mods.galacticraft.core.client.gui.container;

import com.mojang.blaze3d.systems.RenderSystem;
import micdoodle8.mods.galacticraft.api.prefab.entity.RocketEntity;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.inventory.RocketInventoryContainer;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class GuiRocketInventory extends GuiContainerGC<RocketInventoryContainer>
{
    private static final ResourceLocation[] rocketTextures = new ResourceLocation[4];

    static
    {
        for (int i = 0; i < 4; i++)
        {
            GuiRocketInventory.rocketTextures[i] = new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/rocket_" + i * 18 + ".png");
        }
    }

    public GuiRocketInventory(RocketInventoryContainer container, PlayerInventory playerInv, ITextComponent title)
    {
        super(container, playerInv, title);
        this.passEvents = false;
        this.ySize = container.getRocket().getInventoryCapacity() <= 3 ? 132 : 145 + container.getRocket().getInventoryCapacity() * 2;
    }

    @Override
    protected void init()
    {
        super.init();
        List<ITextComponent> fuelTankDesc = new ArrayList<>();
        fuelTankDesc.add(new TranslationTextComponent("gui.fuel_tank.desc.0"));
        fuelTankDesc.add(new TranslationTextComponent("gui.fuel_tank.desc.1"));
        this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + (((RocketEntity) this.minecraft.player.getRidingEntity()).getInventoryCapacity() == 2 ? 70 : 71), (this.height - this.ySize) / 2 + 6, 36, 40, fuelTankDesc, this.width, this.height, this));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.font.drawString(I18n.format("gui.message.fuel.name"), 8, 2 + 3, 4210752);

        this.font.drawString(this.title.getFormattedText(), 8, 34 + 2 + 3, 4210752);
        
        this.font.drawString(GCCoreUtil.translate("gui.message.fuel.name") + ":", 125, 15 + 3, 4210752);
        final float percentage = Math.round(container.getStoredFuel() * 100 / (float)container.getMaxFuel());
        final String color = percentage > 80.0D ? EnumColor.BRIGHT_GREEN.getCode() : percentage > 40.0D ? EnumColor.ORANGE.getCode() : EnumColor.RED.getCode();
        final String str = percentage + "% " + GCCoreUtil.translate("gui.message.full.name");
        this.font.drawString(color + str, 117 - str.length() / 2, 20 + 8, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.minecraft.getTextureManager().bindTexture(GuiRocketInventory.rocketTextures[(this.container.getRocket().getInventoryCapacity() - 2) / 18]);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        final int var5 = (this.width - this.xSize) / 2;
        final int var6 = (this.height - this.ySize) / 2;
        this.blit(var5, var6, 0, 0, 176, this.ySize);
        
        final int fuelLevel = Math.round(container.getStoredFuel() * 38 / (float)container.getMaxFuel());
        this.blit((this.width - this.xSize) / 2 + (container.getRocket().getInventoryCapacity() == 2 ? 71 : 72), (this.height - this.ySize) / 2 + 45 - fuelLevel, 176, 38 - fuelLevel, 42, fuelLevel);
    }
}
