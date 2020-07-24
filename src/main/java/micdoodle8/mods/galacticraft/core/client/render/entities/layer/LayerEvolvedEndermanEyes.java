//package micdoodle8.mods.galacticraft.core.client.render.entities.layer;
//
//import com.mojang.blaze3d.platform.GLX;
//import micdoodle8.mods.galacticraft.core.Constants;
//import micdoodle8.mods.galacticraft.core.client.model.ModelEvolvedEnderman;
//import micdoodle8.mods.galacticraft.core.client.render.entities.RenderEvolvedEnderman;
//import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedEnderman;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraft.client.renderer.entity.layers.LayerRenderer;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//@OnlyIn(Dist.CLIENT)
//public class LayerEvolvedEndermanEyes extends LayerRenderer<EntityEvolvedEnderman, ModelEvolvedEnderman>
//{
//    private static final ResourceLocation EYES_TEXTURE = new ResourceLocation(Constants.MOD_ID_CORE, "textures/model/evolved_enderman_eyes.png");
//
//    private final RenderEvolvedEnderman render;
//
//    public LayerEvolvedEndermanEyes(RenderEvolvedEnderman render)
//    {
//        super(render);
//        this.render = render;
//    }
//
//    @Override
//    public boolean shouldCombineTextures()
//    {
//        return false;
//    }
//
//    @Override
//    public void render(EntityEvolvedEnderman entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
//    {
//        this.bindTexture(EYES_TEXTURE);
//        RenderSystem.enableBlend();
//        RenderSystem.disableAlphaTest();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
//        RenderSystem.disableLighting();
//        RenderSystem.depthMask(!entity.isInvisible());
//        int i = 61680;
//        int j = 61680;
//        int k = 0;
//        RenderSystem.glMultiTexCoord2f(33985, 61680.0F, 0.0F);
//        RenderSystem.enableLighting();
//        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
//        gamerenderer.setupFogColor(true);
//        this.getEntityModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//        gamerenderer.setupFogColor(false);
//        this.func_215334_a(entity);
//        RenderSystem.depthMask(true);
//        RenderSystem.disableBlend();
//        RenderSystem.enableAlphaTest();
//    }
//}