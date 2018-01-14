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
public class SaveCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "save";
    }

    @Override
    protected String getCommandPath()
    {
        return "save";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        ConfigurationHandler.saveConfig();

        player.sendMessage(ChatUtil.getNormalMessage("Saved current data to JSON."));
    }
}
