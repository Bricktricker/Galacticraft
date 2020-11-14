package micdoodle8.mods.galacticraft.core.client.render.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.entities.MoonBuggyEntity;
import micdoodle8.mods.galacticraft.core.entities.MoonBuggyEntity.BuggyType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.data.EmptyModelData;

public class BuggyRenderer extends EntityRenderer<MoonBuggyEntity> {
	
	public static final List<Pair<ResourceLocation, BiConsumer<BuggyRenderer, List<BakedQuad>>>> MODELS = Arrays.asList(
			Pair.of(new ResourceLocation(Constants.MOD_ID_CORE, "buggy/main_body"), (r, q) -> r.mainModel = q),
			Pair.of(new ResourceLocation(Constants.MOD_ID_CORE, "buggy/radar_dish"), (r, q) -> r.radarDish = q),
			Pair.of(new ResourceLocation(Constants.MOD_ID_CORE, "buggy/wheel_left"), (r, q) -> r.wheelLeft = q),
			Pair.of(new ResourceLocation(Constants.MOD_ID_CORE, "buggy/wheel_right"), (r, q) -> r.wheelRight = q),
			Pair.of(new ResourceLocation(Constants.MOD_ID_CORE, "buggy/wheel_right_cover"), (r, q) -> r.wheelRightCover = q),
			Pair.of(new ResourceLocation(Constants.MOD_ID_CORE, "buggy/wheel_left_cover"), (r, q) -> r.wheelLeftCover = q),
			Pair.of(new ResourceLocation(Constants.MOD_ID_CORE, "buggy/cargo"), (r, q) -> r.cargo = q)
			);
	
	private List<BakedQuad> mainModel;
	private List<BakedQuad> radarDish;
	private List<BakedQuad> wheelRight;
	private List<BakedQuad> wheelLeft;
	private List<BakedQuad> wheelRightCover;
	private List<BakedQuad> wheelLeftCover;
	private List<BakedQuad> cargo;
	
	private final RenderType renderType;

	public BuggyRenderer(EntityRendererManager renderManager) {
		super(renderManager);
		this.shadowSize = 1.0f;
		this.renderType = RenderType.getEntityCutoutNoCull(this.getEntityTexture(null));
	}
	
	@Override
	public void render(MoonBuggyEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int packedLightIn) {
		if(mainModel == null) {
			this.loadModels();
		}
		
		float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;	
		matrixStack.push();
		
		matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		matrixStack.rotate(new Quaternion(new Vector3f(0, 0, 1), -pitch, true));
		final float damage = entity.getDamageTaken() - partialTicks;
		if(damage > 0.0F) {
			matrixStack.rotate(new Quaternion(Vector3f.XP, MathHelper.sin(damage) * damage * 0.3F * partialTicks, true));
			matrixStack.rotate(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), MathHelper.sin(damage) * damage * 0.3F * partialTicks, true));
		}
		
		matrixStack.scale(0.41f, 0.41f, 0.41f);
		
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.renderType);

		//main model
		for(BakedQuad q : mainModel) {
			ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
		}
		
		//front wheel right
		matrixStack.push();
        float wheelRotationX = entity.getWheelRotationX();
        float wheelRotationZ = entity.getWheelRotationZ();
		matrixStack.translate(1.25, 0.976, -2.727F);
		matrixStack.rotate(Vector3f.YP.rotationDegrees(wheelRotationZ));
		for(BakedQuad q : wheelRightCover) {
			ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
		}
		matrixStack.rotate(Vector3f.XP.rotationDegrees(wheelRotationX));
		for(BakedQuad q : wheelRight) {
			ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
		}
		matrixStack.pop();
		
		//front wheel left
		matrixStack.push();
		matrixStack.translate(-1.25, 0.976, -2.727F);
		matrixStack.rotate(Vector3f.YP.rotationDegrees(wheelRotationZ));
		for(BakedQuad q : wheelLeftCover) {
			ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
		}
		matrixStack.rotate(Vector3f.XP.rotationDegrees(wheelRotationX));
		for(BakedQuad q : wheelLeft) {
			ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
		}
		matrixStack.pop();
		
		//back wheels right
		matrixStack.push();
		matrixStack.translate(1.9, 0.976, 2.727F);
		matrixStack.rotate(Vector3f.YP.rotationDegrees(-wheelRotationZ));
		matrixStack.rotate(Vector3f.XP.rotationDegrees(wheelRotationX));
		for(BakedQuad q : wheelRight) {
			ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
		}
		matrixStack.pop();
		
		//back wheels left
		matrixStack.push();
		matrixStack.translate(-1.9, 0.976, 2.727F);
		matrixStack.rotate(Vector3f.YP.rotationDegrees(-wheelRotationZ));
		matrixStack.rotate(Vector3f.XP.rotationDegrees(wheelRotationX));
		for(BakedQuad q : wheelLeft) {
			ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
		}
		matrixStack.pop();
		
		//radar dish
		matrixStack.push();
		matrixStack.translate(-1.178, 4.1, -2.397);
		int ticks = entity.ticksExisted + entity.getEntityId() * 10000;
		matrixStack.rotate(Vector3f.XP.rotationDegrees((float) Math.sin(ticks * 0.05) * 50.0F));
		matrixStack.rotate(Vector3f.ZP.rotationDegrees((float) Math.cos(ticks * 0.1) * 50.0F));
		for(BakedQuad q : radarDish) {
			ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
		}
		matrixStack.pop();
		
		BuggyType buggyType = entity.getBuggyType();
		int type = 0;
		if(buggyType != null) {
			type = buggyType.ordinal();
		}
		if(type > 0) {
			matrixStack.push();
			matrixStack.translate(-1.3, 0, 0);
			for(BakedQuad q : cargo) {
				ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
			}
			matrixStack.pop();
			if(type > 1) {
				for(BakedQuad q : cargo) {
					ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
				}
				if(type > 2) {
					matrixStack.push();
					matrixStack.translate(1.3, 0, 0);
					for(BakedQuad q : cargo) {
						ivertexbuilder.addQuad(matrixStack.getLast(), q, 1.0f, 1.0f, 1.0f, packedLightIn, OverlayTexture.NO_OVERLAY);	
					}
					matrixStack.pop();
				}
			}
		}
		
		matrixStack.pop();
	}

	@SuppressWarnings("deprecation")
	@Override
	public ResourceLocation getEntityTexture(MoonBuggyEntity entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}
	
	private void loadModels() {
		Random random = new Random();
		ModelManager modelManager = Minecraft.getInstance().getModelManager();
		
		for(int i = 0; i < MODELS.size(); i++) {
			IBakedModel model = modelManager.getModel(MODELS.get(i).getLeft());
			List<BakedQuad> quads = model.getQuads(null, null, random, EmptyModelData.INSTANCE);
			MODELS.get(i).getRight().accept(this, quads);
		}
	}

}
