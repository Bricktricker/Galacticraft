package micdoodle8.mods.galacticraft.core.command;

import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class CommandGCKit extends CommandBase
{
    @Override
    public String getUsage(ICommandSender var1)
    {
        return "/" + this.getName() + " [<player>]";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getName()
    {
        return "gckit";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        EntityPlayerMP playerBase;

        if (args.length < 2)
        {
            try
            {
                if (args.length == 1)
                {
                    playerBase = PlayerUtil.getPlayerBaseServerFromPlayerUsername(args[0], true);
                } else
                {
                    playerBase = PlayerUtil.getPlayerBaseServerFromPlayerUsername(sender.getName(), true);
                }

                if (playerBase != null)
                {
                    ItemHandlerHelper.giveItemToPlayer(playerBase, new ItemStack(GCItems.emergencyKit), 0);
                    CommandBase.notifyCommandListener(sender, this, "commands.emergencykit", EnumColor.GREY + "[" + playerBase.getName(), "]");
                } else
                {
                    throw new Exception("Could not find player with name: " + args[0]);
                }
            } catch (final Exception var6)
            {
                throw new CommandException(var6.getMessage());
            }
        } else
        {
            throw new WrongUsageException(GCCoreUtil.translateWithFormat("commands.dimensiontp.too_many", this.getUsage(sender)));
        }
    }
}
