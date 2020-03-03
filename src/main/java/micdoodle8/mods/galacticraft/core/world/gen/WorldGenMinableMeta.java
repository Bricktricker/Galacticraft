package micdoodle8.mods.galacticraft.core.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGenMinableMeta extends WorldGenMinable
{
    private final Block minableBlockId;

    private final int numberOfBlocks;

    private final int metadata;
    private final Block fillerID;
    private final int fillerMetadata;
    private boolean usingMetadata;

    public WorldGenMinableMeta(Block placeBlock, int blockCount, int placeMeta, boolean metaActive, Block replaceBlock, int replaceMeta)
    {
        super(placeBlock.getStateFromMeta(placeMeta), blockCount, BlockMatcher.forBlock(replaceBlock));
        this.minableBlockId = placeBlock;
        this.numberOfBlocks = blockCount;
        this.metadata = placeMeta;
        this.usingMetadata = metaActive;
        this.fillerID = replaceBlock;
        this.fillerMetadata = replaceMeta;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        float f = rand.nextFloat() * (float) Math.PI;
        float sinFvalue = MathHelper.sin(f) * (float) this.numberOfBlocks / 8.0F;
        float cosFvalue = MathHelper.cos(f) * (float) this.numberOfBlocks / 8.0F;
        float chunkCentreX = (float) (position.getX() + 8);
        float chunkCentreZ = (float) (position.getZ() + 8);
        double clumpXa = chunkCentreX + sinFvalue;
        double clumpXb = (double) (chunkCentreX - sinFvalue) - clumpXa;
        double clumpZa = chunkCentreZ + cosFvalue;
        double clumpZb = (double) (chunkCentreZ - cosFvalue) - clumpZa;
        double clumpYa = position.getY() + rand.nextInt(3) - 2;
        double clumpYb = (double) (position.getY() + rand.nextInt(3) - 2) - clumpYa;

        final IBlockState oreState = this.minableBlockId.getStateFromMeta(this.usingMetadata ? this.metadata : 0);
        double size = (rand.nextDouble() * (double) this.numberOfBlocks + 1D) / 16.0D;

        for (int i = 0; i < this.numberOfBlocks; ++i)
        {
            float f1 = (float) i / (float) this.numberOfBlocks;
            double centreX = clumpXa + clumpXb * (double) f1;
            double centreY = clumpYa + clumpYb * (double) f1;
            double centreZ = clumpZa + clumpZb * (double) f1;
            double sizeXZ = ((double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * size + 1.0D) / 2.0D;
            int xmin = MathHelper.floor(centreX - sizeXZ);
            int xmax = MathHelper.floor(centreX + sizeXZ);
            int ymin = MathHelper.floor(centreY - sizeXZ);
            int ymax = MathHelper.floor(centreY + sizeXZ);
            int zmin = MathHelper.floor(centreZ - sizeXZ);
            int zmax = MathHelper.floor(centreZ + sizeXZ);
            centreX -= 0.5D;
            centreY -= 0.5D;
            centreZ -= 0.5D;

            for (int x = xmin; x <= xmax; ++x)
            {
                double dx = ((double) x - centreX) / sizeXZ;

                if (dx * dx < 1.0D)
                {
                    for (int y = ymin; y <= ymax; ++y)
                    {
                        double dy = ((double) y - centreY) / sizeXZ;
                        double xySquared = dx * dx + dy * dy;

                        if (xySquared < 1.0D)
                        {
                            for (int z = zmin; z <= zmax; ++z)
                            {
                                double dz = ((double) z - centreZ) / sizeXZ;

                                if (xySquared + dz * dz < 1.0D)
                                {
                                    BlockPos blockpos = new BlockPos(x, y, z);
                                    IBlockState state = worldIn.getBlockState(blockpos);

                                    if (state.getBlock() == this.fillerID && state.getBlock().getMetaFromState(state) == this.fillerMetadata)
                                    {
                                        worldIn.setBlockState(blockpos, oreState, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
