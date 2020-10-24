package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.tile.IMachineSidesProperties;
import micdoodle8.mods.galacticraft.core.tile.ElectricCompressorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockIngotCompressorElectric extends BlockMachineBase
{
    //    public static final EnumProperty<EnumMachineExtendedType> TYPE = EnumProperty.create("type", EnumMachineExtendedType.class);
    public static final IMachineSidesProperties MACHINESIDES_RENDERTYPE = IMachineSidesProperties.TWOFACES_HORIZ;

    public BlockIngotCompressorElectric(Properties builder)
    {
        super(builder);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new ElectricCompressorTileEntity.T1();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
}
