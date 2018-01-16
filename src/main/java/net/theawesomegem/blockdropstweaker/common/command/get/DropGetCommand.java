package net.theawesomegem.blockdropstweaker.common.command.get;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.FortuneQuantityData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Created by TheAwesomeGem on 1/9/2018.
 */
public class DropGetCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "get";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.get";
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

        DropData dropData = DropData.getDropData(blockDropData, playerData.getSelectedDrop());

        if(dropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Select a drop first using '/bd drop select'"));

            return;
        }

        boolean isFortuneChance = (args.length >= 1 && args[0].equals("fortunechance"));
        boolean isFortuneQuantity = (args.length >= 1 && args[0].equals("fortunequantity"));

        if(isFortuneChance)
        {
            player.sendMessage(ChatUtil.getNormalMessage("---Fortune Chance---"));

            for(Entry<Integer, Integer> fortuneChanceEntry : dropData.fortunechancemap.entrySet())
            {
                player.sendMessage(ChatUtil.getNormalMessage("Level " + fortuneChanceEntry.getKey() + "=" + fortuneChanceEntry.getValue() + "%"));
            }

            player.sendMessage(ChatUtil.getNormalMessage("-------------------"));

            return;
        }

        if(isFortuneQuantity)
        {
            player.sendMessage(ChatUtil.getNormalMessage("---Fortune Quantity---"));

            for(Entry<Integer, FortuneQuantityData> fortuneChanceEntry : dropData.fortunequantitymap.entrySet())
            {
                player.sendMessage(ChatUtil.getNormalMessage("Level " + fortuneChanceEntry.getKey() + "=" + fortuneChanceEntry.getValue().minquantity + ":" + fortuneChanceEntry.getValue().maxquantity));
            }

            player.sendMessage(ChatUtil.getNormalMessage("----------------------"));

            return;
        }

        player.sendMessage(ChatUtil.getNormalMessage("Y-Level=" + (dropData.minYLevel + ":" + dropData.maxYLevel)));
        player.sendMessage(ChatUtil.getNormalMessage("Exp=" + (dropData.minExp + ":" + dropData.maxExp)));
        player.sendMessage(ChatUtil.getNormalMessage("Tools Blacklist=" + (dropData.toolsBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Biomes Blacklist=" + (dropData.biomeBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Gamestages Blacklist=" + (dropData.gamestageBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Gamestages All=" + (dropData.gamestageAll)));
        player.sendMessage(ChatUtil.getNormalMessage("Modifiers Blacklist=" + (dropData.modifierBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Traits Blacklist=" + (dropData.traitBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Enchants Blacklist=" + (dropData.enchantmentBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("NBTs Blacklist=" + (dropData.nbtBlacklist)));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        List<String> tabList = new ArrayList<>();

        tabList.add("fortunechance");
        tabList.add("fortunequantity");

        return getListOfStringsMatchingLastWord(args, tabList);
    }
}