package net.theawesomegem.blockdropstweaker.common.command.set;

import net.minecraft.command.CommandException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.FortuneQuantityData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/9/2018.
 */
public class DropSetFortuneQuantityCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "fortunequantity";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.set.fortunequantity";
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
        String value = args[1];

        String[] quantityInfo = value.split(":");

        if(quantityInfo.length < 2)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Please use the format 'min:max'. For example '1:5' for 1 to 5 quantity"));

            return;
        }

        int fortuneLevel, minQuantity, maxQuantity;

        try
        {
            fortuneLevel = Integer.parseInt(fortuneLevelStr);
            minQuantity = Integer.parseInt(quantityInfo[0]);
            maxQuantity = Integer.parseInt(quantityInfo[1]);
        }
        catch (NumberFormatException e)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Both the fortune level and the quantity must be whole numbers."));

            return;
        }

        FortuneQuantityData quantityData = new FortuneQuantityData();
        quantityData.minquantity = minQuantity;
        quantityData.maxquantity = maxQuantity;

        dropData.fortunequantitymap.put(fortuneLevel, quantityData);

        player.sendMessage(ChatUtil.getNormalMessage("Fortune Level '" + fortuneLevelStr + "' 'quantity' set to '" + minQuantity + ":" + maxQuantity + "'"));
    }
}