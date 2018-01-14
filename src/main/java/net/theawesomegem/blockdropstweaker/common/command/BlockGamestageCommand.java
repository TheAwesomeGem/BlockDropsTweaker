package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.BlockGamestageAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockGamestageClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockGamestageRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class BlockGamestageCommand extends BlockDropsTreeBaseCommand
{
    public BlockGamestageCommand()
    {
        addSubcommand(new BlockGamestageAddCommand());
        addSubcommand(new BlockGamestageRemoveCommand());
        addSubcommand(new BlockGamestageClearCommand());
    }

    @Override
    public String getName()
    {
        return "gamestage";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.gamestage";
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

        player.sendMessage(ChatUtil.getNormalMessage("-----Gamestages(" + (blockDropData.gamestageAll ? "All" : "Any")  + ")-----"));

        for(String gamestage : blockDropData.gameStageList)
        {
            player.sendMessage(ChatUtil.getNormalMessage(gamestage));
        }

        player.sendMessage(ChatUtil.getNormalMessage("---------------------"));
    }
}
