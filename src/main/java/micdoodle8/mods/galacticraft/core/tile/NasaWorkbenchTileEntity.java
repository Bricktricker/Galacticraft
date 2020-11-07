package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.item.ISchematic;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.GCTileEntities;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti.EnumBlockMultiType;
import micdoodle8.mods.galacticraft.core.inventory.NasaWorkbenchContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class NasaWorkbenchTileEntity extends TileEntity implements IMultiBlock, INamedContainerProvider {

	private List<ISchematic> activatedSchematics;

	public NasaWorkbenchTileEntity() {
		super(GCTileEntities.NASA_WORKBENCH.get());
		this.activatedSchematics = new ArrayList<ISchematic>();
		this.activatedSchematics.add(GCItems.SCHEMATIC_ROCKET_T1.get());
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		ListNBT schematicList = new ListNBT();
		for(int i = 0; i < activatedSchematics.size(); i++) {
			StringNBT s = StringNBT.valueOf(activatedSchematics.get(i).getItemRegistryName().toString());
			schematicList.func_218660_b(i, s);
		}
		compound.put("schematics", schematicList);

		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);

		activatedSchematics.clear();
		ListNBT schematicList = compound.getList("schematics", Constants.NBT.TAG_STRING);
		for(int i = 0; i < schematicList.size(); i++) {
			ResourceLocation rl = new ResourceLocation(schematicList.getString(i));
			Item item = ForgeRegistries.ITEMS.getValue(rl);
			if(item instanceof ISchematic) {
				activatedSchematics.add((ISchematic)item);	
			}else {
				GalacticraftCore.LOGGER.warn("stored item {} in the nasa workbench is not a schematic", item);
			}
		}
	}

	@Override
	public void onCreate(World world, BlockPos placedPosition) {

	}

	@Override
	public BlockMulti.EnumBlockMultiType getMultiType() {
		return EnumBlockMultiType.NASA_WORKBENCH;
	}

	@Override
	public void getPositions(BlockPos placedPosition, List<BlockPos> positions) {
		int buildHeight = this.world.getHeight() - 1;

		for(int y = 1; y < 3; y++) {
			if(placedPosition.getY() + y > buildHeight) {
				return;
			}

			for(int x = -1; x < 2; x++) {
				for(int z = -1; z < 2; z++) {
					if(Math.abs(x) != 1 || Math.abs(z) != 1) {
						positions.add(new BlockPos(placedPosition.getX() + x, placedPosition.getY() + y, placedPosition.getZ() + z));
					}
				}
			}
		}

		if(placedPosition.getY() + 3 <= buildHeight) {
			positions.add(new BlockPos(placedPosition.getX(), placedPosition.getY() + 3, placedPosition.getZ()));
		}
	}

	@Override
	public void onDestroy(TileEntity callingBlock) {

	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(getPos().getX() - 1, getPos().getY() - 1, getPos().getZ() - 1, getPos().getX() + 2, getPos().getY() + 5, getPos().getZ() + 2);
	}

	@Override
	public void tick() {

	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new NasaWorkbenchContainer(p_createMenu_1_, p_createMenu_2_, this);
	}
	
	public void writeSchematics(PacketBuffer buf) {
		buf.writeVarInt(this.activatedSchematics.size());
		activatedSchematics.stream()
			.map(i -> i.getItemRegistryName().toString())
			.forEach(buf::writeString);
	}
	
	public void readSchematics(PacketBuffer buf) {
		this.activatedSchematics.clear();
		int num = buf.readVarInt();
		for(int i = 0; i < num; i++) {
			ResourceLocation rl = new ResourceLocation(buf.readString());
			Item item = ForgeRegistries.ITEMS.getValue(rl);
			if(item instanceof ISchematic) {
				activatedSchematics.add((ISchematic)item);	
			}else {
				GalacticraftCore.LOGGER.warn("stored item {} in the nasa workbench is not a schematic", item);
			}
		}
		
	}
	
	public boolean addSchematic(ISchematic schematic) {
		if(!this.activatedSchematics.contains(schematic)) {
			this.activatedSchematics.add(schematic);
			this.markDirty();
			return true;
		}
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.nasa_workbench");
	}

	@Override
	public ActionResultType onActivated(PlayerEntity entityPlayer) {
		return ActionResultType.PASS;
	}
	
	public List<ISchematic> getSchematics() {
		return this.activatedSchematics;
	}
}
