package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.tile.IMultiBlock;
import micdoodle8.mods.galacticraft.core.tile.TileEntityBuggyFueler;
import micdoodle8.mods.galacticraft.core.tile.TileEntityLandingPad;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PadFullBlock extends Block implements IPartialSealableBlock {
	public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 8); // the position in a completed pad
	private static final VoxelShape AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16, 3, 16);

	public PadFullBlock(Properties builder) {
		super(builder);
	}
	
	public static BlockPos getMainPos(BlockState state, World worldIn, BlockPos pos) {
		int posID = state.get(POSITION).intValue();
		int xDrift = 0;
		if(posID < 3) {
			xDrift = 1;
		}else if(posID > 5) {
			xDrift = -1;
		}

		int zDrift = -((posID + (xDrift * 3)) - 4);
		
		return pos.add(xDrift, 0, zDrift);
	}

	public static TileEntity getMainTE(BlockState state, World worldIn, BlockPos pos) {		
		BlockPos center = getMainPos(state, worldIn, pos);
		return worldIn.getTileEntity(center);
	}
	
	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		BlockPos center = getMainPos(state, worldIn, pos);
		boolean destroy = true;
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				if(!(x == 0 || z == 0)) {
					destroy &= worldIn.getBlockState(center.add(x, 0, z)).getBlock() == this;	
				}
			}
		}
		if(destroy) {
			final TileEntity te = worldIn.getTileEntity(center);
			if(te instanceof IMultiBlock) {
				((IMultiBlock)te).onDestroy(te);
			}
			
			ItemStack playerItem = player.getHeldItemMainhand();
			for(int x = -1; x <= 1; x++) {
				for(int z = -1; z <= 1; z++) {
					BlockPos newPos = center.add(x, 0, z);
					BlockState blockstate = worldIn.getBlockState(newPos);
					worldIn.setBlockState(newPos, Blocks.AIR.getDefaultState(), 35);
					worldIn.playEvent(player, 2001, newPos, Block.getStateId(blockstate));
					if(!worldIn.isRemote && !player.isCreative() && player.canHarvestBlock(blockstate)) {
						Block.spawnDrops(blockstate, worldIn, pos, (TileEntity) null, player, playerItem);
					}
				}
			}
		}
		
		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return AABB;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return state.get(POSITION).intValue() == 4; // Only middle pad has a tile entity
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return this == GCBlocks.LANDING_PAD_FULL.get() ? new TileEntityLandingPad() : new TileEntityBuggyFueler();
	}

	@Override
	public boolean isSealed(World worldIn, BlockPos pos, Direction direction) {
		return direction == Direction.UP;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return new ItemStack(Item.getItemFromBlock(GCBlocks.LANDING_PAD.get()), 1);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(POSITION);
	}
}
