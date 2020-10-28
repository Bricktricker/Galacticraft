package micdoodle8.mods.galacticraft.core.blocks;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public abstract class BlockThermalAir extends AirBlock
{
    public static final BooleanProperty THERMAL = BooleanProperty.create("thermal");

    public BlockThermalAir(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public boolean isAir(BlockState state, IBlockReader world, BlockPos pos) {
    	return true;
    }
    
    @Override
    public boolean isAir(BlockState state) {
    	return true;
    }
}
