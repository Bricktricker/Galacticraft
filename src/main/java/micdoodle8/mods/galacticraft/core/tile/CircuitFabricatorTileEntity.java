package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.recipe.ShapedRecipesGC;
import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.inventory.ContainerCircuitFabricator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ObjectHolder;

public class CircuitFabricatorTileEntity extends ProcessingTileEntity<ShapedRecipesGC>
{
    @ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.circuitFabricator)
    public static TileEntityType<CircuitFabricatorTileEntity> TYPE;

    public static final int PROCESS_TIME_REQUIRED = 300;
    public static final int INV_SIZE = 6;

    public CircuitFabricatorTileEntity()
    {
        super(TYPE, INV_SIZE, 10000, 20); //TODO: make values configurable
    }
    
    @Override
    protected ShapedRecipesGC getRecipe() {
    	//TODO: Implement recipes
    	return null;
    }

    @Override
    protected int getProcessingTime(ShapedRecipesGC recipe) {
        return CircuitFabricatorTileEntity.PROCESS_TIME_REQUIRED;
    }
    
    @Override
    protected void recipeFinished(ShapedRecipesGC recipe) {
    	this.world.playSound(null, this.getPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3F, this.world.rand.nextFloat() * 0.1F + 0.9F);
    	//TODO: consume input items, put output item into slot
    }

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.circut_fabricator");
	}

	@Override
	public Container createMenu(int containerId, PlayerInventory playerInv, PlayerEntity p_createMenu_3_) {
		return new ContainerCircuitFabricator(containerId, playerInv, this, this.containerStats);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot > 5) {
			return false;
		}
		
		//Top left one
		if(slot == 0) {
			return stack.getItem().isIn(Tags.Items.GEMS_DIAMOND);
		}
		
		//two middle ones
		if(slot == 1 || slot == 2) {
			return stack.getItem() == GCItems.rawSilicon; //TODO: is this right?
		}
		
		//redstone slot
		if(slot == 3) {
			return stack.getItem().isIn(Tags.Items.DUSTS_REDSTONE);
		}
		
		return false;
	}
}
