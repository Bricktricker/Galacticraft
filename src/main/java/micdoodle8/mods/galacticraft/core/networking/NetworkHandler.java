package micdoodle8.mods.galacticraft.core.networking;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	
	private static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(Constants.MOD_ID_CORE, "main_new"))
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.simpleChannel();
	
	public static void registerPackets() {
		int messageNumber = 0;
		INSTANCE.registerMessage(messageNumber++, UpdateDisablePacket.class, UpdateDisablePacket::encode, UpdateDisablePacket::new, UpdateDisablePacket::handle);
		INSTANCE.registerMessage(messageNumber++, IgniteRocketPacket.class, IgniteRocketPacket::encode, IgniteRocketPacket::new, IgniteRocketPacket::handle);
		INSTANCE.registerMessage(messageNumber++, SwitchPagePacket.class, SwitchPagePacket::encode, SwitchPagePacket::new, SwitchPagePacket::handle);
		INSTANCE.registerMessage(messageNumber++, OpenRocketGuiPacket.class, OpenRocketGuiPacket::encode, OpenRocketGuiPacket::new, OpenRocketGuiPacket::handle);
	}

}
