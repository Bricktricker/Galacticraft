package micdoodle8.mods.galacticraft.core.items;

import java.util.function.Consumer;

import micdoodle8.mods.galacticraft.api.item.ISchematic;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.inventory.NasaWorkbenchContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RocketT1SchematicItem extends Item implements ISchematic {

	public RocketT1SchematicItem(Properties properties) {
		super(properties);
	}

	@Override
	public ResourceLocation getScreenTexture() {
		return new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/rocketbench.png");
	}

	@Override
	public ITextComponent getTitle() {
		return new TranslationTextComponent("schematic.rocket_t1.name");
	}

	@Override
	public Vec2f getScreenSize() {
		return new Vec2f(176, 220);
	}

	@Override
	public int getNumCraftingSlots() {
		return 17;
	}

	@Override
	public void addSlots(Consumer<Slot> slotConsumer, CraftingInventory inventory, CraftResultInventory craftResult, PlayerInventory playerInventory) {
		
		int slotIndex = 0;
		//result slot
		slotConsumer.accept(new CraftingResultSlot(playerInventory.player, inventory, craftResult, slotIndex++, 142, 96));
		
		//Top right three slots
		slotConsumer.accept(new Slot(inventory, slotIndex++, 93, 12));
		slotConsumer.accept(new Slot(inventory, slotIndex++, 119, 12));
		slotConsumer.accept(new Slot(inventory, slotIndex++, 145, 12));
		
		//top rocket slot
		slotConsumer.accept(new Slot(inventory, slotIndex++, 48, 19));
		
		//top 2x3 area
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 2; j++) {
				slotConsumer.accept(new Slot(inventory, slotIndex++, 39 + j * 18, 37 + i * 18));
			}
		}
		
		//first 4 line slots
		for(int i = 0; i < 4; i++) {
			slotConsumer.accept(new Slot(inventory, slotIndex++, 21 + i * 18, 91));
		}
		
		//last 3 slots
		slotConsumer.accept(new Slot(inventory, slotIndex++, 21, 109));
		slotConsumer.accept(new Slot(inventory, slotIndex++, 48, 109));
		slotConsumer.accept(new Slot(inventory, slotIndex++, 75, 109));
		
		// Slots for the main inventory
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				slotConsumer.accept(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 138 + i * 18));
			}
		}

		// Slots for the hotbar
		for(int k = 0; k < 9; ++k) {
			slotConsumer.accept(new Slot(playerInventory, k, 8 + k * 18, 196));
		}
	}
	
	@Override
	public ItemStack getResult(CraftingInventory inventory) {
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index, NasaWorkbenchContainer container) {
		return ItemStack.EMPTY;
	}

}
