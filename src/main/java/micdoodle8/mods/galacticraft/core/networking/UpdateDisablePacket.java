package micdoodle8.mods.galacticraft.core.networking;

import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class UpdateDisablePacket extends BlockPosPacket {

	public UpdateDisablePacket(BlockPos pos) {
		super(pos);
	}
	
	public UpdateDisablePacket(PacketBuffer buf) {
		super(buf);
	}
	
	@Override
	protected void handleEnqueued(NetworkEvent.Context ctx) {
		TileEntity te = ctx.getSender().world.getTileEntity(this.pos);
		if(te instanceof IDisableableMachine) {
			final IDisableableMachine machine = (IDisableableMachine) te;
			machine.setDisabled(!machine.isDisabled());
		}else {
			LOGGER.warn("no IDisableableMachine at {}", this.pos);
		}
	}

}
