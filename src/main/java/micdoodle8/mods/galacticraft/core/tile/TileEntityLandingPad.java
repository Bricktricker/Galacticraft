package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.prefab.entity.IRocket;
import micdoodle8.mods.galacticraft.api.prefab.entity.RocketEntity;
import micdoodle8.mods.galacticraft.api.tile.ILandingPadAttachable;
import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti.EnumBlockMultiType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TileEntityLandingPad extends TileEntity implements IMultiBlock {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.landingPadFull)
	public static TileEntityType<TileEntityLandingPad> TYPE;
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private LazyOptional<IRocket> dockedEntity;
	private boolean isDestroying = false; //Marker, when we start destroying the other landing pads to prevent reentering

	public TileEntityLandingPad() {
		super(TYPE);
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
	public BlockMulti.EnumBlockMultiType getMultiType() {
		return EnumBlockMultiType.ROCKET_PAD;
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
		if(this.isDestroying) return;
		this.isDestroying = true;
		
		final BlockPos thisBlock = getPos();
		List<BlockPos> positions = new ArrayList<>();
		this.getPositions(thisBlock, positions);

		for(BlockPos pos : positions) {
			BlockState stateAt = this.world.getBlockState(pos);
			
			if(stateAt.getBlock() != GCBlocks.LANDING_PAD_FULL.get()) {
				//TODO: this warning should display once, when destroying a non center piece. Maybe pass in the start destroying position?
				LOGGER.warn("Tried to remove landing pad, but found blockState {}", stateAt);
			}else if(!this.world.isRemote){
				this.world.destroyBlock(pos, true);
			}
		}
		this.world.destroyBlock(thisBlock, true);
		
		this.dockedEntity.ifPresent(r -> {
			r.onPadDestroyed();
			this.dockedEntity = LazyOptional.empty();
		});
	}

	public HashSet<ILandingPadAttachable> getConnectedTiles() {
		HashSet<ILandingPadAttachable> connectedTiles = new HashSet<>();

		for(int x = this.getPos().getX() - 1; x < this.getPos().getX() + 2; x++) {
			this.testConnectedTile(x, this.getPos().getZ() - 2, connectedTiles);
			this.testConnectedTile(x, this.getPos().getZ() + 2, connectedTiles);
		}

		for(int z = this.getPos().getZ() - 1; z < this.getPos().getZ() + 2; z++) {
			this.testConnectedTile(this.getPos().getX() - 2, z, connectedTiles);
			this.testConnectedTile(this.getPos().getX() + 2, z, connectedTiles);
		}

		return connectedTiles;
	}

	private void testConnectedTile(int x, int z, HashSet<ILandingPadAttachable> connectedTiles) {
		BlockPos testPos = new BlockPos(x, this.getPos().getY(), z);
		if(!this.world.isBlockLoaded(testPos)) {
			return;
		}

		final TileEntity tile = this.world.getTileEntity(testPos);

		if(tile instanceof ILandingPadAttachable && ((ILandingPadAttachable) tile).canAttachToLandingPad(this.world, this.getPos())) {
			connectedTiles.add((ILandingPadAttachable) tile);
//            if (GalacticraftCore.isPlanetsLoaded && tile instanceof TileEntityLaunchController)
//            {
//                ((TileEntityLaunchController) tile).setAttachedPad(this);
//            } TODO Planets
		}
	}

	public boolean isBlockAttachable(IWorldReader world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);

		if(tile instanceof ILandingPadAttachable) {
			return ((ILandingPadAttachable) tile).canAttachToLandingPad(world, this.getPos());
		}

		return false;
	}

	public LazyOptional<IRocket> getDockedRocket() {
		return this.dockedEntity;
	}
}
