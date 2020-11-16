package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCTileEntities;
import micdoodle8.mods.galacticraft.core.blocks.PadFullBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class BuggyPadSingleTileEntity extends TileEntity implements ITickableTileEntity {

	private boolean completed = false;

	public BuggyPadSingleTileEntity() {
		super(GCTileEntities.BUGGY_PAD.get());
	}

	@Override
	public void tick() {
		if(!this.world.isRemote && !this.completed) {
			this.checkCompleted();
		}
	}

	public void checkCompleted() {
		List<BuggyPadSingleTileEntity> attachedLaunchPads = new ArrayList<>();

		for(int x = this.getPos().getX() - 1; x < this.getPos().getX() + 2; x++) {
			for(int z = this.getPos().getZ() - 1; z < this.getPos().getZ() + 2; z++) {
				final TileEntity tile = this.world.getTileEntity(new BlockPos(x, this.getPos().getY(), z));

				if(tile instanceof BuggyPadSingleTileEntity && !tile.isRemoved()) {
					attachedLaunchPads.add((BuggyPadSingleTileEntity) tile);
				}
			}
		}

		if(attachedLaunchPads.size() == 9) {

			for(int i = 0; i < attachedLaunchPads.size(); i++) {
				BuggyPadSingleTileEntity tile = attachedLaunchPads.get(i);
				tile.completed = true;
				this.world.removeTileEntity(tile.getPos());
				this.world.setBlockState(tile.getPos(), GCBlocks.BUGGY_PAD_FULL.get().getDefaultState().with(PadFullBlock.POSITION, i), 3 | 8);
			}

		}else {
			this.completed = false;
		}
	}

	public boolean isComplete() {
		return this.completed;
	}

}
