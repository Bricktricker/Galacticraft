package micdoodle8.mods.galacticraft.core.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IRenderHandler;

/**
 * Dummy Cloud renderer to render no clouds
 */
public class CloudRenderer implements IRenderHandler
{
    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(int ticks, float partialTicks, ClientWorld world, Minecraft mc)
    {
        // Do nothing
    }
}
