package micdoodle8.mods.galacticraft.core.util;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ItemGroupGC extends ItemGroup {
	private Supplier<ItemStack> itemForTab;

	public ItemGroupGC(int index, String label, Supplier<ItemStack> itemForTab) {
		super(index, label);
		this.itemForTab = itemForTab;
	}

	@Override
	public ItemStack createIcon() {
		return this.itemForTab.get();
	}

}
