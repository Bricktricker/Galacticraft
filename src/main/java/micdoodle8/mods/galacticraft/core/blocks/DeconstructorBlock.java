package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.tile.DeconstructorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
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

public class DeconstructorBlock extends Block {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public DeconstructorBlock(Properties builder) {
        super(builder);
    }
    
    @Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof DeconstructorTileEntity) {
				DeconstructorTileEntity dTe = (DeconstructorTileEntity) te;
				NetworkHooks.openGui((ServerPlayerEntity) player, dTe, pos);
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
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new DeconstructorTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
}
