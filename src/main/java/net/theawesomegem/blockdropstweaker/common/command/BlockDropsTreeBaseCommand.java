package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.server.command.CommandTreeBase;
import net.theawesomegem.blockdropstweaker.ModInfo;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by TheAwesomeGem on 1/6/2018.
 */
public abstract class BlockDropsTreeBaseCommand extends CommandTreeBase
{
    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands." + ModInfo.MOD_ID + "." + getCommandPath() + ".usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        World world = sender.getEntityWorld();

        if (world.isRemote)
            return;

        if (!(sender instanceof EntityPlayer))
        {
            sender.sendMessage(ChatUtil.getNormalMessage("This command is player based. Sorry!"));

            return;
        }

        EntityPlayer player = (EntityPlayer) sender;

        if(!(args.length > 0 && (getSubCommand(args[0]) != null)))
        {
            if (!onPreCommand(server, player, args))
                return;
        }

        if (args.length > 0)
        {
            ICommand cmd = getSubCommand(args[0]);

            if (cmd == null)
            {
                String subCommandsString = getAvailableSubCommandsString(server, sender);

                throw new CommandException("commands.tree_base.invalid_cmd.list_subcommands", args[0], subCommandsString);
            }
            else if (!cmd.checkPermission(server, sender))
            {
                throw new CommandException("commands.generic.permission");
            }
            else
            {
                cmd.execute(server, sender, shiftArgs(args));
            }

            return;
        }

        onCommand(server, player, args);
    }

    protected boolean onPreCommand(MinecraftServer server, EntityPlayer player, String[] args)  throws CommandException
    {
        return true;
    }

    protected abstract String getCommandPath();
    protected abstract void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException;

    @Override
    public int getRequiredPermissionLevel()
    {
        return 4;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return super.checkPermission(server, sender);
    }

    //Y u make this private and not protected CommandTreeBase.java class?
    //============================================================================
    private static String[] shiftArgs(@Nullable String[] s)
    {
        if(s == null || s.length == 0)
        {
            return new String[0];
        }

        String[] s1 = new String[s.length - 1];
        System.arraycopy(s, 1, s1, 0, s1.length);

        return s1;
    }

    private String getAvailableSubCommandsString(MinecraftServer server, ICommandSender sender)
    {
        Collection<String> availableCommands = new ArrayList<>();

        for (ICommand command : getSubCommands())
        {
            if (command.checkPermission(server, sender))
            {
                availableCommands.add(command.getName());
            }
        }

        return CommandBase.joinNiceStringFromCollection(availableCommands);
    }
    //============================================================================
}
