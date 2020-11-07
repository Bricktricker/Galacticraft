package micdoodle8.mods.galacticraft.core.inventory;

import org.apache.commons.lang3.Validate;

import micdoodle8.mods.galacticraft.api.item.ISchematic;
import micdoodle8.mods.galacticraft.core.tile.NasaWorkbenchTileEntity;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class NasaWorkbenchContainer extends Container {

	private final NasaWorkbenchTileEntity workbench;
	private final PlayerInventory playerInventory;
	private int page = 0;
	
	private CraftingInventory craftingInventory;
	private CraftResultInventory craftResult;

	public NasaWorkbenchContainer(int windowID, PlayerInventory inv, PacketBuffer buf) {
		super(GCContainers.NASA_WORKBENCH.get(), windowID);
		TileEntity te = inv.player.world.getTileEntity(buf.readBlockPos());
		if(te instanceof NasaWorkbenchTileEntity) {
			this.workbench = (NasaWorkbenchTileEntity) te;
			this.workbench.readSchematics(buf);
			this.playerInventory = inv;
			this.init();
		}else {
			GCLog.severe("could not find NasaWorkbenchTileEntity at requested position");
			this.workbench = null;
			this.playerInventory = null;
		}
	}

	public NasaWorkbenchContainer(int windowID, PlayerInventory inv, NasaWorkbenchTileEntity workbench) {
		super(GCContainers.NASA_WORKBENCH.get(), windowID);
		this.workbench = workbench;
		this.playerInventory = inv;
		this.init();
	}

	private void init() {
		ISchematic currentSchematic = getCurrentSchematic();
		craftingInventory = new CraftingInventory(this, currentSchematic.getNumCraftingSlots(), 1);
		craftResult = new CraftResultInventory();
		currentSchematic.addSlots(this::addSlot, craftingInventory, this.craftResult, this.playerInventory);
	}
	
	protected void updateCraftingResult() {
		World world = playerInventory.player.world;
	      if (!world.isRemote) {
	         ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)playerInventory.player;
	         ItemStack resultStack = ItemStack.EMPTY;
	         if(!this.craftingInventory.isEmpty()) {
	        	 resultStack = this.getCurrentSchematic().getResult(this.craftingInventory);
	         }

	         this.craftResult.setInventorySlotContents(0, resultStack);
	         serverplayerentity.connection.sendPacket(new SSetSlotPacket(this.windowId, 0, resultStack));
	      }
	   }
	
	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		this.updateCraftingResult();
	}
	
	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		if(!playerInventory.player.world.isRemote) {
			this.clearContainer(playerIn, playerInventory.player.world, this.craftingInventory);
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		return getCurrentSchematic().transferStackInSlot(playerIn, index, this);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}
	
	public ISchematic getCurrentSchematic() {
		return this.workbench.getSchematics().get(page);
	}
	
	public boolean hasNextPage() {
		return this.page != this.workbench.getSchematics().size() - 1;
	}
	
	public boolean hasPrevPage() {
		return this.page != 0;
	}
	
	public int getPage() {
		return this.page;
	}
	
	public void nextPage(boolean forward) {
		this.inventorySlots.clear();
		this.inventoryItemStacks.clear();
		this.page += forward ? 1 : -1;
		Validate.inclusiveBetween(0, this.workbench.getSchematics().size(), this.page);
		this.init();
	}

}
