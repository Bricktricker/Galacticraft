package micdoodle8.mods.galacticraft.core.networking;

import net.minecraft.network.PacketBuffer;

public abstract class BooleanPacket extends AbstractPacket {

	protected final boolean value;
	
	public BooleanPacket(boolean value) {
		this.value = value;
	}
	
	public BooleanPacket(PacketBuffer buf) {
		this.value = buf.readBoolean();
	}
	
	@Override
	public void encode(PacketBuffer buf) {
		buf.writeBoolean(this.value);
	}

}
