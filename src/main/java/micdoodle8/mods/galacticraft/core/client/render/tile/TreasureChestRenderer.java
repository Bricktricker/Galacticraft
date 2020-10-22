package micdoodle8.mods.galacticraft.core.client.render.tile;

import java.lang.reflect.Field;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.tileentity.ChestTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TreasureChestRenderer extends ChestTileEntityRenderer<ChestTileEntity> {

	//TODO: register in TextureStitchEvent.Pre
	private static final ResourceLocation TREASURE_CHEST_TEXTURE = new ResourceLocation(Constants.MOD_ID_CORE, "entity/treasure");
	private Material CHEST_MATERIAL = new Material(Atlases.CHEST_ATLAS, TREASURE_CHEST_TEXTURE);

	//TODO: register in ClientSetup
	public TreasureChestRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(ChestTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		World world = tileEntityIn.getWorld();
		boolean flag = world != null;
		BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);

		matrixStackIn.push();
		float f = blockstate.get(ChestBlock.FACING).getHorizontalAngle();
		matrixStackIn.translate(0.5D, 0.5D, 0.5D);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
		matrixStackIn.translate(-0.5D, -0.5D, -0.5D);

		float lidAngle = ChestBlock.func_226917_a_((IChestLid) tileEntityIn).func_225538_a_(tileEntityIn).get(partialTicks);
		lidAngle = 1.0F - lidAngle;
		lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
		combinedLightIn = new DualBrightnessCallback<>().func_225538_a_(tileEntityIn).applyAsInt(combinedLightIn);
		IVertexBuilder ivertexbuilder = CHEST_MATERIAL.getBuffer(bufferIn, RenderType::getEntityCutout);

		this.singleLid.rotateAngleX = -(lidAngle * ((float) Math.PI / 2F));
		this.singleLatch.rotateAngleX = this.singleLid.rotateAngleX;
		this.singleLid.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
		this.singleLatch.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
		this.singleBottom.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);

		matrixStackIn.pop();
	}

	@Override
	protected Material getMaterial(ChestTileEntity tileEntity, ChestType chestType) {
		return CHEST_MATERIAL;
	}
}
