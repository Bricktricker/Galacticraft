//package micdoodle8.mods.galacticraft.planets.asteroids.client.render.tile;
//
//import com.google.common.base.Function;
//import com.google.common.collect.ImmutableList;
//import com.mojang.blaze3d.systems.RenderSystem;
//import micdoodle8.mods.galacticraft.core.client.model.OBJLoaderGC;
//import micdoodle8.mods.galacticraft.core.util.ClientUtil;
//import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;
//import micdoodle8.mods.galacticraft.planets.asteroids.tile.TileEntityBeamReflector;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.RenderHelper;
//import net.minecraft.client.renderer.texture.AtlasTexture;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.client.model.IModel;
//import net.minecraftforge.client.model.ModelLoader;
//import net.minecraftforge.client.model.obj.OBJModel;
//import org.lwjgl.opengl.GL11;
//
//@OnlyIn(Dist.CLIENT)
//public class TileEntityBeamReflectorRenderer extends TileEntityRenderer<TileEntityBeamReflector>
//{
//    private static OBJModel.OBJBakedModel reflectorModelBase;
//    private static OBJModel.OBJBakedModel reflectorModelAxle;
//    private static OBJModel.OBJBakedModel reflectorModelEnergyBlaster;
//    private static OBJModel.OBJBakedModel reflectorModelRing;
//
//    public static void updateModels(ModelLoader modelLoader)
//    {
//        try
//        {
//            reflectorModelBase = ClientUtil.modelFromOBJ(modelLoader, new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "block/reflector.obj"), ImmutableList.of("Base"));
//            reflectorModelAxle = ClientUtil.modelFromOBJ(modelLoader, new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "block/reflector.obj"), ImmutableList.of("Axle"));
//            reflectorModelEnergyBlaster = ClientUtil.modelFromOBJ(modelLoader, new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "block/reflector.obj"), ImmutableList.of("EnergyBlaster"));
//            reflectorModelRing = ClientUtil.modelFromOBJ(modelLoader, new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "block/reflector.obj"), ImmutableList.of("Ring"));
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void render(TileEntityBeamReflector tile, double x, double y, double z, float partialTicks, int destroyStage)
//    {
//        RenderSystem.disableRescaleNormal();
//        RenderSystem.pushMatrix();
//        RenderSystem.translatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
//        RenderSystem.scalef(0.5F, 0.5F, 0.5F);
//        this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
//
//        if (Minecraft.isAmbientOcclusionEnabled())
//        {
//            RenderSystem.shadeModel(7425);
//        }
//        else
//        {
//            RenderSystem.shadeModel(7424);
//        }
//
//        ClientUtil.drawBakedModel(reflectorModelBase);
//        RenderSystem.rotatef(tile.yaw, 0, 1, 0);
//        ClientUtil.drawBakedModel(reflectorModelAxle);
//        float dX = 0.0F;
//        float dY = 1.13228F;
//        float dZ = 0.0F;
//        RenderSystem.translatef(dX, dY, dZ);
//        RenderSystem.rotatef(tile.pitch, 1, 0, 0);
//        RenderSystem.translatef(-dX, -dY, -dZ);
//        ClientUtil.drawBakedModel(reflectorModelEnergyBlaster);
//        RenderSystem.translatef(dX, dY, dZ);
//        RenderSystem.rotatef(tile.ticks * 5, 0, 0, 1);
//        RenderSystem.translatef(-dX, -dY, -dZ);
//        ClientUtil.drawBakedModel(reflectorModelRing);
//        RenderSystem.popMatrix();
//        RenderHelper.enableStandardItemLighting();
//    }
//}
