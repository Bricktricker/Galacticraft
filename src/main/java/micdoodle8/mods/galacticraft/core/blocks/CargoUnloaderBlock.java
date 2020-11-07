package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.CargoUnloaderTileEntity;
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
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class CargoUnloaderBlock extends Block implements IShiftDescription {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public CargoUnloaderBlock(Properties builder) {
		super(builder);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof CargoUnloaderTileEntity) {
				CargoUnloaderTileEntity clTe = (CargoUnloaderTileEntity) te;
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

		if(tile instanceof CargoUnloaderTileEntity) {
			((CargoUnloaderTileEntity) tile).checkForCargoEntity();
		}
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new CargoUnloaderTileEntity.T1();
	}

	@Override
	public String getShiftDescription(ItemStack stack) {
		return I18n.format("tile.cargo_unloader.description");
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