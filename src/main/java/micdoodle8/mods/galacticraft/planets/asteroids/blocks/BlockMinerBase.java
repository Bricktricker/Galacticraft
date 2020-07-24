package micdoodle8.mods.galacticraft.planets.asteroids.blocks;

import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.tile.TileEntityMinerBaseSingle;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockMinerBase extends BlockTileGC implements IShiftDescription
{
    public BlockMinerBase(Properties builder)
    {
        super(builder);
    }

//    @OnlyIn(Dist.CLIENT)
//    @Override
//    public ItemGroup getCreativeTabToDisplayOn()
//    {
//        return GalacticraftCore.galacticraftBlocksTab;
//    }

//    @Override
//    public Item getItemDropped(BlockState state, Random random, int par3)
//    {
//        return super.getItemDropped(state, random, par3);
//    }
//
//    @Override
//    public int damageDropped(BlockState state)
//    {
//        return 0;
//    }
//
//    @Override
//    public int quantityDropped(BlockState state, int fortune, Random random)
//    {
//        return 1;
//    }

//    @Override
//    public boolean isOpaqueCube(BlockState state)
//    {
//        return false;
//    }

//    @Override
//    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, BlockState state, BlockPos pos, Direction face)
//    {
//        return BlockFaceShape.UNDEFINED;
//    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new TileEntityMinerBaseSingle();
    }

//    @Override
//    public BlockState getStateForPlacement(BlockItemUseContext context)
//    {
//        return getStateFromMeta(0);
//    }

//    @Override
//    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, Direction side)
//    {
//        return true;
//    }

    @Override
    public String getShiftDescription(ItemStack stack)
    {
        return GCCoreUtil.translate(this.getTranslationKey() + ".description");
    }

    @Override
    public boolean showDescription(ItemStack stack)
    {
        return true;
    }

//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public BlockRenderLayer getRenderLayer()
//    {
//        return BlockRenderLayer.CUTOUT;
//    }

//    @Override
//    public EnumSortCategoryBlock getCategory(int meta)
//    {
//        return EnumSortCategoryBlock.GENERAL;
//    }
}
