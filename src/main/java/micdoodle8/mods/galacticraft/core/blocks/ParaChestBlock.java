package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.ParaChestTileEntity;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ParaChestBlock extends Block implements IShiftDescription {
	public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
	public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);

	public ParaChestBlock(Properties builder) {
		super(builder);
		this.setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH).with(COLOR, DyeColor.RED));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlayer().getHorizontalFacing());
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit) {
		if(worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		}else {

			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof ParaChestTileEntity) {
				ParaChestTileEntity pcTe = (ParaChestTileEntity) te;
				NetworkHooks.openGui((ServerPlayerEntity) playerIn, pcTe, buf -> buf.writeBlockPos(pos));
				return ActionResultType.SUCCESS;
			}

			return ActionResultType.PASS;
		}
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		// TODO: drop items

		super.onReplaced(state, worldIn, pos, newState, isMoving);
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ParaChestTileEntity();
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
		builder.add(COLOR, FACING);
	}

}
