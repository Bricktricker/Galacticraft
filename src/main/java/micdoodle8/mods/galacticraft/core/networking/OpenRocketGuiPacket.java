package micdoodle8.mods.galacticraft.core.networking;

import micdoodle8.mods.galacticraft.api.prefab.entity.RocketEntity;
import micdoodle8.mods.galacticraft.core.inventory.RocketInventoryContainer;
import micdoodle8.mods.galacticraft.core.util.IntReferenceWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class OpenRocketGuiPacket extends AbstractPacket {

	public OpenRocketGuiPacket() {
	}

	public OpenRocketGuiPacket(PacketBuffer buf) {
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
		if(ridingEntity instanceof RocketEntity && ridingEntity.isAlive()) {
			RocketEntity rocket = (RocketEntity) ridingEntity;
			NetworkHooks.openGui(player, new INamedContainerProvider() {
				@Override
				public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
					return new RocketInventoryContainer(p_createMenu_1_, p_createMenu_2_, new IntReferenceWrapper(rocket.getFuelReference()));
				}

				@Override
				public ITextComponent getDisplayName() {
					return new TranslationTextComponent("container.galacticraftcore.rocket_t1");
				}
			});
		}
	}

}
