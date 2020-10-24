package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.api.recipe.ShapedRecipesGC;
import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.sounds.GCSounds;
import micdoodle8.mods.galacticraft.core.inventory.DeconstructorContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ObjectHolder;

public class DeconstructorTileEntity extends ProcessingTileEntity<ShapedRecipesGC>
{
    @ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.deconstructor)
    public static TileEntityType<DeconstructorTileEntity> TYPE;

    public static final float SALVAGE_CHANCE = 0.75F;
    public static final int PROCESS_TIME_REQUIRED_BASE = 250;
    public final int processTimeRequired = PROCESS_TIME_REQUIRED_BASE;
    public static final int INV_SIZE = 10;

    public DeconstructorTileEntity()
    {
        super(TYPE, INV_SIZE, 10000, 75);
    }
    
    @Override
    protected ShapedRecipesGC getRecipe() {
    	return null; //TODO: implement
    }
    
    @Override
    protected int getProcessingTime(ShapedRecipesGC recipe) {
    	return PROCESS_TIME_REQUIRED_BASE;
    }
    
    @Override
    protected void recipeFinished(ShapedRecipesGC recipe) {
    	//TODO: implement
    	this.world.playSound(null, this.getPos(), GCSounds.deconstructor, SoundCategory.BLOCKS, 0.25F, this.world.rand.nextFloat() * 0.04F + 0.38F);
    }

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.galacticraftcore.deconstructor");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new DeconstructorContainer(p_createMenu_1_, p_createMenu_2_, this, this.containerStats);
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot == 0;
	}
}
