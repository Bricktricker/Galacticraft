package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
import micdoodle8.mods.galacticraft.core.tile.TileEntityBuggyFueler;
import micdoodle8.mods.galacticraft.core.tile.TileEntityLandingPad;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PadFullBlock extends BlockAdvancedTile implements IPartialSealableBlock {
	public static final EnumProperty<EnumLandingPadFullType> PAD_TYPE = EnumProperty.create("type", EnumLandingPadFullType.class);
	public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 8); // the position in a completed pad
	private final VoxelShape AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D);

	public enum EnumLandingPadFullType implements IStringSerializable {
		ROCKET_PAD("rocket"), BUGGY_PAD("buggy");

		private final String name;

		EnumLandingPadFullType(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}

	public PadFullBlock(Properties builder) {
		super(builder);
//        this.maxY = 0.25F;
	}

	public TileEntity getMainTE(BlockState state, World worldIn, BlockPos pos) {
		int posID = state.get(POSITION).intValue();
		int xDrift = 0;
		if(posID < 3) {
			xDrift = -1;
		}else if(posID > 5) {
			xDrift = 1;
		}

		// TODO: verfify
		int zDrift = (posID - (xDrift * 3)) - 4;

		return worldIn.getTileEntity(pos.add(xDrift, 0, zDrift));
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		final TileEntity te = getMainTE(state, worldIn, pos);

		if(te instanceof IMultiBlock) {
			((IMultiBlock) te).onDestroy(te);
		}

		super.onReplaced(state, worldIn, pos, newState, isMoving);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return this.AABB;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return state.get(POSITION).intValue() == 5; // Only middle pad has a tile entity
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return this == GCBlocks.landingPadFull ? new TileEntityLandingPad() : new TileEntityBuggyFueler();
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		worldIn.notifyBlockUpdate(pos, state, state, 3);
	}

	@Override
	public boolean isSealed(World worldIn, BlockPos pos, Direction direction) {
		return direction == Direction.UP;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return new ItemStack(Item.getItemFromBlock(GCBlocks.landingPad), 1);
	}

//    @Override
//    public BlockState getStateFromMeta(int meta)
//    {
//        return this.getDefaultState().with(PAD_TYPE, EnumLandingPadFullType.byMetadata(meta));
//    }

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(PAD_TYPE, POSITION);
	}
}
