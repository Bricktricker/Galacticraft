package micdoodle8.mods.galacticraft.core.items;

import micdoodle8.mods.galacticraft.api.entity.IRocketType.EnumRocketType;
import micdoodle8.mods.galacticraft.api.item.IHoldableItem;
import micdoodle8.mods.galacticraft.api.prefab.entity.RocketTier1;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.PadFullBlock;
import micdoodle8.mods.galacticraft.core.entities.EntityTier1Rocket;
import micdoodle8.mods.galacticraft.core.entities.GCEntities;
import micdoodle8.mods.galacticraft.core.fluid.GCFluids;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.tile.TileEntityLandingPad;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTier1Rocket extends Item implements IHoldableItem {
	public ItemTier1Rocket(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		GalacticraftCore.LOGGER.info("called onItemUse");
		ItemStack stack = context.getPlayer().getHeldItem(context.getHand());

		if(context.getWorld().isRemote && context.getPlayer() instanceof ClientPlayerEntity) {
			//ClientProxyCore.playerClientHandler.onBuild(8, (ClientPlayerEntity) context.getPlayer());
			return ActionResultType.PASS;
		}else {
			BlockPos target = context.getPos();
			BlockState targetState = context.getWorld().getBlockState(target);

			if(targetState.getBlock() instanceof PadFullBlock) {
				PadFullBlock padBlock = (PadFullBlock) targetState.getBlock();
				TileEntity te = padBlock.getMainTE(targetState, context.getWorld(), target);
				if(te instanceof TileEntityLandingPad) {
					if(!placeRocketOnPad(stack, context.getWorld(), (TileEntityLandingPad)te, target.getX() + 0.5f, target.getY() + 0.25f, target.getZ() + 0.5f)) {
						return ActionResultType.FAIL;
					}

					if(!context.getPlayer().abilities.isCreativeMode) {
						stack.shrink(1);
					}
					return ActionResultType.SUCCESS;
				}
			}

			return ActionResultType.FAIL;

		}
	}

	public static boolean placeRocketOnPad(ItemStack stack, World worldIn, TileEntityLandingPad tile, float centerX, float centerY, float centerZ) {
		// Check whether there is already a rocket on the pad
		if(tile.getDockedRocket().isPresent()) {
			return false;
		}

		final RocketTier1 spaceship = GCEntities.ROCKET_T1.get().create(worldIn);
		spaceship.setPosition(centerX, centerY, centerZ);
		//spaceship.rocketType = EntityTier1Rocket.getTypeFromItem(stack.getItem());

		spaceship.setPosition(spaceship.getPosX(), spaceship.getPosY() /*+ spaceship.getOnPadYOffset()*/, spaceship.getPosZ());
		worldIn.addEntity(spaceship);

//		if(spaceship.rocketType.getPreFueled()) {
//			spaceship.fuelTank.fill(new FluidStack(GCFluids.FUEL.getFluid(), spaceship.getMaxFuel()), IFluidHandler.FluidAction.EXECUTE);
//		}else if(stack.hasTag() && stack.getTag().contains("RocketFuel")) {
//			spaceship.fuelTank.fill(new FluidStack(GCFluids.FUEL.getFluid(), stack.getTag().getInt("RocketFuel")), IFluidHandler.FluidAction.EXECUTE);
//		}

		return true;
	}

//    @Override
//    public void getSubItems(ItemGroup tab, NonNullList<ItemStack> list)
//    {
//        if (tab == GalacticraftCore.galacticraftItemsTab || tab == ItemGroup.SEARCH)
//        {
//            for (int i = 0; i < EnumRocketType.values().length; i++)
//            {
//                list.add(new ItemStack(this, 1, i));
//            }
//        }
//    }

	@Override
	@OnlyIn(Dist.CLIENT)
	public Rarity getRarity(ItemStack par1ItemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		EnumRocketType type = EnumRocketType.values()[stack.getDamage()];

		if(!type.getTooltip().getFormattedText().isEmpty()) {
			tooltip.add(type.getTooltip());
		}

		if(type.getPreFueled()) {
			tooltip.add(new StringTextComponent(EnumColor.RED + "\u00a7o" + GCCoreUtil.translate("gui.creative_only.desc")));
		}

		if(stack.hasTag() && stack.getTag().contains("RocketFuel")) {
			tooltip.add(new StringTextComponent(GCCoreUtil.translate("gui.message.fuel.name") + ": " + stack.getTag().getInt("RocketFuel") + " / " + EntityTier1Rocket.FUEL_CAPACITY));
		}
	}

	@Override
	public boolean shouldHoldLeftHandUp(PlayerEntity player) {
		return true;
	}

	@Override
	public boolean shouldHoldRightHandUp(PlayerEntity player) {
		return true;
	}

	@Override
	public boolean shouldCrouch(PlayerEntity player) {
		return true;
	}

}
