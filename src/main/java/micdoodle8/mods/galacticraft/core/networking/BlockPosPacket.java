package micdoodle8.mods.galacticraft.core.networking;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public abstract class BlockPosPacket extends AbstractPacket {

	protected final BlockPos pos;
	
	public BlockPosPacket(BlockPos pos) {
		this.pos = pos;
	}
	
	public BlockPosPacket(PacketBuffer buf) {
		this.pos = buf.readBlockPos();
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeBlockPos(this.pos);
	}
	
}
