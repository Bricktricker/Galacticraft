package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.TileEntitySolar;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

import javax.annotation.Nullable;

public class BlockSolarAdvanced extends BlockTileGC implements IShiftDescription, IPartialSealableBlock
{
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public BlockSolarAdvanced(Properties builder)
    {
        super(builder);
    }

//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void getSubBlocks(ItemGroup tab, NonNullList<ItemStack> list)
//    {
//        list.add(new ItemStack(this, 1, BlockSolar.BASIC_METADATA));
//        list.add(new ItemStack(this, 1, BlockSolar.ADVANCED_METADATA));
//    }

//    @Override
//    public ItemGroup getCreativeTabToDisplayOn()
//    {
//        return GalacticraftCore.galacticraftBlocksTab;
//    }

//    @Override
//    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, Direction side)
//    {
//        for (int y = 1; y <= 2; y++)
//        {
//            for (int x = -1; x <= 1; x++)
//            {
//                for (int z = -1; z <= 1; z++)
//                {
//                    BlockPos posAt = pos.add(y == 2 ? x : 0, y, y == 2 ? z : 0);
//                    BlockState bs = worldIn.getBlockState(posAt);
//
//                    if (bs.getMaterial() != Material.AIR && !bs.getBlock().isReplaceable(worldIn, posAt))
//                    {
//                        return false;
//                    }
//                }
//            }
//        }
//
//        for (int x = -2; x <= 2; x++)
//        {
//            for (int z = -2; z <= 2; z++)
//            {
//                BlockPos posAt = pos.add(x, 0, z);
//                Block block = worldIn.getBlockState(posAt).getBlock();
//
//                if (block == this)
//                {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//        // return new BlockVec3(x1, y1, z1).newVecSide(LogicalSide ^ 1).getBlock(world) != GCBlocks.fakeBlock; TODO
//    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing()), 3);
        BlockMulti.onPlacement(worldIn, pos, placer, this);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        final TileEntity var9 = worldIn.getTileEntity(pos);

        if (var9 instanceof TileEntitySolar)
        {
            ((TileEntitySolar) var9).onDestroy(var9);
        }

        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public ActionResultType onMachineActivated(World world, BlockPos pos, BlockState state, PlayerEntity entityPlayer, Hand hand, ItemStack heldItem, BlockRayTraceResult hit)
    {
//        entityPlayer.openGui(GalacticraftCore.instance, -1, world, pos.getX(), pos.getY(), pos.getZ()); TODO guis
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new TileEntitySolar.TileEntitySolarT2();
    }

//    @Override
//    public boolean isFullCube(BlockState state)
//    {
//        return false;
//    }

    @Override
    public String getShiftDescription(ItemStack stack)
    {
        return GCCoreUtil.translate("tile.solar_adv.description");
    }

    @Override
    public boolean showDescription(ItemStack stack)
    {
        return true;
    }

//    @Override
//    public boolean isOpaqueCube(BlockState state)
//    {
//        return false;
//    }

    @Override
    public boolean isSealed(World worldIn, BlockPos pos, Direction direction)
    {
        return true;
    }

//    @Override
//    public BlockState getStateFromMeta(int meta)
//    {
//        Direction enumfacing = Direction.byHorizontalIndex(meta % 4);
//        EnumSolarType type = EnumSolarType.byMetadata((int) Math.floor(meta / 4.0));
//        return this.getDefaultState().with(FACING, enumfacing).with(TYPE, type);
//    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

//    @Override
//    public EnumSortCategoryBlock getCategory(int meta)
//    {
//        return EnumSortCategoryBlock.MACHINE;
//    }
}
