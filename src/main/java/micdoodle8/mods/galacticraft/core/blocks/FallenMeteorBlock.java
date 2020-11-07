package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.TileEntityFallenMeteor;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class FallenMeteorBlock extends Block implements IShiftDescription {

	private static final VoxelShape BOUNDS = Block.makeCuboidShape(0.15, 0.05, 0.15, 0.85, 0.75, 0.85);

	public FallenMeteorBlock(Properties builder) {
		super(builder);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return BOUNDS;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		TileEntity tile = worldIn.getTileEntity(pos);

		if(tile instanceof TileEntityFallenMeteor) {
			TileEntityFallenMeteor meteor = (TileEntityFallenMeteor) tile;

			if(meteor.getHeatLevel() <= 0) {
				return;
			}

			if(entityIn instanceof LivingEntity) {
				final LivingEntity livingEntity = (LivingEntity) entityIn;

				worldIn.playSound(null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.NEUTRAL, 0.5F,
						2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);

				for(int var5 = 0; var5 < 8; ++var5) {
					worldIn.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + Math.random(), pos.getY() + 0.2D + Math.random(), pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
				}

				if(!livingEntity.isBurning()) {
					livingEntity.setFire(2);
				}

				double var9 = pos.getX() + 0.5F - livingEntity.getPosX();
				double var7;

				for(var7 = livingEntity.getPosZ() - pos.getZ(); var9 * var9 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D) {
					var9 = (Math.random() - Math.random()) * 0.01D;
				}

				livingEntity.knockBack(livingEntity, 1, var9, var7);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		world.setBlockState(pos, this.getDefaultState(), 3);
		TileEntity tile = world.getTileEntity(pos);

		if(tile instanceof TileEntityFallenMeteor) {
			((TileEntityFallenMeteor) tile).setHeatLevel(0);
		}
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if(!worldIn.isRemote) {
			this.tryToFall(worldIn, pos, state);
		}
	}

	private void tryToFall(World world, BlockPos pos, BlockState state) {
		if(this.canFallBelow(world, pos.down()) && pos.getY() >= 0) {
			int prevHeatLevel = ((TileEntityFallenMeteor) world.getTileEntity(pos)).getHeatLevel();
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
			BlockPos blockpos1;

			for(blockpos1 = pos.down(); this.canFallBelow(world, blockpos1) && blockpos1.getY() > 0; blockpos1 = blockpos1.down()) {
			}

			if(blockpos1.getY() >= 0) {
				world.setBlockState(blockpos1.up(), state, 3);
				((TileEntityFallenMeteor) world.getTileEntity(blockpos1.up())).setHeatLevel(prevHeatLevel);
			}
		}
	}

	private boolean canFallBelow(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return FallingBlock.canFallThrough(state);
	}

	public static int colorMultiplier(IBlockReader worldIn, BlockPos pos) {
		if(worldIn != null && pos != null) {
			TileEntity tile = worldIn.getTileEntity(pos);

			if(tile instanceof TileEntityFallenMeteor) {
				TileEntityFallenMeteor meteor = (TileEntityFallenMeteor) tile;

				Vector3 col = new Vector3(198, 108, 58);
				col.translate(200 - meteor.getScaledHeatLevel() * 200);
				col.x = Math.min(255, col.x);
				col.y = Math.min(255, col.y);
				col.z = Math.min(255, col.z);

				return ColorUtil.to32BitColor(255, (byte) col.x, (byte) col.y, (byte) col.z);
			}
		}

		return 16777215;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityFallenMeteor();
	}

	@Override
	public String getShiftDescription(ItemStack stack) {
		return I18n.format(this.getTranslationKey() + ".description");
	}

	@Override
	public boolean showDescription(ItemStack stack) {
		return true;
	}

	@Override
	public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
		if(state.getBlock() != this) {
			return 0;
		}

		Random rand = world instanceof World ? ((World) world).rand : new Random();
		return MathHelper.nextInt(rand, 3, 7);
	}
}