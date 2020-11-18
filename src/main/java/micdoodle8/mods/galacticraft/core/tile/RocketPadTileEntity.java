package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.prefab.entity.IRocket;
import micdoodle8.mods.galacticraft.api.prefab.entity.RocketEntity;
import micdoodle8.mods.galacticraft.core.GCTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class RocketPadTileEntity extends TileEntity implements IMultiBlock {
	
	private LazyOptional<IRocket> dockedEntity;

	public RocketPadTileEntity() {
		super(GCTileEntities.ROCKET_PAD_FULL.get());
		this.dockedEntity = LazyOptional.empty();
	}

	@Override
	public void tick() {

		if(!this.world.isRemote && !this.dockedEntity.isPresent()) {
			List<RocketEntity> rockets = this.world.getEntitiesWithinAABB(RocketEntity.class, new AxisAlignedBB(this.getPos().getX() - 0.5D, this.getPos().getY(), this.getPos().getZ() - 0.5D,
					this.getPos().getX() + 0.5D, this.getPos().getY() + 1.0D, this.getPos().getZ() + 0.5D));
			
			if(rockets.isEmpty()) {
				return;
			}
			
			RocketEntity r = rockets.get(0);
			this.dockedEntity = r.getInterface();
		}
	}

	@Override
	public ActionResultType onActivated(PlayerEntity entityPlayer) {
		return ActionResultType.PASS;
	}

	@Override
	public void onCreate(World world, BlockPos placedPosition) {

	}

	@Override
	public void getPositions(BlockPos placedPosition, List<BlockPos> positions) {
		int y = placedPosition.getY();
		for(int x = -1; x < 2; x++) {
			for(int z = -1; z < 2; z++) {
				if(x == 0 && z == 0) {
					continue;
				}
				positions.add(new BlockPos(placedPosition.getX() + x, y, placedPosition.getZ() + z));
			}
		}
	}

	@Override
	public void onDestroy(TileEntity callingBlock) {
		this.dockedEntity.ifPresent(r -> {
			r.onPadDestroyed();
			this.dockedEntity = LazyOptional.empty();
		});
	}

	public LazyOptional<IRocket> getDockedRocket() {
		return this.dockedEntity;
	}
}