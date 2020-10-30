package micdoodle8.mods.galacticraft.api.prefab.entity;

import micdoodle8.mods.galacticraft.api.entity.IRocketType;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IRocket extends ICapabilityProvider {
	
    /**
     * When the fuel dock is destroyed. Most likely kills the entity and drops
     * it's containing items.
     */
	void onPadDestroyed();
	
	void ignite();
	
	IRocketType getRocketType();

}
