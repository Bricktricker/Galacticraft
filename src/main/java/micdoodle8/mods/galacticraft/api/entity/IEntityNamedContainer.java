package micdoodle8.mods.galacticraft.api.entity;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.core.client.GCKeyHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

/**
 * Implement on entities that have a conatainer screen.
 * Opens when the user presses the {@link GCKeyHandler#spaceKey}.
 */
public interface IEntityNamedContainer {

	@Nullable
	Container createMenu(int windowID, PlayerInventory playerInv, PlayerEntity player);

	ITextComponent getContainerName();

}
