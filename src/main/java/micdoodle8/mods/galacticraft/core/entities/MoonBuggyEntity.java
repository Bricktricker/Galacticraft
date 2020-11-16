package micdoodle8.mods.galacticraft.core.entities;

import micdoodle8.mods.galacticraft.api.entity.IEntityNamedContainer;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.client.gui.container.BuggyInventoryContainer;
import micdoodle8.mods.galacticraft.core.util.IntReferenceWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class MoonBuggyEntity extends Entity implements IEntityNamedContainer {
	public static final int FUEL_CAPACITY = 1000;

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

	private LazyOptional<MoonBuggyEntity> buggyCap;

	private BuggyType buggyType;

	private float wheelRotationX;
	private float wheelRotationZ;
	private int timeClimbing;

	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYaw;
	private double lerpPitch;

	public MoonBuggyEntity(World worldIn) {
		this(GCEntities.MOON_BUGGY.get(), worldIn);
	}

	public MoonBuggyEntity(EntityType<?> entityTypeIn, World worldIn) {
		super(entityTypeIn, worldIn);

		this.preventEntitySpawning = true;
		this.ignoreFrustumCheck = true;

		this.buggyType = null;

		this.fuelTank = new FluidTank(FUEL_CAPACITY, f -> f.getFluid().isIn(FluidTags.LAVA)); // TODO: currently only accepts lava
		this.fuelCap = LazyOptional.of(() -> this.fuelTank);

		this.inventory = new ItemStackHandler(0);
		this.inventoryCap = LazyOptional.of(() -> this.inventory);

		this.buggyCap = LazyOptional.of(() -> this);

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
		if(this.isPassenger(passenger)) {
			final double offsetX = Math.cos(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
			final double offsetZ = Math.sin(this.rotationYaw / Constants.RADIANS_TO_DEGREES_D + 114.8) * -0.5D;
			passenger.setPosition(this.getPosX() + offsetX, this.getPosY() + 0.4F + passenger.getYOffset(), this.getPosZ() + offsetZ);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if(!this.world.isRemote) {
			if(this.getPosY() < 0.0) {
				this.remove();
			}
		}

		if(this.getDamageTaken() > 0.0F) {
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}

		this.tickLerp();

		if(!this.fuelTank.isEmpty()) {
			if(this.world.isRemote) {
				boolean vorward = this.getForward().dotProduct(this.getMotion()) < 0;
				this.wheelRotationX += Math.sqrt(this.getMotion().x * this.getMotion().x + this.getMotion().z * this.getMotion().z) * 150.0F * (vorward ? 1 : -1); // check for moving forward /
																																									// backwards
				this.wheelRotationX %= 360;
				this.wheelRotationZ = Math.max(-30.0F, Math.min(30.0F, this.wheelRotationZ * 0.9F));
			}
			this.updateMotion();
			this.move(MoverType.SELF, this.getMotion());
		}

		if(this.world.isRemote && this.getMotion().lengthSquared() > 0) {
			this.fuelTank.drain(1, FluidAction.EXECUTE);
		}
	}

	private void updateMotion() {
		double d1 = this.hasNoGravity() ? 0.0D : (double) -0.04D;
		double momentum = 0.09F;

		Vec3d vec3d = this.getMotion();
		this.setMotion(vec3d.x * momentum, vec3d.y + d1, vec3d.z * momentum);
		double moveForward = 0.0;
		if(this.isBeingRidden()) {
			LivingEntity passenger = (LivingEntity) this.getPassengers().get(0);
			moveForward = passenger.moveForward * 0.2D;
			this.rotationYaw -= passenger.moveStrafing * passenger.moveForward * 3D;
			if(passenger.moveStrafing > 0.1D) {
				this.wheelRotationZ = Math.max(-30.0F, Math.min(30.0F, this.wheelRotationZ + 1F));
			}else if(passenger.moveStrafing < -0.1D) {
				this.wheelRotationZ = Math.max(-30.0F, Math.min(30.0F, this.wheelRotationZ - 1F));
			}
		}

		Vec3d vorwardMotion = this.getForward().scale(moveForward);
		this.setMotion(this.getMotion().add(vorwardMotion));

		if(this.collidedHorizontally && moveForward > 0) {
			double motY = 0.15D * ((-Math.pow((this.timeClimbing) - 1, 2)) / 250.0F) + 0.15F;
			motY = Math.max(-0.15, motY);
			this.setMotion(this.getMotion().x, motY, this.getMotion().z);
		}

		if((this.getMotion().x == 0 || this.getMotion().z == 0) && !this.onGround) {
			this.timeClimbing++;
		}else {
			this.timeClimbing = 0;
		}
	}

	private void tickLerp() {
		if(this.canPassengerSteer()) {
			this.lerpSteps = 0;
			this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
		}

		if(this.lerpSteps > 0 && !this.world.isRemote) { // TODO: is this world check correct
			double d0 = this.getPosX() + (this.lerpX - this.getPosX()) / (double) this.lerpSteps;
			double d1 = this.getPosY() + (this.lerpY - this.getPosY()) / (double) this.lerpSteps;
			double d2 = this.getPosZ() + (this.lerpZ - this.getPosZ()) / (double) this.lerpSteps;
			double d3 = MathHelper.wrapDegrees(this.lerpYaw - (double) this.rotationYaw);
			this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
			this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpPitch - (double) this.rotationPitch) / (double) this.lerpSteps);
			--this.lerpSteps;
			this.setPosition(d0, d1, d2);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(this.isInvulnerableTo(source)) {
			return false;
		}
		if(this.world.isRemote && this.isAlive()) {
			Entity e = source.getTrueSource();
			if(this.getPosY() > 300 || (e instanceof LivingEntity && !(e instanceof PlayerEntity))) {
				return false;
			}else {
				this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
				this.markVelocityChanged();
				boolean isCreative = e instanceof PlayerEntity && ((PlayerEntity) e).abilities.isCreativeMode;
				if(isCreative || this.getDamageTaken() > 40.0F) {
					if(!isCreative && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
						// TODO: drop self and items
					}

					this.remove();
				}
				return true;
			}
		}else {
			return true;
		}
	}

	@Override
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.lerpX = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.lerpYaw = (double) yaw;
		this.lerpPitch = (double) pitch;
		this.lerpSteps = 5;
	}

	@Override
	public void performHurtAnimation() {
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
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

	public void setDamageTaken(float damageTaken) {
		this.dataManager.set(DAMAGE_TAKEN, damageTaken);
	}

	public float getDamageTaken() {
		return this.dataManager.get(DAMAGE_TAKEN);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand) {
		if(hand != Hand.MAIN_HAND) {
			return false;
		}

		player.startRiding(this);
		return true;
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return entityIn instanceof PlayerEntity;
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

	public float getWheelRotationZ() {
		return wheelRotationZ;
	}

	public float getWheelRotationX() {
		return wheelRotationX;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return this.fuelCap.cast();
		}
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.inventory.getSlots() > 0) {
			return this.inventoryCap.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void remove(boolean keepData) {
		super.remove(keepData);
		if(!keepData) {
			this.fuelCap.invalidate();
			this.inventoryCap.invalidate();
			this.buggyCap.invalidate();
		}
	}

	public LazyOptional<MoonBuggyEntity> getInterface() {
		return this.buggyCap;
	}
	
	@Override
	public Container createMenu(int windowID, PlayerInventory playerInv, PlayerEntity player) {
		return new BuggyInventoryContainer(windowID, playerInv, new IntReferenceWrapper(this.fuelReference));
	}

	@Override
	public ITextComponent getContainerName() {
		return new TranslationTextComponent("container.galacticraftcore.moon_buggy");
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
