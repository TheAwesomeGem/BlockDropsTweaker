package net.theawesomegem.blockdropstweaker.common.command.util;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class ReloadCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "reload";
    }

    @Override
    protected String getCommandPath()
    {
        return "reload";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        ConfigurationHandler.loadConfig();

        player.sendMessage(ChatUtil.getNormalMessage("Reloaded from the JSON."));
        player.sendMessage(ChatUtil.getNormalMessage("Any unsaved changes will be lost."));
    }
}
