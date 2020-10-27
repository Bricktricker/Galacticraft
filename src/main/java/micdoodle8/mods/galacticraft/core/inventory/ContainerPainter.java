package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.api.item.IPaintable;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.tile.PainterTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ObjectHolder;

public class ContainerPainter extends Container {
	@ObjectHolder(Constants.MOD_ID_CORE + ":" + GCContainerNames.PAINTER)
	public static ContainerType<ContainerPainter> TYPE;

	private final PainterTileEntity painter;

	public ContainerPainter(int windowID, PlayerInventory inv, PacketBuffer buf) {
		super(TYPE, windowID);
		TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof PainterTileEntity) {
			this.painter = (PainterTileEntity) te;
			this.init(inv);
		}else {
			this.painter = null;
		}
	}

	public ContainerPainter(int windowID, PlayerInventory playerInv, PainterTileEntity painter) {
		super(TYPE, windowID);
		this.painter = painter;
		this.init(playerInv);
	}

	private void init(PlayerInventory playerInv) {

		// To be painted
		this.addSlot(new SlotItemHandler(painter.getInventory(), 0, 40, 25));
		// TODO: slots which can only accept one item

		// For dyes and other colour giving items
		this.addSlot(new SlotItemHandler(painter.getInventory(), 1, 122, 25));

		int i;
		for(i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 104 + i * 18));
			}
		}
		for(i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInv, i, 8 + i * 18, 162));
		}

		painter.playersUsing.add(playerInv.player);
	}

	public PainterTileEntity getPainter() {
		return painter;
	}

	@Override
	public void onContainerClosed(PlayerEntity entityplayer) {
		super.onContainerClosed(entityplayer);
		this.painter.playersUsing.remove(entityplayer);
	}

	@Override
	public boolean canInteractWith(PlayerEntity par1EntityPlayer) {
		return this.painter.isUsableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift
	 * clicking.
	 */
	@Override
	public ItemStack transferStackInSlot(PlayerEntity par1EntityPlayer, int index) {
		ItemStack stackOrig = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		final int b = this.inventorySlots.size();

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			stackOrig = stack.copy();

			if(index < 2) {
				if(!this.mergeItemStack(stack, b - 36, b, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(stack, stackOrig);
			}else {
				Item item = stack.getItem();
				if(item instanceof IPaintable || (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof IPaintable)) {
					if(!this.mergeOneItem(stack, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				}else if(index < b - 9) {
					if(!this.mergeItemStack(stack, b - 9, b, false)) {
						return ItemStack.EMPTY;
					}
				}else if(!this.mergeItemStack(stack, b - 36, b - 9, false)) {
					return ItemStack.EMPTY;
				}
			}

			if(stack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			}else {
				slot.onSlotChanged();
			}

			if(stack.getCount() == stackOrig.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(par1EntityPlayer, stack);
		}

		return stackOrig;
	}

	protected boolean mergeOneItem(ItemStack par1ItemStack, int par2, int par3, boolean par4) {
		boolean flag1 = false;
		if(par1ItemStack.getCount() > 0) {
			Slot slot;
			ItemStack slotStack;

			for(int k = par2; k < par3; k++) {
				slot = this.inventorySlots.get(k);
				slotStack = slot.getStack();

				if(slotStack.isEmpty()) {
					ItemStack stackOneItem = par1ItemStack.copy();
					stackOneItem.setCount(1);
					par1ItemStack.shrink(1);
					slot.putStack(stackOneItem);
					slot.onSlotChanged();
					flag1 = true;
					break;
				}
			}
		}

		return flag1;
	}
}
