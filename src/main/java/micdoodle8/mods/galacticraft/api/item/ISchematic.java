package micdoodle8.mods.galacticraft.api.item;

import java.util.function.Consumer;

import micdoodle8.mods.galacticraft.core.inventory.NasaWorkbenchContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.ITextComponent;

/**
 * Needs to be implemented on an item, that the Nasa workbench consumes as a schematic
 */
public interface ISchematic {
	
	/**
	 * The background texture for the schematic screen
	 * @return the ResourceLocation of your texture
	 */
	public ResourceLocation getScreenTexture();
	
	/**
	 * An ITextComponent representing your schematic title
	 * @return the title of the schematic screen
	 */
	public ITextComponent getTitle();
	
	/**
	 * The used screen size in pixel of your screen texture
	 * @return the size packed into an Vec2f
	 */
	public Vec2f getScreenSize();
	
	/**
	 * The number of crafting slots your schematic has. Dont include the result slot in here
	 * @return number of crafting slots
	 */
	public int getNumCraftingSlots();
	
	/**
	 * Add your slots to the nasa workbench consumer. Add the craftResult slot as the first slot with index 0.
	 * @param slotConsumer The consumer thats adding you slot to the container
	 * @param inventory the crafting inventory
	 * @param craftResult the result slot inventory
	 * @param playerInventory the player inventory
	 */
	public void addSlots(Consumer<Slot> slotConsumer, CraftingInventory inventory, CraftResultInventory craftResult, PlayerInventory playerInventory);
	
	/**
	 * Check if the inventory assembles a valid crafting recipe. If yes, return the resulting ItemStack, otherwise return {@link ItemStack#EMPTY}
	 * @param inventory
	 * @return the crafting result
	 */
	public ItemStack getResult(CraftingInventory inventory);
	
	/**
	 * Called when Container#transferStackInSlot is called
	 */
	public ItemStack transferStackInSlot(PlayerEntity player, int index, NasaWorkbenchContainer container);
	
	default ResourceLocation getItemRegistryName() {
		return ((Item)this).getRegistryName();
	}
}
