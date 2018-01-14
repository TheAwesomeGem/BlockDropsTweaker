package net.theawesomegem.blockdropstweaker.common.command.set;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.FortuneQuantityData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsTreeBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by TheAwesomeGem on 1/9/2018.
 */
public class DropSetCommand extends BlockDropsTreeBaseCommand
{
    public DropSetCommand()
    {
        this.addSubcommand(new DropSetFortuneChanceCommand());
        this.addSubcommand(new DropSetFortuneQuantityCommand());
    }

    @Override
    public String getName()
    {
        return "set";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.set";
    }

    @Override
    protected boolean onPreCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
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

            return false;
        }

        DropData dropData = DropData.getDropData(blockDropData, playerData.getSelectedDrop());

        if(dropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Select a drop first using '/bd drop select'"));

            return false;
        }

        String key = args[0];
        String value = args[1];

        switch (key.toLowerCase())
        {
            case "chance":
            {
                int chance;

                try
                {
                    chance = Integer.parseInt(value);
                }
                catch (NumberFormatException e)
                {
                    player.sendMessage(ChatUtil.getNormalMessage("'chance' can only be a number."));

                    return false;
                }

                for(Integer fortuneLevel : dropData.fortunechancemap.keySet())
                {
                    dropData.fortunechancemap.put(fortuneLevel, chance);
                }

                player.sendMessage(ChatUtil.getNormalMessage("'chance' set to '" + chance + "' while ignoring any fortune."));

                return false;
            }

            case "quantity":
            {
                String[] quantityInfo = value.split(":");

                if(quantityInfo.length < 2)
                {
                    player.sendMessage(ChatUtil.getNormalMessage("Please use the format 'min:max'. For example '1:5' for 1 to 5 quantity"));

                    return false;
                }

                int minQuantity;
                int maxQuantity;

                try
                {
                    minQuantity = Integer.parseInt(quantityInfo[0]);
                    maxQuantity = Integer.parseInt(quantityInfo[1]);
                }
                catch (NumberFormatException e)
                {
                    player.sendMessage(ChatUtil.getNormalMessage("'quantity' can only be a whole number."));

                    return false;
                }

                for(Integer fortuneLevel : dropData.fortunequantitymap.keySet())
                {
                    FortuneQuantityData quantityData = new FortuneQuantityData();
                    quantityData.minquantity = minQuantity;
                    quantityData.maxquantity = maxQuantity;

                    dropData.fortunequantitymap.put(fortuneLevel, quantityData);
                }

                player.sendMessage(ChatUtil.getNormalMessage("'quantity' set to '" + minQuantity + ":" + maxQuantity + "' while ignoring any fortune."));

                return false;
            }

            case "ylevel":
            {
                String[] ylevelInfo = value.split(":");

                if(ylevelInfo.length < 2)
                {
                    player.sendMessage(ChatUtil.getNormalMessage("Please use the format 'min:max'. For example '0:256' for 0 to 256 y-level"));

                    return false;
                }

                int minYLevel;
                int maxYLevel;

                try
                {
                    minYLevel = Integer.parseInt(ylevelInfo[0]);
                    maxYLevel = Integer.parseInt(ylevelInfo[1]);
                }
                catch (NumberFormatException e)
                {
                    player.sendMessage(ChatUtil.getNormalMessage("'ylevel' can only be a whole number."));

                    return false;
                }

                dropData.minYLevel = minYLevel;
                dropData.maxYLevel = maxYLevel;

                player.sendMessage(ChatUtil.getNormalMessage("'ylevel' set to '" + minYLevel + ":" + maxYLevel + "'"));

                return false;
            }

            case "toolblacklist":
            {
                dropData.toolsBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'toolblacklist' set to '" + (dropData.toolsBlacklist) + "'"));


                return false;
            }

            case "biomeblacklist":
            {
                dropData.biomeBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'biomeblacklist' set to '" + (dropData.biomeBlacklist) + "'"));


                return false;
            }

            case "gamestageblacklist":
            {
                dropData.gamestageBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'gamestageblacklist' set to '" + (dropData.gamestageBlacklist) + "'"));


                return false;
            }

            case "gamestageall":
            {
                dropData.gamestageAll = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'gamestageall' set to '" + (dropData.gamestageAll) + "'"));


                return false;
            }

            case "modifierblacklist":
            {
                dropData.modifierBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'modifierblacklist' set to '" + (dropData.modifierBlacklist) + "'"));


                return false;
            }

            case "traitblacklist":
            {
                dropData.traitBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'traitblacklist' set to '" + (dropData.traitBlacklist) + "'"));


                return false;
            }

            case "enchantmentblacklist":
            {
                dropData.enchantmentBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'enchantmentblacklist' set to '" + (dropData.enchantmentBlacklist) + "'"));


                return false;
            }

            case "nbtblacklist":
            {
                dropData.nbtBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'nbtblacklist' set to '" + (dropData.nbtBlacklist) + "'"));


                return false;
            }
        }

        return false;
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {

    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        List<String> tabListItems = super.getTabCompletions(server, sender, args, pos);

        if(args.length == 1)
        {
            tabListItems.add("chance");
            tabListItems.add("quantity");
            tabListItems.add("fortunechance");
            tabListItems.add("fortunequantity");
            tabListItems.add("ylevel");
            tabListItems.add("toolblacklist");
            tabListItems.add("biomeblacklist");
            tabListItems.add("gamestageblacklist");
            tabListItems.add("gamestageall");
            tabListItems.add("modifierblacklist");
            tabListItems.add("traitblacklist");
            tabListItems.add("enchantmentblacklist");
            tabListItems.add("nbtblacklist");
        }

        return getListOfStringsMatchingLastWord(args, tabListItems);
    }
}
