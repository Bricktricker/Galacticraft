package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.CargoLoaderTileEntity;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
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
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class CargoLoaderBlock extends Block implements IShiftDescription {
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

	public CargoLoaderBlock(Properties builder) {
		super(builder);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof CargoLoaderTileEntity) {
				CargoLoaderTileEntity clTe = (CargoLoaderTileEntity) te;
				NetworkHooks.openGui((ServerPlayerEntity) player, clTe, pos);
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
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onBlockAdded(state, worldIn, pos, oldState, isMoving);

		TileEntity tile = worldIn.getTileEntity(pos);

		if(tile instanceof CargoLoaderTileEntity) {
			((CargoLoaderTileEntity) tile).checkForCargoEntity();
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		super.onReplaced(state, worldIn, pos, newState, isMoving);
		WorldUtil.markAdjacentPadForUpdate(worldIn, pos); // WHY?
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new CargoLoaderTileEntity.T1();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 3);
		WorldUtil.markAdjacentPadForUpdate(worldIn, pos); // WHy
	}

	@Override
	public String getShiftDescription(ItemStack stack) {
		return GCCoreUtil.translate("tile.cargo_loader.description");
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
