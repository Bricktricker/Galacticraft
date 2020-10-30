package micdoodle8.mods.galacticraft.core.client.render.entities;

import micdoodle8.mods.galacticraft.api.prefab.entity.RocketTier1;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.model.ModelRocketTier1;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

public class RenderTier1Rocket extends EntityRenderer<RocketTier1> {
	private final ResourceLocation spaceshipTexture;

	protected ModelRocketTier1 rocketModel;

	public RenderTier1Rocket(EntityRendererManager renderManager) {
		super(renderManager);

		this.rocketModel = new ModelRocketTier1();
		this.spaceshipTexture = new ResourceLocation(Constants.MOD_ID_CORE, "textures/model/rocket_t1.png");
		this.shadowSize = 0.9F;
	}

	@Override
	public void render(RocketTier1 entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.push();

		final float var24 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
		final float var25 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;

		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		matrixStackIn.rotate(new Quaternion(new Vector3f(0, 0, 1), -var24, true));
		matrixStackIn.rotate(new Quaternion(new Vector3f(0, 1, 0), -var25, true));

		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		matrixStackIn.translate(0, 1.55F, 0);

		matrixStackIn.scale(-1.0F, -1.0F, 1.0F); // Here or below if?

		final float var28 = entity.getRollAmplitude() - partialTicks;
		if(var28 > 0.0F) {
			final float i = entity.isLaunched() ? (5 - MathHelper.floor(entity.getTimeUntilLaunch() / 85)) / 10F : 0.3F;
			matrixStackIn.rotate(new Quaternion(Vector3f.XP, MathHelper.sin(var28) * var28 * i * partialTicks, true));
			matrixStackIn.rotate(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), MathHelper.sin(var28) * var28 * i * partialTicks, true));
		}

		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.rocketModel.getRenderType(this.getEntityTexture(entity)));
		this.rocketModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		matrixStackIn.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(RocketTier1 entity) {
		return this.spaceshipTexture;
	}
}
