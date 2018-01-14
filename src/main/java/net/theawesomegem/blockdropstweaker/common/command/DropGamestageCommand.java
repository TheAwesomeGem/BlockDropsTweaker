package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.DropGamestageAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropGamestageClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropGamestageRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class DropGamestageCommand extends BlockDropsTreeBaseCommand
{
    public DropGamestageCommand()
    {
        addSubcommand(new DropGamestageAddCommand());
        addSubcommand(new DropGamestageRemoveCommand());
        addSubcommand(new DropGamestageClearCommand());
    }

    @Override
    public String getName()
    {
        return "gamestage";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.gamestage";
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

        player.sendMessage(ChatUtil.getNormalMessage("-----Gamestages(" + (dropData.gamestageAll ? "All" : "Any")  + ")-----"));

        for(String gamestage : dropData.gameStageList)
        {
            player.sendMessage(ChatUtil.getNormalMessage(gamestage));
        }

        player.sendMessage(ChatUtil.getNormalMessage("---------------------"));
    }
}
