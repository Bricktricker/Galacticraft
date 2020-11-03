package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CheeseBlock extends Block implements IShiftDescription {

	public static final IntegerProperty BITES = BlockStateProperties.BITES_0_6;
	protected static final VoxelShape[] CHEESE_AABB = new VoxelShape[] {
			VoxelShapes.create(0.0625, 0.0, 0.0625, 0.9375, 0.5, 0.9375),
			VoxelShapes.create(0.1875, 0.0, 0.0625, 0.9375, 0.5, 0.9375),
			VoxelShapes.create(0.3125, 0.0, 0.0625, 0.9375, 0.5, 0.9375),
			VoxelShapes.create(0.4375, 0.0, 0.0625, 0.9375, 0.5, 0.9375),
			VoxelShapes.create(0.5625, 0.0, 0.0625, 0.9375, 0.5, 0.9375),
			VoxelShapes.create(0.6875, 0.0, 0.0625, 0.9375, 0.5, 0.9375),
			VoxelShapes.create(0.8125, 0.0, 0.0625, 0.9375, 0.5, 0.9375)
		};

	public CheeseBlock(Properties builder) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(BITES, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return CHEESE_AABB[state.get(BITES)];
	}

	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(worldIn.isRemote) {
			ItemStack itemstack = player.getHeldItem(handIn);
			if(this.eatCheeseSlice(worldIn, pos, state, player) == ActionResultType.SUCCESS) {
				return ActionResultType.SUCCESS;
			}

			if(itemstack.isEmpty()) {
				return ActionResultType.CONSUME;
			}
		}

		return this.eatCheeseSlice(worldIn, pos, state, player);
	}

	private ActionResultType eatCheeseSlice(IWorld worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn) {
		if(!playerIn.canEat(false)) {
			return ActionResultType.PASS;
		}else {
			playerIn.getFoodStats().addStats(2, 0.1F);
			int i = state.get(BITES);
			if(i < 6) {
				worldIn.setBlockState(pos, state.with(BITES, Integer.valueOf(i + 1)), 3);
			}else {
				worldIn.removeBlock(pos, false);
			}

			return ActionResultType.SUCCESS;
		}
	}

	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState()
				: super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BITES);
	}

	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return (7 - blockState.get(BITES)) * 2;
	}

	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	public String getShiftDescription(ItemStack stack) {
		return I18n.format(this.getTranslationKey() + ".description");
	}

	@Override
	public boolean showDescription(ItemStack stack) {
		return true;
	}
}
