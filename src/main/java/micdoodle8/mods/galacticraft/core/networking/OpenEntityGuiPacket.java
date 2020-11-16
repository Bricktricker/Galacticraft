package micdoodle8.mods.galacticraft.core.networking;

import micdoodle8.mods.galacticraft.api.entity.IEntityNamedContainer;
import micdoodle8.mods.galacticraft.core.inventory.RocketInventoryContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class OpenEntityGuiPacket extends AbstractPacket {

	public OpenEntityGuiPacket() {
	}

	public OpenEntityGuiPacket(PacketBuffer buf) {
	}

	@Override
	public void encode(PacketBuffer buf) {
	}

	@Override
	protected void handleEnqueued(Context ctx) {
		ServerPlayerEntity player = ctx.getSender();
		if(player.openContainer instanceof RocketInventoryContainer) {
			return;
		}

		Entity ridingEntity = player.getRidingEntity();
		if(ridingEntity instanceof IEntityNamedContainer && ridingEntity.isAlive()) {
			IEntityNamedContainer namedContainer = (IEntityNamedContainer) ridingEntity;
			NetworkHooks.openGui(player, new INamedContainerProvider() {
				@Override
				public Container createMenu(int windowID, PlayerInventory playerInv, PlayerEntity player) {
					return namedContainer.createMenu(windowID, playerInv, player);
				}

				@Override
				public ITextComponent getDisplayName() {
					return namedContainer.getContainerName();
				}
			});
		}
	}

}
