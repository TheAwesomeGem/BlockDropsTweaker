package net.theawesomegem.blockdropstweaker.common.command.get;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
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
public class BlockGetCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "get";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.get";
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

        boolean isFortune = (args.length >= 1 && args[0].equals("fortunechance"));

        if(isFortune)
        {
            player.sendMessage(ChatUtil.getNormalMessage("---Fortune Chance---"));

            for(Entry<Integer, Integer> fortuneChanceEntry : blockDropData.fortunechance.entrySet())
            {
                player.sendMessage(ChatUtil.getNormalMessage("Level " + fortuneChanceEntry.getKey() + "=" + fortuneChanceEntry.getValue() + "%"));
            }

            player.sendMessage(ChatUtil.getNormalMessage("-------------------"));

            return;
        }

        player.sendMessage(ChatUtil.getNormalMessage("Replace=" + (blockDropData.replace)));
        player.sendMessage(ChatUtil.getNormalMessage("Silk Touch=" + (blockDropData.allowsilktouch)));
        player.sendMessage(ChatUtil.getNormalMessage("Always Silk Touch=" + (blockDropData.dropsilktouchalways)));
        player.sendMessage(ChatUtil.getNormalMessage("Y-Level=" + (blockDropData.minYLevel + ":" + blockDropData.maxYLevel)));
        player.sendMessage(ChatUtil.getNormalMessage("Exp=" + (blockDropData.minExp + ":" + blockDropData.maxExp)));
        player.sendMessage(ChatUtil.getNormalMessage("Tools Blacklist=" + (blockDropData.toolsBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Biomes Blacklist=" + (blockDropData.biomeBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Gamestages Blacklist=" + (blockDropData.gamestageBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Gamestages All=" + (blockDropData.gamestageAll)));
        player.sendMessage(ChatUtil.getNormalMessage("Modifiers Blacklist=" + (blockDropData.modifierBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Traits Blacklist=" + (blockDropData.traitBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("Enchants Blacklist=" + (blockDropData.enchantmentBlacklist)));
        player.sendMessage(ChatUtil.getNormalMessage("NBTs Blacklist=" + (blockDropData.nbtBlacklist)));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        List<String> tabList = new ArrayList<>();

        tabList.add("fortunechance");

        return getListOfStringsMatchingLastWord(args, tabList);
    }
}
