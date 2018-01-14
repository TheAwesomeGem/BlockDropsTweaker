package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.BlockToolAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockToolClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockToolRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/9/2018.
 */
public class BlockToolCommand extends BlockDropsTreeBaseCommand
{
    public BlockToolCommand()
    {
        addSubcommand(new BlockToolAddCommand());
        addSubcommand(new BlockToolRemoveCommand());
        addSubcommand(new BlockToolClearCommand());
    }

    @Override
    public String getName()
    {
        return "tool";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.tool";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);

        BlockDropData blockDropData = (playerData.getSelectedBlock() == null ? null : ConfigurationHandler.blockDropMap.get(playerData.getSelectedBlock()));

        if(blockDropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Select a block first using '/bd block select'"));

            return;
        }

        player.sendMessage(ChatUtil.getNormalMessage("-----Tools-----"));

        for(String toolInfo : blockDropData.tools)
        {
            player.sendMessage(ChatUtil.getNormalMessage(toolInfo));
        }

        player.sendMessage(ChatUtil.getNormalMessage("---------------"));
    }
}
