package net.theawesomegem.blockdropstweaker.common.command.add;

import net.minecraft.command.CommandException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.Primary;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class BlockGamestageAddCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "add";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.gamestage.add";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        if(args.length < 1)
        {
            throw new WrongUsageException(this.getUsage(player));
        }

        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);

        BlockDropData blockDropData = (playerData.getSelectedBlock() == null ? null : ConfigurationHandler.blockDropMap.get(playerData.getSelectedBlock()));

        if(blockDropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Select a block first using '/bd block select'"));

            return;
        }

        String gameStage = args[0];

        if(!Primary.Instance.isGameStagesLoaded())
        {
            player.sendMessage(ChatUtil.getNormalMessage("Gamestage mod is not loaded."));

            return;
        }

        if(blockDropData.gameStageList.contains(gameStage))
        {
            player.sendMessage(ChatUtil.getNormalMessage("That gamestage already exists."));

            return;
        }

        blockDropData.gameStageList.add(gameStage);

        player.sendMessage(ChatUtil.getNormalMessage("Added Gamestage '" + gameStage + "'"));
    }
}
