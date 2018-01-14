package net.theawesomegem.blockdropstweaker.common.command.util;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class CopyCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "copy";
    }

    @Override
    protected String getCommandPath()
    {
        return "copy";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        if(args.length <= 0)
            return;

        String message = args[0];

        ChatUtil.copyMessage(player, message);
        player.sendMessage(ChatUtil.getNormalMessage("'" + message + "' copied to clipboard."));
    }
}