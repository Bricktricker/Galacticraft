package micdoodle8.mods.galacticraft.planets.mars.world.gen;

import com.mojang.datafixers.Dynamic;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.DungeonConfiguration;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.MapGenDungeon;

import java.util.function.Function;

public class MapGenDungeonMars extends MapGenDungeon
{
    public MapGenDungeonMars(Function<Dynamic<?>, ? extends DungeonConfiguration> func)
    {
        super(func);
    }

    @Override
    public String getStructureName()
    {
        return "GC_Dungeon_Mars";
    }
}
