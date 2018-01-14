package net.theawesomegem.blockdropstweaker.common.command.set;

import net.minecraft.command.CommandException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/9/2018.
 */
public class DropSetFortuneChanceCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "fortunechance";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.set.fortunechance";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        if(args.length < 2)
        {
            throw new WrongUsageException(this.getUsage(player));
        }

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

        String fortuneLevelStr = args[0];
        String chanceStr = args[1];

        int fortuneLevel, chance;

        try
        {
            fortuneLevel = Integer.parseInt(fortuneLevelStr);
            chance = Integer.parseInt(chanceStr);
        }
        catch (NumberFormatException e)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Both the fortune level and the chance must be whole numbers."));

            return;
        }

        dropData.fortunechancemap.put(fortuneLevel, chance);

        player.sendMessage(ChatUtil.getNormalMessage("Fortune Level '" + fortuneLevelStr + "' 'chance' set to '" + chanceStr + "'"));
    }
}
