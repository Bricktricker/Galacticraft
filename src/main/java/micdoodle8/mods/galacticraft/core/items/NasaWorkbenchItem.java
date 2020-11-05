package micdoodle8.mods.galacticraft.core.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class NasaWorkbenchItem extends BlockItem {
	public NasaWorkbenchItem(Block block, Item.Properties builder) {
		super(block, builder);
	}

	@Override
	protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
		for(int i = 1; i < 4; i++) {
			context.getWorld().setBlockState(context.getPos().up(i), Blocks.AIR.getDefaultState(), 27);
    	}
    	for(int i = 0; i < 2; i++) {
    		for(int j = 0; j < 4; j++) {
    			BlockPos newPos = context.getPos().up(i+1).offset(Direction.byHorizontalIndex(j));
    			context.getWorld().setBlockState(newPos, Blocks.AIR.getDefaultState(), 27);
    		}
    	}
		return super.placeBlock(context, state);
	}
}