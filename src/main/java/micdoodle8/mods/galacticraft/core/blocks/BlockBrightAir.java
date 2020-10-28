package micdoodle8.mods.galacticraft.core.blocks;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockBrightAir extends AirBlock
{
    public BlockBrightAir(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public boolean isAir(BlockState state) {
    	return true;
    }
    
    @Override
    public boolean isAir(BlockState state, IBlockReader world, BlockPos pos) {
    	return true;
    }
}
