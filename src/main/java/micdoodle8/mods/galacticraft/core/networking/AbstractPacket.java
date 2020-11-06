package micdoodle8.mods.galacticraft.core.networking;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public abstract class AbstractPacket {
	
	protected static final Logger LOGGER = LogManager.getLogger();
	
	public void handle(Supplier<NetworkEvent.Context> ctxSup) {
		NetworkEvent.Context ctx = ctxSup.get();
		ctx.enqueueWork(() -> handleEnqueued(ctx));
	}
	
	protected void handleEnqueued(NetworkEvent.Context ctx) {
		throw new IllegalStateException("BlockPosPacket#handleEnqueued not overwritten");
	}
	
	public abstract void encode(PacketBuffer buf);

}
