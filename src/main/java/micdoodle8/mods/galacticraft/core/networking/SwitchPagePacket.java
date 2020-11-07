package micdoodle8.mods.galacticraft.core.networking;

import micdoodle8.mods.galacticraft.core.inventory.NasaWorkbenchContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

/**
 * Used by the nasa workbench to switch to the next or previous page
 */
public class SwitchPagePacket extends BooleanPacket {

	public SwitchPagePacket(boolean value) {
		super(value);
	}
	
	public SwitchPagePacket(PacketBuffer buf) {
		super(buf);
	}
	
	@Override
	protected void handleEnqueued(Context ctx) {
		 Container container =  ctx.getSender().openContainer;
		 if(container instanceof NasaWorkbenchContainer) {
			 NasaWorkbenchContainer workbench = (NasaWorkbenchContainer) container;
			 workbench.nextPage(this.value);
		 }else {
			 LOGGER.warn("sender container is no NasaWorkbenchContainer");
			 ctx.getSender().closeContainer();
		 }
	}

}
