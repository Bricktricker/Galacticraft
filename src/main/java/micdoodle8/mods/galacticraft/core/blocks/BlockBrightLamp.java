package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.TileEntityArclamp;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.RedstoneUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockBrightLamp extends Block implements IShiftDescription {
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());
//    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	protected static final VoxelShape DOWN_AABB = Block.makeCuboidShape(0.2F, 0.0F, 0.2F, 0.8F, 0.6F, 0.8F);
	protected static final VoxelShape UP_AABB = Block.makeCuboidShape(0.2F, 0.4F, 0.2F, 0.8F, 1.0F, 0.8F);
	protected static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.2F, 0.2F, 0.0F, 0.8F, 0.8F, 0.6F);
	protected static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.2F, 0.2F, 0.4F, 0.8F, 0.8F, 1.0F);
	protected static final VoxelShape WEST_AABB = Block.makeCuboidShape(0.0F, 0.2F, 0.2F, 0.6F, 0.8F, 0.8F);
	protected static final VoxelShape EAST_AABB = Block.makeCuboidShape(0.4F, 0.2F, 0.2F, 1.0F, 0.8F, 0.8F);

	// Metadata: bits 0-2 are the LogicalSide of the base plate using standard
	// LogicalSide convention (0-5)

	public BlockBrightLamp(Properties builder) {
		super(builder);
		this.setDefaultState(stateContainer.getBaseState().with(FACING, Direction.UP)); // .with(ACTIVE, true));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch(state.get(FACING)) {
			case EAST:
				return EAST_AABB;
			case WEST:
				return WEST_AABB;
			case SOUTH:
				return SOUTH_AABB;
			case NORTH:
				return NORTH_AABB;
			case DOWN:
				return DOWN_AABB;
			case UP:
			default:
				return UP_AABB;
		}
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		Block block = state.getBlock();
		if(block != this) {
			return state.getLightValue();
		}
		/**
		 * Gets the light value of the specified block coords. Args: x, y, z
		 */

		if(world instanceof World) {
			return RedstoneUtil.isBlockReceivingRedstone((World) world, pos) ? 0 : this.lightValue;
		}

		return 0;
	}

	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityArclamp();
	}

	@Override
	public String getShiftDescription(ItemStack stack) {
		return GCCoreUtil.translate(this.getTranslationKey() + ".description");
	}

	@Override
	public boolean showDescription(ItemStack stack) {
		return true;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
