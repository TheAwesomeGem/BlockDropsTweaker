package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.theawesomegem.blockdropstweaker.ModInfo;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/6/2018.
 */
public abstract class BlockDropsBaseCommand extends CommandBase
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

        if(world.isRemote)
            return;

        if(!(sender instanceof EntityPlayer))
        {
            sender.sendMessage(ChatUtil.getNormalMessage("This command is player based. Sorry!"));

            return;
        }

        EntityPlayer player = (EntityPlayer) sender;

        onCommand(server, player, args);
    }

    protected abstract void onCommand(MinecraftServer server, EntityPlayer player, String[] args)  throws CommandException;
    protected abstract String getCommandPath();

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
}
