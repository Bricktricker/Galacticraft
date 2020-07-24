//package micdoodle8.mods.galacticraft.planets.asteroids.client.render.tile;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.blaze3d.systems.RenderSystem;
//import micdoodle8.mods.galacticraft.core.util.ClientUtil;
//import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;
//import micdoodle8.mods.galacticraft.planets.asteroids.tile.TileEntityShortRangeTelepad;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.RenderHelper;
//import net.minecraft.client.renderer.texture.AtlasTexture;
//import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.client.model.ModelLoader;
//import net.minecraftforge.client.model.obj.OBJModel;
//import org.lwjgl.opengl.GL11;
//
//@OnlyIn(Dist.CLIENT)
//public class TileEntityShortRangeTelepadRenderer extends TileEntityRenderer<TileEntityShortRangeTelepad>
//{
//    private static OBJModel.OBJBakedModel teleporterTop;
//    private static OBJModel.OBJBakedModel teleporterBottom;
//
//    public static void updateModels(ModelLoader modelLoader)
//    {
//        try
//        {
//            teleporterTop = ClientUtil.modelFromOBJ(modelLoader, new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "block/telepad_short.obj"), ImmutableList.of("Top", "Connector"));
//            teleporterBottom = ClientUtil.modelFromOBJ(modelLoader, new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "block/telepad_short.obj"), ImmutableList.of("Bottom"));
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void render(TileEntityShortRangeTelepad tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage)
//    {
//        RenderSystem.pushMatrix();
//
//        RenderHelper.disableStandardItemLighting();
//        this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
//        if (Minecraft.isAmbientOcclusionEnabled())
//        {
//            RenderSystem.shadeModel(7425);
//        }
//        else
//        {
//            RenderSystem.shadeModel(7424);
//        }
//
//        RenderSystem.translatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
//
//        RenderSystem.scalef(0.745F, 1.0F, 0.745F);
//
//        ClientUtil.drawBakedModel(teleporterBottom);
//        RenderSystem.translatef(0.0F, -0.7F, 0.0F);
//        ClientUtil.drawBakedModel(teleporterTop);
//
//        RenderSystem.popMatrix();
//    }
//}
