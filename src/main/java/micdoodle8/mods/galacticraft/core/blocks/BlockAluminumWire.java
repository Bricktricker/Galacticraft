package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.TileEntityAluminumWire;
import micdoodle8.mods.galacticraft.core.tile.TileEntityAluminumWireSwitch;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockAluminumWire extends BlockTransmitter implements IShiftDescription
{
    public static final EnumProperty<EnumWireType> WIRE_TYPE = EnumProperty.create("wiretype", EnumWireType.class);
    private static final float MIN = 0.38F;
    private static final float MINH = 0.3F;
    private static final float MAX = 0.62F;
    private static final float MAXH = 0.7F;
    protected static final VoxelShape[] BOUNDING_BOXES = new VoxelShape[] {

            Block.makeCuboidShape(MIN, MIN, MIN, MAX, MAX, MAX),  // No connection                                  0000000
            Block.makeCuboidShape(MIN, MIN, MIN, MAX, MAX, 1.0D), // South                                          0000001
            Block.makeCuboidShape(0.0D, MIN, MIN, MAX, MAX, MAX), // West                                           0000010
            Block.makeCuboidShape(0.0D, MIN, MIN, MAX, MAX, 1.0D), // South West                                    0000011
            Block.makeCuboidShape(MIN, MIN, 0.0D, MAX, MAX, MAX), // North                                          0000100
            Block.makeCuboidShape(MIN, MIN, 0.0D, MAX, MAX, 1.0D), // North South                                   0000101
            Block.makeCuboidShape(0.0D, MIN, 0.0D, MAX, MAX, MAX), // North West                                    0000110
            Block.makeCuboidShape(0.0D, MIN, 0.0D, MAX, MAX, 1.0D), // North South West                             0000111
            Block.makeCuboidShape(MIN, MIN, MIN, 1.0D, MAX, MAX), // East                                           0001000
            Block.makeCuboidShape(MIN, MIN, MIN, 1.0D, MAX, 1.0D), // East South                                    0001001
            Block.makeCuboidShape(0.0D, MIN, MIN, 1.0D, MAX, MAX), // West East                                     0001010
            Block.makeCuboidShape(0.0D, MIN, MIN, 1.0D, MAX, 1.0D), // South West East                              0001011
            Block.makeCuboidShape(MIN, MIN, 0.0D, 1.0D, MAX, MAX), // North East                                    0001100
            Block.makeCuboidShape(MIN, MIN, 0.0D, 1.0D, MAX, 1.0D), // North South East                             0001101
            Block.makeCuboidShape(0.0D, MIN, 0.0D, 1.0D, MAX, MAX), // North East West                              0001110
            Block.makeCuboidShape(0.0D, MIN, 0.0D, 1.0D, MAX, 1.0D), // North South East West                       0001111

            Block.makeCuboidShape(MIN, 0.0D, MIN, MAX, MAX, MAX),  // Down                                          0010000
            Block.makeCuboidShape(MIN, 0.0D, MIN, MAX, MAX, 1.0D), // Down South                                    0010001
            Block.makeCuboidShape(0.0D, 0.0D, MIN, MAX, MAX, MAX), // Down West                                     0010010
            Block.makeCuboidShape(0.0D, 0.0D, MIN, MAX, MAX, 1.0D), // Down South West                              0010011
            Block.makeCuboidShape(MIN, 0.0D, 0.0D, MAX, MAX, MAX), // Down North                                    0010100
            Block.makeCuboidShape(MIN, 0.0D, 0.0D, MAX, MAX, 1.0D), // Down North South                             0010101
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, MAX, MAX, MAX), // Down North West                              0010110
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, MAX, MAX, 1.0D), // Down North South West                       0010111
            Block.makeCuboidShape(MIN, 0.0D, MIN, 1.0D, MAX, MAX), // Down East                                     0011000
            Block.makeCuboidShape(MIN, 0.0D, MIN, 1.0D, MAX, 1.0D), // Down East South                              0011001
            Block.makeCuboidShape(0.0D, 0.0D, MIN, 1.0D, MAX, MAX), // Down West East                               0011010
            Block.makeCuboidShape(0.0D, 0.0D, MIN, 1.0D, MAX, 1.0D), // Down South West East                        0011011
            Block.makeCuboidShape(MIN, 0.0D, 0.0D, 1.0D, MAX, MAX), // Down North East                              0011100
            Block.makeCuboidShape(MIN, 0.0D, 0.0D, 1.0D, MAX, 1.0D), // Down North South East                       0011101
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, MAX, MAX), // Down North East West                        0011110
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, MAX, 1.0D), // Down North South East West                 0011111
    
            Block.makeCuboidShape(MIN, MIN, MIN, MAX, 1.0D, MAX),  // Up                                            0100000
            Block.makeCuboidShape(MIN, MIN, MIN, MAX, 1.0D, 1.0D), // Up South                                      0100001
            Block.makeCuboidShape(0.0D, MIN, MIN, MAX, 1.0D, MAX), // Up West                                       0100010
            Block.makeCuboidShape(0.0D, MIN, MIN, MAX, 1.0D, 1.0D), // Up South West                                0100011
            Block.makeCuboidShape(MIN, MIN, 0.0D, MAX, 1.0D, MAX), // Up North                                      0100100
            Block.makeCuboidShape(MIN, MIN, 0.0D, MAX, 1.0D, 1.0D), // Up North South                               0100101
            Block.makeCuboidShape(0.0D, MIN, 0.0D, MAX, 1.0D, MAX), // Up North West                                0100110
            Block.makeCuboidShape(0.0D, MIN, 0.0D, MAX, 1.0D, 1.0D), // Up North South West                         0100111
            Block.makeCuboidShape(MIN, MIN, MIN, 1.0D, 1.0D, MAX), // Up East                                       0101000
            Block.makeCuboidShape(MIN, MIN, MIN, 1.0D, 1.0D, 1.0D), // Up East South                                0101001
            Block.makeCuboidShape(0.0D, MIN, MIN, 1.0D, 1.0D, MAX), // Up West East                                 0101010
            Block.makeCuboidShape(0.0D, MIN, MIN, 1.0D, 1.0D, 1.0D), // Up South West East                          0101011
            Block.makeCuboidShape(MIN, MIN, 0.0D, 1.0D, 1.0D, MAX), // Up North East                                0101100
            Block.makeCuboidShape(MIN, MIN, 0.0D, 1.0D, 1.0D, 1.0D), // Up North South East                         0101101
            Block.makeCuboidShape(0.0D, MIN, 0.0D, 1.0D, 1.0D, MAX), // Up North East West                          0101110
            Block.makeCuboidShape(0.0D, MIN, 0.0D, 1.0D, 1.0D, 1.0D), // Up North South East West                   0101111

            Block.makeCuboidShape(MIN, 0.0D, MIN, MAX, 1.0D, MAX),  // Up Down                                      0110000
            Block.makeCuboidShape(MIN, 0.0D, MIN, MAX, 1.0D, 1.0D), // Up Down South                                0110001
            Block.makeCuboidShape(0.0D, 0.0D, MIN, MAX, 1.0D, MAX), // Up Down West                                 0110010
            Block.makeCuboidShape(0.0D, 0.0D, MIN, MAX, 1.0D, 1.0D), // Up Down South West                          0110011
            Block.makeCuboidShape(MIN, 0.0D, 0.0D, MAX, 1.0D, MAX), // Up Down North                                0110100
            Block.makeCuboidShape(MIN, 0.0D, 0.0D, MAX, 1.0D, 1.0D), // Up Down North South                         0110101
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, MAX, 1.0D, MAX), // Up Down North West                          0110110
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, MAX, 1.0D, 1.0D), // Up Down North South West                   0110111
            Block.makeCuboidShape(MIN, 0.0D, MIN, 1.0D, 1.0D, MAX), // Up Down East                                 0111000
            Block.makeCuboidShape(MIN, 0.0D, MIN, 1.0D, 1.0D, 1.0D), // Up Down East South                          0111001
            Block.makeCuboidShape(0.0D, 0.0D, MIN, 1.0D, 1.0D, MAX), // Up Down West East                           0111010
            Block.makeCuboidShape(0.0D, 0.0D, MIN, 1.0D, 1.0D, 1.0D), // Up Down South West East                    0111011
            Block.makeCuboidShape(MIN, 0.0D, 0.0D, 1.0D, 1.0D, MAX), // Up Down North East                          0111100
            Block.makeCuboidShape(MIN, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), // Up Down North South East                   0111101
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, MAX), // Up Down North East West                    0111110
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), // Up Down North South East West             0111111
    
            Block.makeCuboidShape(MINH, MINH, MINH, MAXH, MAXH, MAXH),  // No connection                            1000000
            Block.makeCuboidShape(MINH, MINH, MINH, MAXH, MAXH, 1.0D), // South                                     1000001
            Block.makeCuboidShape(0.0D, MINH, MINH, MAXH, MAXH, MAXH), // West                                      1000010
            Block.makeCuboidShape(0.0D, MINH, MINH, MAXH, MAXH, 1.0D), // South West                                1000011
            Block.makeCuboidShape(MINH, MINH, 0.0D, MAXH, MAXH, MAXH), // North                                     1000100
            Block.makeCuboidShape(MINH, MINH, 0.0D, MAXH, MAXH, 1.0D), // North South                               1000101
            Block.makeCuboidShape(0.0D, MINH, 0.0D, MAXH, MAXH, MAXH), // North West                                1000110
            Block.makeCuboidShape(0.0D, MINH, 0.0D, MAXH, MAXH, 1.0D), // North South West                          1000111
            Block.makeCuboidShape(MINH, MINH, MINH, 1.0D, MAXH, MAXH), // East                                      1001000
            Block.makeCuboidShape(MINH, MINH, MINH, 1.0D, MAXH, 1.0D), // East South                                1001001
            Block.makeCuboidShape(0.0D, MINH, MINH, 1.0D, MAXH, MAXH), // West East                                 1001010
            Block.makeCuboidShape(0.0D, MINH, MINH, 1.0D, MAXH, 1.0D), // South West East                           1001011
            Block.makeCuboidShape(MINH, MINH, 0.0D, 1.0D, MAXH, MAXH), // North East                                1001100
            Block.makeCuboidShape(MINH, MINH, 0.0D, 1.0D, MAXH, 1.0D), // North South East                          1001101
            Block.makeCuboidShape(0.0D, MINH, 0.0D, 1.0D, MAXH, MAXH), // North East West                           1001110
            Block.makeCuboidShape(0.0D, MINH, 0.0D, 1.0D, MAXH, 1.0D), // North South East West                     1001111

            Block.makeCuboidShape(MINH, 0.0D, MINH, MAXH, MAXH, MAXH),  // Down                                     1010000
            Block.makeCuboidShape(MINH, 0.0D, MINH, MAXH, MAXH, 1.0D), // Down South                                1010001
            Block.makeCuboidShape(0.0D, 0.0D, MINH, MAXH, MAXH, MAXH), // Down West                                 1010010
            Block.makeCuboidShape(0.0D, 0.0D, MINH, MAXH, MAXH, 1.0D), // Down South West                           1010011
            Block.makeCuboidShape(MINH, 0.0D, 0.0D, MAXH, MAXH, MAXH), // Down North                                1010100
            Block.makeCuboidShape(MINH, 0.0D, 0.0D, MAXH, MAXH, 1.0D), // Down North South                          1010101
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, MAXH, MAXH, MAXH), // Down North West                           1010110
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, MAXH, MAXH, 1.0D), // Down North South West                     1010111
            Block.makeCuboidShape(MINH, 0.0D, MINH, 1.0D, MAXH, MAXH), // Down East                                 1011000
            Block.makeCuboidShape(MINH, 0.0D, MINH, 1.0D, MAXH, 1.0D), // Down East South                           1011001
            Block.makeCuboidShape(0.0D, 0.0D, MINH, 1.0D, MAXH, MAXH), // Down West East                            1011010
            Block.makeCuboidShape(0.0D, 0.0D, MINH, 1.0D, MAXH, 1.0D), // Down South West East                      1011011
            Block.makeCuboidShape(MINH, 0.0D, 0.0D, 1.0D, MAXH, MAXH), // Down North East                           1011100
            Block.makeCuboidShape(MINH, 0.0D, 0.0D, 1.0D, MAXH, 1.0D), // Down North South East                     1011101
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, MAXH, MAXH), // Down North East West                      1011110
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, MAXH, 1.0D), // Down North South East West                1011111
    
            Block.makeCuboidShape(MINH, MINH, MINH, MAXH, 1.0D, MAXH),  // Up                                       1100000
            Block.makeCuboidShape(MINH, MINH, MINH, MAXH, 1.0D, 1.0D), // Up South                                  1100001
            Block.makeCuboidShape(0.0D, MINH, MINH, MAXH, 1.0D, MAXH), // Up West                                   1100010
            Block.makeCuboidShape(0.0D, MINH, MINH, MAXH, 1.0D, 1.0D), // Up South West                             1100011
            Block.makeCuboidShape(MINH, MINH, 0.0D, MAXH, 1.0D, MAXH), // Up North                                  1100100
            Block.makeCuboidShape(MINH, MINH, 0.0D, MAXH, 1.0D, 1.0D), // Up North South                            1100101
            Block.makeCuboidShape(0.0D, MINH, 0.0D, MAXH, 1.0D, MAXH), // Up North West                             1100110
            Block.makeCuboidShape(0.0D, MINH, 0.0D, MAXH, 1.0D, 1.0D), // Up North South West                       1100111
            Block.makeCuboidShape(MINH, MINH, MINH, 1.0D, 1.0D, MAXH), // Up East                                   1101000
            Block.makeCuboidShape(MINH, MINH, MINH, 1.0D, 1.0D, 1.0D), // Up East South                             1101001
            Block.makeCuboidShape(0.0D, MINH, MINH, 1.0D, 1.0D, MAXH), // Up West East                              1101010
            Block.makeCuboidShape(0.0D, MINH, MINH, 1.0D, 1.0D, 1.0D), // Up South West East                        1101011
            Block.makeCuboidShape(MINH, MINH, 0.0D, 1.0D, 1.0D, MAXH), // Up North East                             1101100
            Block.makeCuboidShape(MINH, MINH, 0.0D, 1.0D, 1.0D, 1.0D), // Up North South East                       1101101
            Block.makeCuboidShape(0.0D, MINH, 0.0D, 1.0D, 1.0D, MAXH), // Up North East West                        1101110
            Block.makeCuboidShape(0.0D, MINH, 0.0D, 1.0D, 1.0D, 1.0D), // Up North South East West                  1101111

            Block.makeCuboidShape(MINH, 0.0D, MINH, MAXH, 1.0D, MAXH),  // Up Down                                  1110000
            Block.makeCuboidShape(MINH, 0.0D, MINH, MAXH, 1.0D, 1.0D), // Up Down South                             1110001
            Block.makeCuboidShape(0.0D, 0.0D, MINH, MAXH, 1.0D, MAXH), // Up Down West                              1110010
            Block.makeCuboidShape(0.0D, 0.0D, MINH, MAXH, 1.0D, 1.0D), // Up Down South West                        1110011
            Block.makeCuboidShape(MINH, 0.0D, 0.0D, MAXH, 1.0D, MAXH), // Up Down North                             1110100
            Block.makeCuboidShape(MINH, 0.0D, 0.0D, MAXH, 1.0D, 1.0D), // Up Down North South                       1110101
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, MAXH, 1.0D, MAXH), // Up Down North West                        1110110
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, MAXH, 1.0D, 1.0D), // Up Down North South West                  1110111
            Block.makeCuboidShape(MINH, 0.0D, MINH, 1.0D, 1.0D, MAXH), // Up Down East                              1111000
            Block.makeCuboidShape(MINH, 0.0D, MINH, 1.0D, 1.0D, 1.0D), // Up Down East South                        1111001
            Block.makeCuboidShape(0.0D, 0.0D, MINH, 1.0D, 1.0D, MAXH), // Up Down West East                         1111010
            Block.makeCuboidShape(0.0D, 0.0D, MINH, 1.0D, 1.0D, 1.0D), // Up Down South West East                   1111011
            Block.makeCuboidShape(MINH, 0.0D, 0.0D, 1.0D, 1.0D, MAXH), // Up Down North East                        1111100
            Block.makeCuboidShape(MINH, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), // Up Down North South East                  1111101
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, MAXH), // Up Down North East West                   1111110
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)}; // Up Down North South East West            1111111

    public enum EnumWireType implements IStringSerializable
    {
        ALUMINUM_WIRE(0, "alu_wire"),
        ALUMINUM_WIRE_HEAVY(1, "alu_wire_heavy"),
        ALUMINUM_WIRE_SWITCHED(2, "alu_wire_switch"),
        ALUMINUM_WIRE_SWITCHED_HEAVY(3, "alu_wire_switch_heavy");

        private final int meta;
        private final String name;

        EnumWireType(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta()
        {
            return this.meta;
        }

        private final static EnumWireType[] values = values();
        public static EnumWireType byMetadata(int meta)
        {
            return values[meta % values.length];
        }

        @Override
        public String getName()
        {
            return this.name;
        }
    }

    public BlockAluminumWire(Properties builder)
    {
        super(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
//        state = this.getActualState(state, source, pos);
        return BOUNDING_BOXES[getBoundingBoxIdx(state)];
    }

    private static int getBoundingBoxIdx(BlockState state)
    {
        int i = 0;

        if (state.get(NORTH))
        {
            i |= 1 << Direction.NORTH.getHorizontalIndex();
        }

        if (state.get(EAST))
        {
            i |= 1 << Direction.EAST.getHorizontalIndex();
        }

        if (state.get(SOUTH))
        {
            i |= 1 << Direction.SOUTH.getHorizontalIndex();
        }

        if (state.get(WEST))
        {
            i |= 1 << Direction.WEST.getHorizontalIndex();
        }

        if (state.get(DOWN))
        {
            i |= 1 << 4;
        }

        if (state.get(UP))
        {
            i |= 1 << 5;
        }

        // Is heavy:
        if (state.get(WIRE_TYPE).ordinal() % 2 == 1)
        {
            i |= 1 << 6;
        }

        return i;
    }

//    @Override
//    public ItemGroup getCreativeTabToDisplayOn()
//    {
//        return GalacticraftCore.galacticraftBlocksTab;
//    }
//
//    @Override
//    public boolean isOpaqueCube(BlockState state)
//    {
//        return false;
//    }
//
//    @Override
//    public boolean isFullCube(BlockState state)
//    {
//        return false;
//    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        switch (state.get(WIRE_TYPE))
        {
        default:
        case ALUMINUM_WIRE:
            return new TileEntityAluminumWire.TileEntityAluminumWireT1();
        case ALUMINUM_WIRE_HEAVY:
            return new TileEntityAluminumWire.TileEntityAluminumWireT2();
        case ALUMINUM_WIRE_SWITCHED:
            return new TileEntityAluminumWireSwitch.TileEntityAluminumWireSwitchableT1();
        case ALUMINUM_WIRE_SWITCHED_HEAVY:
            return new TileEntityAluminumWireSwitch.TileEntityAluminumWireSwitchableT2();
        }
    }

    //    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void getSubBlocks(ItemGroup tab, NonNullList<ItemStack> list)
//    {
//        list.add(new ItemStack(this, 1, 0));
//        list.add(new ItemStack(this, 1, 1));
//        list.add(new ItemStack(this, 1, 2));
//        list.add(new ItemStack(this, 1, 3));
//    }

    @Override
    public NetworkType getNetworkType(BlockState state)
    {
        return NetworkType.POWER;
    }

    @Override
    public String getShiftDescription(ItemStack item)
    {
        switch (item.getDamage())
        {
        case 0:
            return GCCoreUtil.translate("tile.aluminum_wire.alu_wire.description");
        case 1:
            return GCCoreUtil.translate("tile.aluminum_wire.alu_wire_heavy.description");
        case 2:
            return GCCoreUtil.translate("tile.aluminum_wire.alu_wire_switch.description");
        case 3:
            return GCCoreUtil.translate("tile.aluminum_wire.alu_wire_switch_heavy.description");
        }
        return "";
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WIRE_TYPE, UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public boolean showDescription(ItemStack item)
    {
        return true;
    }

//    @Override
//    public EnumSortCategoryBlock getCategory(ItemStack item)
//    {
//        return EnumSortCategoryBlock.TRANSMITTER;
//    }
}
