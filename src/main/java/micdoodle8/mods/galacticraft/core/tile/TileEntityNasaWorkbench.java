package micdoodle8.mods.galacticraft.core.tile;

import micdoodle8.mods.galacticraft.core.BlockNames;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti;
import micdoodle8.mods.galacticraft.core.blocks.BlockMulti.EnumBlockMultiType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

public class TileEntityNasaWorkbench extends TileEntityFake implements IMultiBlock
{
    @ObjectHolder(Constants.MOD_ID_CORE + ":" + BlockNames.nasaWorkbench)
    public static TileEntityType<TileEntityNasaWorkbench> TYPE;

    private boolean initialised;

    public TileEntityNasaWorkbench()
    {
        super(TYPE);
    }

    @Override
    public ActionResultType onActivated(PlayerEntity entityPlayer)
    {
//        entityPlayer.openGui(GalacticraftCore.instance, GuiIdsCore.NASA_WORKBENCH_ROCKET, this.world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ()); TODO guis
        return ActionResultType.SUCCESS;
    }

    @Override
    public void tick()
    {
        if (!this.initialised)
        {
            this.initialised = this.initialiseMultiTiles(this.getPos(), this.world);
        }
    }

    @Override
    public void onCreate(World world, BlockPos placedPosition)
    {
        this.mainBlockPosition = placedPosition;
        this.markDirty();
        List<BlockPos> positions = new ArrayList<>();
        this.getPositions(placedPosition, positions);
        //((BlockMulti) GCBlocks.fakeBlock).makeFakeBlock(world, positions, placedPosition, this.getMultiType());
    }

    @Override
    public BlockMulti.EnumBlockMultiType getMultiType()
    {
        return EnumBlockMultiType.NASA_WORKBENCH;
    }

    @Override
    public void getPositions(BlockPos placedPosition, List<BlockPos> positions)
    {
        int buildHeight = this.world.getHeight() - 1;

        for (int y = 1; y < 3; y++)
        {
            if (placedPosition.getY() + y > buildHeight)
            {
                return;
            }

            for (int x = -1; x < 2; x++)
            {
                for (int z = -1; z < 2; z++)
                {
                    if (Math.abs(x) != 1 || Math.abs(z) != 1)
                    {
                        positions.add(new BlockPos(placedPosition.getX() + x, placedPosition.getY() + y, placedPosition.getZ() + z));
                    }
                }
            }
        }

        if (placedPosition.getY() + 3 <= buildHeight)
        {
            positions.add(new BlockPos(placedPosition.getX(), placedPosition.getY() + 3, placedPosition.getZ()));
        }
    }

    @Override
    public void onDestroy(TileEntity callingBlock)
    {
        final BlockPos thisBlock = getPos();
        List<BlockPos> positions = new ArrayList<>();
        this.getPositions(thisBlock, positions);

        for (BlockPos pos : positions)
        {
            BlockState stateAt = this.world.getBlockState(pos);

            if (stateAt.getBlock() == null && stateAt.get(BlockMulti.MULTI_TYPE) == EnumBlockMultiType.NASA_WORKBENCH) //'null' was 'GCBlocks.fakeBlock'
            {
                if (this.world.isRemote && this.world.rand.nextDouble() < 0.05D)
                {
                    Minecraft.getInstance().particles.addBlockDestroyEffects(pos, this.world.getBlockState(thisBlock));
                }
                this.world.removeBlock(pos, false);
            }
        }
        this.world.destroyBlock(thisBlock, true);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(getPos().getX() - 1, getPos().getY() - 1, getPos().getZ() - 1, getPos().getX() + 2, getPos().getY() + 5, getPos().getZ() + 2);
    }
}
