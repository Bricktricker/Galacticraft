package micdoodle8.mods.galacticraft.core.blocks;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.NasaWorkbenchTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class NasaWorkbenchBlock extends Block implements IShiftDescription, IPartialSealableBlock {
	public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 12); // Position in the completed NASA workbench

	public NasaWorkbenchBlock(Properties builder) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(POSITION, 0));
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		for(int i = 1; i < 4; i++) {
			worldIn.setBlockState(pos.up(i), state.with(POSITION, i), 3);
		}
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 4; j++) {
				BlockPos newPos = pos.up(i + 1).offset(Direction.byHorizontalIndex(j));
				int id = ((i + 1) * 4) + j;
				worldIn.setBlockState(newPos, state.with(POSITION, id), 3);
			}
		}

	}

	/**
	 * check if the NASA Workbench can be placed here. Check all position for
	 * {@link BlockState#isReplaceable(BlockItemUseContext)}
	 */
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getPos();
		if(pos.getY() > 250) {
			return null;
		}
		for(int i = 0; i < 2; i++) {
			BlockPos heightPos = pos.up(i + 1);
			if(!context.getWorld().getBlockState(heightPos).isReplaceable(context)) {
				return null;
			}
			for(int j = 0; j < 4; j++) {
				BlockPos checkPos = heightPos.offset(Direction.byHorizontalIndex(j));
				if(!context.getWorld().getBlockState(checkPos).isReplaceable(context)) {
					return null;
				}
			}
		}
		if(!context.getWorld().getBlockState(pos.up(3)).isReplaceable(context)) {
			return null;
		}
		return this.getDefaultState();
	}

	public static TileEntity getMainTE(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos target = getMainPos(state, worldIn, pos);
		return worldIn.getTileEntity(target);
	}

	public static BlockPos getMainPos(BlockState state, IWorldReader worldIn, BlockPos pos) {
		int posID = state.get(POSITION).intValue();
		if(posID < 4) {
			return pos.down(posID);
		}
		int level = posID / 4; // get the height above ground
		int direction = posID % 4; // get the direction
		Direction d = Direction.byHorizontalIndex(direction).getOpposite();

		BlockPos target = pos.down(level).offset(d);
		return target;
	}

	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		BlockPos root = getMainPos(state, worldIn, pos);
		boolean destroy = true;
		for(int i = 1; i < 4; i++) {
			destroy &= worldIn.getBlockState(root.up(i)).getBlock() == this;
		}
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 4; j++) {
				BlockPos newPos = root.up(i + 1).offset(Direction.byHorizontalIndex(j));
				destroy &= worldIn.getBlockState(newPos).getBlock() == this;
			}
		}

		if(destroy) {
			ItemStack playerItem = player.getHeldItemMainhand();
			for(int i = 0; i < 4; i++) {
				BlockPos newPos = root.up(i);
				BlockState blockstate = worldIn.getBlockState(newPos);
				worldIn.setBlockState(newPos, Blocks.AIR.getDefaultState(), 35);
				worldIn.playEvent(player, 2001, newPos, Block.getStateId(blockstate));
				if(!worldIn.isRemote && !player.isCreative() && player.canHarvestBlock(blockstate)) {
					Block.spawnDrops(blockstate, worldIn, pos, (TileEntity) null, player, playerItem);
				}
			}
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 4; j++) {
					BlockPos newPos = root.up(i + 1).offset(Direction.byHorizontalIndex(j));
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
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		if(state.getBlock() != this) {
			return false;
		}
		int id = state.get(POSITION).intValue();
		if(id == 0) {
			if(!worldIn.getBlockState(pos.down()).isSolidSide(worldIn, pos.down(), Direction.UP)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			TileEntity te = getMainTE(state, worldIn, pos);
			if(te instanceof NasaWorkbenchTileEntity) {
				NasaWorkbenchTileEntity nwTe = (NasaWorkbenchTileEntity) te;
				NetworkHooks.openGui((ServerPlayerEntity) playerIn, nwTe, buf -> {
					buf.writeBlockPos(pos);
					nwTe.writeSchematics(buf);
				});
			}else {
				return ActionResultType.FAIL;
			}
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return state.get(POSITION) == 0;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new NasaWorkbenchTileEntity();
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(POSITION);
	}

	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.BLOCK;
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
	public boolean isSealed(World worldIn, BlockPos pos, Direction direction) {
		return true;
	}

}
