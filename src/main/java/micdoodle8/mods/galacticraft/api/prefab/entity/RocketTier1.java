package micdoodle8.mods.galacticraft.api.prefab.entity;

import micdoodle8.mods.galacticraft.api.entity.IRocketType;
import micdoodle8.mods.galacticraft.core.entities.GCEntities;
import micdoodle8.mods.galacticraft.core.inventory.RocketInventoryContainer;
import micdoodle8.mods.galacticraft.core.util.IntReferenceWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class RocketTier1 extends RocketEntity {
	
	private IRocketType type = new Tier1Type();

	public RocketTier1(World worldIn) {
		super(GCEntities.ROCKET_T1.get(), worldIn);
	}
	
	public RocketTier1(EntityType<RocketTier1> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public IRocketType getRocketType() {
		return type;
	}

	@Override
	public int getFuelTankCapacity() {
		return 1000;
	}

	@Override
	public int getInventoryCapacity() {
		return 2; //type.getRocketType().getInventorySpace();
	}

	@Override
	public int getPreLaunchWait() {
		return 5 * 20;
	}
	
	@Override
	public float getLiftPower() {
		return 0.75f;
	}
	
	@SuppressWarnings("resource")
	@Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand) {
		boolean val = super.processInitialInteract(player, hand);
		if(val) {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
				if(Minecraft.getInstance().player == player) {
					Minecraft.getInstance().gameSettings.thirdPersonView = 1;
				}
			});
		}
		return val;
	}
	
	static class Tier1Type implements IRocketType {
		public EnumRocketType getRocketType() {
			return EnumRocketType.DEFAULT;
		}
		public int getRocketTier() {
			return 1;
		}
	}

	@Override
	public int getFuelUsage() {
		return 1;
	}

	@Override
	public Container createMenu(int windowID, PlayerInventory playerInv, PlayerEntity player) {
		return new RocketInventoryContainer(windowID, playerInv, new IntReferenceWrapper(this.getFuelReference()));
	}

	@Override
	public ITextComponent getContainerName() {
		return new TranslationTextComponent("container.galacticraftcore.rocket_t1");
	}

}
