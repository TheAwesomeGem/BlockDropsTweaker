package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.DropToolAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropToolClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropToolRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/9/2018.
 */
public class DropToolCommand extends BlockDropsTreeBaseCommand
{
    public DropToolCommand()
    {
        addSubcommand(new DropToolAddCommand());
        addSubcommand(new DropToolRemoveCommand());
        addSubcommand(new DropToolClearCommand());
    }

    @Override
    public String getName()
    {
        return "tool";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.tool";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);

        BlockDropData blockDropData = (playerData.getSelectedBlock() == null ? null : ConfigurationHandler.blockDropMap.get(playerData.getSelectedBlock()));

        if(blockDropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("The block associated with the drop does not exist."));

            return;
        }

        DropData dropData = DropData.getDropData(blockDropData, playerData.getSelectedDrop());

        if(dropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Select a drop first using '/bd drop select'"));

            return;
        }

        player.sendMessage(ChatUtil.getNormalMessage("-----Tools-----"));

        for(String toolInfo : dropData.tools)
        {
            player.sendMessage(ChatUtil.getNormalMessage(toolInfo));
        }

        player.sendMessage(ChatUtil.getNormalMessage("---------------"));
    }
}
