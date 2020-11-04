package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.OxygenCollectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockOxygenCollector extends Block implements IShiftDescription {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public BlockOxygenCollector(Properties builder) {
		super(builder);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof OxygenCollectorTileEntity) {
				OxygenCollectorTileEntity ocTe = (OxygenCollectorTileEntity) te;
				NetworkHooks.openGui((ServerPlayerEntity) player, ocTe, pos);
			}else {
				return ActionResultType.FAIL;
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new OxygenCollectorTileEntity();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof OxygenCollectorTileEntity) {
			if(((OxygenCollectorTileEntity) tile).getLastOxygenCollected() > 1) {
				for(int particleCount = 0; particleCount < 10; particleCount++) {
					double x2 = pos.getX() + rand.nextFloat();
					double y2 = pos.getY() + rand.nextFloat();
					double z2 = pos.getZ() + rand.nextFloat();
					double mX = 0.0D;
					double mY = 0.0D;
					double mZ = 0.0D;
					int dir = rand.nextInt(2) * 2 - 1;
					mX = (rand.nextFloat() - 0.5D) * 0.5D;
					mY = (rand.nextFloat() - 0.5D) * 0.5D;
					mZ = (rand.nextFloat() - 0.5D) * 0.5D;

					final Direction direction = stateIn.get(FACING);

					if(direction.getAxis() == Direction.Axis.Z) {
						x2 = pos.getX() + 0.5D + 0.25D * dir;
						mX = rand.nextFloat() * 2.0F * dir;
					}else {
						z2 = pos.getZ() + 0.5D + 0.25D * dir;
						mZ = rand.nextFloat() * 2.0F * dir;
					}

					// TODO: spawn particles
					// worldIn.addParticle(GCParticles.OXYGEN, false, x2, y2, z2, mX, mY, mZ /*,0.7D, 0.7D, 1.0D*/);
				}
			}
		}
	}

	@Override
	public String getShiftDescription(ItemStack stack) {
		return I18n.format(this.getTranslationKey() + ".description");
	}

	@Override
	public boolean showDescription(ItemStack stack) {
		return true;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

}
