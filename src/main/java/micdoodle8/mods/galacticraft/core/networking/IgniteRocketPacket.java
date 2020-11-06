package micdoodle8.mods.galacticraft.core.networking;

import micdoodle8.mods.galacticraft.api.prefab.entity.RocketEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class IgniteRocketPacket extends AbstractPacket {
	
	public IgniteRocketPacket() {}
	
	public IgniteRocketPacket(PacketBuffer buf) {}

	@Override
	public void encode(PacketBuffer buf) {}
	
	protected void handleEnqueued(NetworkEvent.Context ctx) {
		ServerPlayerEntity player = ctx.getSender();
		Entity ridingEntity = player.getRidingEntity();
		if(ridingEntity instanceof RocketEntity && ridingEntity.isAlive()) {
			RocketEntity rocket = (RocketEntity) ridingEntity;
			rocket.ignite();
			//TODO: check for fuel / parachute
		}
	}

}
