package micdoodle8.mods.galacticraft.core.items;

import micdoodle8.mods.galacticraft.api.item.IHoldableItem;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.entities.GCEntities;
import micdoodle8.mods.galacticraft.core.entities.MoonBuggyEntity;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

import javax.annotation.Nullable;
import java.util.List;

public class BuggyItem extends Item implements IHoldableItem {
	private final MoonBuggyEntity.BuggyType buggyType;
	
	public BuggyItem(MoonBuggyEntity.BuggyType buggyType, Item.Properties properties) {
		super(properties);
		this.buggyType = buggyType;
	}

	@Override
	public Rarity getRarity(ItemStack par1ItemStack) {
		return ClientProxyCore.galacticraftItem;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
		ItemStack itemstack = playerIn.getHeldItem(hand);
		final float var4 = 1.0F;
		final float var5 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * var4;
		final float var6 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * var4;
		final double var7 = playerIn.prevPosX + (playerIn.getPosX() - playerIn.prevPosX) * var4;
		final double var9 = playerIn.prevPosY + (playerIn.getPosY() - playerIn.prevPosY) * var4 + 1.62D - playerIn.getYOffset();
		final double var11 = playerIn.prevPosZ + (playerIn.getPosZ() - playerIn.prevPosZ) * var4;
		final Vec3d var13 = new Vec3d(var7, var9, var11);
		final float var14 = MathHelper.cos(-var6 / Constants.RADIANS_TO_DEGREES - (float) Math.PI);
		final float var15 = MathHelper.sin(-var6 / Constants.RADIANS_TO_DEGREES - (float) Math.PI);
		final float var16 = -MathHelper.cos(-var5 / Constants.RADIANS_TO_DEGREES);
		final float var17 = MathHelper.sin(-var5 / Constants.RADIANS_TO_DEGREES);
		final float var18 = var15 * var16;
		final float var20 = var14 * var16;
		final double var21 = 5.0D;
		final Vec3d var23 = var13.add(var18 * var21, var17 * var21, var20 * var21);
		final BlockRayTraceResult var24 = worldIn.rayTraceBlocks(new RayTraceContext(var13, var23, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, playerIn));

		if(var24.getType() == RayTraceResult.Type.MISS) {
			return new ActionResult<>(ActionResultType.PASS, itemstack);
		}else {
			final Vec3d var25 = playerIn.getLook(var4);
			boolean var26 = false;
			final float var27 = 1.0F;
			final List<Entity> var28 = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn,
					playerIn.getBoundingBox().grow(var25.x * var21, var25.y * var21, var25.z * var21).expand(var27, var27, var27));

			for(Entity var30 : var28) {

				if(var30.canBeCollidedWith()) {
					final float var31 = var30.getCollisionBorderSize();
					final AxisAlignedBB var32 = var30.getBoundingBox().expand(var31, var31, var31);

					if(var32.contains(var13)) {
						var26 = true;
					}
				}
			}

			if(var26) {
				return new ActionResult<>(ActionResultType.PASS, itemstack);
			}else {
				if(var24.getType() == RayTraceResult.Type.BLOCK) {
					BlockRayTraceResult blockResult = var24;
					int x = blockResult.getPos().getX();
					int y = blockResult.getPos().getY();
					int z = blockResult.getPos().getZ();

					if(worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.SNOW) {
						--y;
					}

					final MoonBuggyEntity var35 = GCEntities.MOON_BUGGY.get().create(worldIn);
					var35.setBuggyType(this.buggyType);
					var35.setPosition(x + 0.5F, y + 1.0F, z + 0.5F);

//                    if (!worldIn.getCollisionBoxes(var35, var35.getBoundingBox().expand(-0.1D, -0.1D, -0.1D)).isEmpty())
//                    {
//                        return new ActionResult<>(ActionResultType.PASS, itemstack);
//                    } TODO Needed?

					if(itemstack.hasTag() && itemstack.getTag().contains("BuggyFuel")) {
						var35.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(handler -> {
							handler.fill(new FluidStack(Fluids.LAVA, itemstack.getTag().getInt("BuggyFuel")), FluidAction.EXECUTE);
						});
					}

					if(!worldIn.isRemote) {
						worldIn.addEntity(var35);
					}

					if(!playerIn.abilities.isCreativeMode) {
						itemstack.shrink(1);
					}
				}

				return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
			}
		}
	}

	@Override
	public void addInformation(ItemStack item, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(this.buggyType.getInvSize() != 0) {
			tooltip.add(new TranslationTextComponent("gui.buggy.storage_space", this.buggyType.getInvSize()));
		}

		if(item.hasTag() && item.getTag().contains("BuggyFuel")) {
			tooltip.add(new TranslationTextComponent("gui.buggy.fuel", item.getTag().getInt("BuggyFuel"), MoonBuggyEntity.FUEL_CAPACITY));
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
