package micdoodle8.mods.galacticraft.core.entities;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class MoonBuggyEntity extends Entity {
	protected static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(MoonBuggyEntity.class, DataSerializers.FLOAT);

	private LazyOptional<IFluidHandler> fuelCap;
	protected FluidTank fuelTank;
	protected IntReferenceHolder fuelReference = new IntReferenceHolder() {

		@Override
		public int get() {
			return fuelTank.getFluidAmount();
		}

		@Override
		public void set(int amount) {
			fuelTank.getFluid().setAmount(amount);
		}

	};

	protected ItemStackHandler inventory;
	private LazyOptional<IItemHandlerModifiable> inventoryCap;

	private BuggyType buggyType;

	public MoonBuggyEntity(World worldIn) {
		this(GCEntities.MOON_BUGGY.get(), worldIn);
	}

	public MoonBuggyEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);

		this.preventEntitySpawning = true;
		this.ignoreFrustumCheck = true;

		this.buggyType = null;

		this.fuelTank = new FluidTank(1000);
		this.fuelCap = LazyOptional.of(() -> this.fuelTank);

		this.inventory = new ItemStackHandler(0);
		this.inventoryCap = LazyOptional.of(() -> this.inventory);

		this.setMotion(Vec3d.ZERO);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public double getMountedYOffset() {
		return this.getHeight() - 3.0D;
	}

	@Override
	public boolean canBeCollidedWith() {
		return this.isAlive();
	}
	
	@Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger))
        {
            final double offsetX = Math.cos(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
            final double offsetZ = Math.sin(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
            passenger.setPosition(this.getPosX() + offsetX, this.getPosY() + 0.4F + passenger.getYOffset(), this.getPosZ() + offsetZ);
        }
    }

	@Override
	protected void registerData() {
		this.dataManager.register(DAMAGE_TAKEN, 0f);
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
		this.fuelTank.readFromNBT(compound.getCompound("fuel"));

		if(compound.contains("items", net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND)) {
			this.inventory.deserializeNBT(compound.getCompound("items"));
		}
		this.buggyType = BuggyType.values()[compound.getInt("buggyType")];
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
		compound.put("fuel", this.fuelTank.writeToNBT(new CompoundNBT()));

		if(this.inventory.getSlots() > 0) {
			CompoundNBT invNBT = new CompoundNBT();
			this.inventory.deserializeNBT(invNBT);
			compound.put("items", invNBT);
		}
		compound.putInt("buggyType", this.buggyType.ordinal());
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public void setBuggyType(BuggyType type) {
		if(this.buggyType != null) {
			throw new IllegalStateException("Cannot change buggy type");
		}

		this.buggyType = type;
		this.inventory.setSize(type.invSize);
	}

	public ItemStackHandler getInventory() {
		return this.inventory;
	}
	
	public BuggyType getBuggyType() {
		return this.buggyType;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && this.fuelCap != null) {
			return this.fuelCap.cast();
		}
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.inventory.getSlots() > 0) {
			return this.inventoryCap.cast();
		}

		return super.getCapability(cap, side);
	}

	public enum BuggyType {
		NO_INVENTORY(0, new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/buggy_0.png")),
		INVENTORY_1(18, new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/buggy_1.png")),
		INVENTORY_2(36, new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/buggy_2.png")),
		INVENTORY_3(54, new ResourceLocation(Constants.MOD_ID_CORE, "textures/gui/buggy_3.png"));

		private final int invSize;
		private final ResourceLocation textureLoc;

		BuggyType(int invSize, ResourceLocation textureLoc) {
			this.invSize = invSize;
			this.textureLoc = textureLoc;
		}

		public int getInvSize() {
			return invSize;
		}

		public static BuggyType byId(int id) {
			return values()[id];
		}

		public ResourceLocation getTextureLoc() {
			return textureLoc;
		}
	}

}
