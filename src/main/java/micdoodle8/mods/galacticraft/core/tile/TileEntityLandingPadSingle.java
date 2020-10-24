package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.PadFullBlock;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

public class TileEntityLandingPadSingle extends TileEntity implements ITickableTileEntity {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.landingPad)
	public static TileEntityType<TileEntityLandingPadSingle> TYPE;

	private boolean completed = false;

	public TileEntityLandingPadSingle() {
		super(TYPE);
	}

	@Override
	public void tick() {
		if(!this.world.isRemote && !this.completed) {
			this.checkCompleted();
		}
	}

	public void checkCompleted() {
		List<TileEntityLandingPadSingle> attachedLaunchPads = new ArrayList<>();

		for(int x = this.getPos().getX() - 1; x < this.getPos().getX() + 2; x++) {
			for(int z = this.getPos().getZ() - 1; z < this.getPos().getZ() + 2; z++) {
				final TileEntity tile = this.world.getTileEntity(new BlockPos(x, this.getPos().getY(), z));

				if(tile instanceof TileEntityLandingPadSingle && !tile.isRemoved()) {
					attachedLaunchPads.add((TileEntityLandingPadSingle) tile);
				}
			}
		}

		if(attachedLaunchPads.size() == 9) {
			
			for(int i = 0; i < attachedLaunchPads.size(); i++) {
				TileEntityLandingPadSingle tile = attachedLaunchPads.get(i);
				tile.completed = true;
				this.world.removeTileEntity(tile.getPos());
				this.world.setBlockState(getPos(), GCBlocks.landingPadFull.getDefaultState().with(PadFullBlock.POSITION, i), 3);
			}

		}else {
			this.completed = false;
		}
	}
	
	public boolean isComplete() {
		return this.completed;
	}

}
