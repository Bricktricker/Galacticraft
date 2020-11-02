package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.TileEntityBuggyFuelerSingle;
import micdoodle8.mods.galacticraft.core.tile.TileEntityLandingPadSingle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class PadBlock extends Block implements IPartialSealableBlock, IShiftDescription {

	protected static final VoxelShape AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16, 3, 16);

	public PadBlock(Properties builder) {
		super(builder);
	}


	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return AABB;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return this == GCBlocks.LANDING_PAD.get() ? new TileEntityLandingPadSingle() : new TileEntityBuggyFuelerSingle();
	}

	@Override
	public boolean isSealed(World worldIn, BlockPos pos, Direction direction) {
		return direction == Direction.UP;
	}

	@Override
	public String getShiftDescription(ItemStack stack) {
		return I18n.format(this.getTranslationKey() + ".description");
	}

	@Override
	public boolean showDescription(ItemStack stack) {
		return true;
	}

}
