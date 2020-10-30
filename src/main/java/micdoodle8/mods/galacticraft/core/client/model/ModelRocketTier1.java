package micdoodle8.mods.galacticraft.core.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import micdoodle8.mods.galacticraft.api.prefab.entity.RocketTier1;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelRocketTier1 extends EntityModel<RocketTier1>
{
    ModelRenderer insideRoof;
    ModelRenderer rocketBase1;
    ModelRenderer rocketBase2;
    ModelRenderer tip;
    ModelRenderer wing4d;
    ModelRenderer wing4c;
    ModelRenderer wing4e;
    ModelRenderer wing4b;
    ModelRenderer wing4a;
    ModelRenderer wing1a;
    ModelRenderer wing1b;
    ModelRenderer wing1c;
    ModelRenderer wing1e;
    ModelRenderer wing1d;
    ModelRenderer wing2e;
    ModelRenderer wing2d;
    ModelRenderer wing2c;
    ModelRenderer wing2b;
    ModelRenderer wing2a;
    ModelRenderer wing3e;
    ModelRenderer wing3d;
    ModelRenderer wing3c;
    ModelRenderer wing3b;
    ModelRenderer wing3a;
    ModelRenderer top1;
    ModelRenderer top2;
    ModelRenderer top3;
    ModelRenderer top4;
    ModelRenderer top5;
    ModelRenderer top6;
    ModelRenderer top7;
    ModelRenderer insideBottom;
    ModelRenderer insideLeft;
    ModelRenderer insidetop;
    ModelRenderer rocketBase3;
    ModelRenderer insideRight;
    ModelRenderer insideSideLeft;
    ModelRenderer insideSideRight;
    ModelRenderer insideSideBack;
    ModelRenderer insideFloor;

    public ModelRocketTier1()
    {
        this.textureWidth = 256;
        this.textureHeight = 256;

        this.top7 = new ModelRenderer(this, 240, 138);
        this.top7.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.top7.addBox(-2.0F, -60.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.top6 = new ModelRenderer(this, 232, 130);
        this.top6.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.top6.addBox(-3.0F, -58.0F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.wing3e = new ModelRenderer(this, 66, 0);
        this.wing3e.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing3e.addBox(-1.0F, -15.0F, 9.1F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wing2c = new ModelRenderer(this, 66, 0);
        this.wing2c.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing2c.addBox(-15.0F, -12.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.insideLeft = new ModelRenderer(this, 103, 0);
        this.insideLeft.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.insideLeft.addBox(3.9F, -46.0F, -8.9F, 4.9999995F, 41.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.wing4e = new ModelRenderer(this, 66, 0);
        this.wing4e.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing4e.addBox(9.1F, -15.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.top3 = new ModelRenderer(this, 208, 94);
        this.top3.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.top3.addBox(-6.0F, -52.0F, -6.0F, 12.0F, 2.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.wing1e = new ModelRenderer(this, 66, 0);
        this.wing1e.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing1e.addBox(-1.0F, -15.0F, -11.1F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.insideBottom = new ModelRenderer(this, 85, 18);
        this.insideBottom.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.insideBottom.addBox(-3.9F, -22.0F, -8.9F, 8.0F, 17.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.top4 = new ModelRenderer(this, 216, 108);
        this.top4.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.top4.addBox(-5.0F, -54.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.top2 = new ModelRenderer(this, 200, 78);
        this.top2.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.top2.addBox(-7.0F, -50.0F, -7.0F, 14.0F, 2.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.wing1d = new ModelRenderer(this, 66, 0);
        this.wing1d.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing1d.addBox(-1.0F, -14.0F, -13.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.insideFloor = new ModelRenderer(this, 0, 40);
        this.insideFloor.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.insideFloor.addBox(-9.0F, -4.0F, -9.0F, 18.0F, 1.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.insideRoof = new ModelRenderer(this, 0, 59);
        this.insideRoof.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.insideRoof.addBox(-9.0F, -45.0F, -9.0F, 18.0F, 1.0F, 18.0F, 0.0F, 0.0F, 0.0F);
        this.top5 = new ModelRenderer(this, 224, 120);
        this.top5.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.top5.addBox(-4.0F, -56.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.wing2a = new ModelRenderer(this, 74, 0);
        this.wing2a.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing2a.addBox(-18.0F, -14.0F, -1.0F, 1.0F, 15.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wing1b = new ModelRenderer(this, 66, 0);
        this.wing1b.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing1b.addBox(-1.0F, -9.0F, -17.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.top1 = new ModelRenderer(this, 192, 60);
        this.top1.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.top1.addBox(-8.0F, -48.0F, -8.0F, 16.0F, 2.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.wing4c = new ModelRenderer(this, 66, 0);
        this.wing4c.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing4c.addBox(13.0F, -12.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rocketBase3 = new ModelRenderer(this, 0, 28);
        this.rocketBase3.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.rocketBase3.addBox(-5.0F, -4.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, 0.0F, 0.0F);
        this.wing3c = new ModelRenderer(this, 66, 0);
        this.wing3c.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing3c.addBox(-1.0F, -12.0F, 13.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rocketBase1 = new ModelRenderer(this, 0, 0);
        this.rocketBase1.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.rocketBase1.addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.wing3a = new ModelRenderer(this, 60, 0);
        this.wing3a.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing3a.addBox(-1.0F, -14.0F, 17.0F, 2.0F, 15.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.tip = new ModelRenderer(this, 248, 144);
        this.tip.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.tip.addBox(-1.0F, -76.0F, -1.0F, 2.0F, 18.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.insideSideRight = new ModelRenderer(this, 120, 0);
        this.insideSideRight.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.insideSideRight.addBox(-8.9F, -46.0F, -7.9F, 1.0F, 41.0F, 16.0F, 0.0F, 0.0F, 0.0F);
        this.wing2d = new ModelRenderer(this, 66, 0);
        this.wing2d.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing2d.addBox(-13.0F, -14.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.insideRight = new ModelRenderer(this, 103, 42);
        this.insideRight.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.insideRight.addBox(-8.9F, -46.0F, -8.9F, 5.0F, 41.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.insideSideLeft = new ModelRenderer(this, 119, 57);
        this.insideSideLeft.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.insideSideLeft.addBox(8.1F, -46.0F, -7.9F, 1.0F, 41.0F, 17.0F, 0.0F, 0.0F, 0.0F);
        this.wing3b = new ModelRenderer(this, 66, 0);
        this.wing3b.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing3b.addBox(-1.0F, -9.0F, 15.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wing1c = new ModelRenderer(this, 66, 0);
        this.wing1c.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing1c.addBox(-1.0F, -12.0F, -15.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wing4b = new ModelRenderer(this, 66, 0);
        this.wing4b.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing4b.addBox(15.0F, -9.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wing2b = new ModelRenderer(this, 66, 0);
        this.wing2b.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing2b.addBox(-17.0F, -9.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wing3d = new ModelRenderer(this, 66, 0);
        this.wing3d.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing3d.addBox(-1.0F, -14.0F, 11.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wing2e = new ModelRenderer(this, 66, 0);
        this.wing2e.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing2e.addBox(-11.1F, -15.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.wing4a = new ModelRenderer(this, 74, 0);
        this.wing4a.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing4a.addBox(17.0F, -14.0F, -1.0F, 1.0F, 15.0F, 2.0F, 0.0F, 0.0F, 0.0F);
        this.rocketBase2 = new ModelRenderer(this, 0, 15);
        this.rocketBase2.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.rocketBase2.addBox(-6.0F, -2.0F, -6.0F, 12.0F, 1.0F, 12.0F, 0.0F, 0.0F, 0.0F);
        this.wing1a = new ModelRenderer(this, 60, 0);
        this.wing1a.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing1a.addBox(-1.0F, -14.0F, -18.0F, 2.0F, 15.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.insideSideBack = new ModelRenderer(this, 120, 114);
        this.insideSideBack.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.insideSideBack.addBox(-8.9F, -46.0F, 8.1F, 17.0F, 41.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.insidetop = new ModelRenderer(this, 85, 0);
        this.insidetop.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.insidetop.addBox(-3.9F, -46.0F, -8.9F, 8.0F, 17.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.wing4d = new ModelRenderer(this, 66, 0);
        this.wing4d.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.wing4d.addBox(11.0F, -14.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, 0.0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

	@Override
	public void setRotationAngles(RocketTier1 entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.insideRoof.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		this.insideRoof.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.rocketBase1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.rocketBase2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.tip.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing4d.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing4c.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing4e.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing4b.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing4a.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing1a.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing1b.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing1c.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing1e.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing1d.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing2e.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing2d.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing2c.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing2b.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing2a.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing3e.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing3d.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing3c.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing3b.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.wing3a.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.top1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.top2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.top3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.top4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.top5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.top6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.top7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.insideBottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.insideLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.insidetop.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.rocketBase3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.insideRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.insideSideLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.insideSideRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.insideSideBack.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.insideFloor.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
}
