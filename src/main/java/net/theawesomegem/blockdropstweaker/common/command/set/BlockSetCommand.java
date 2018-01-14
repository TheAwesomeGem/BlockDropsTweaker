package net.theawesomegem.blockdropstweaker.common.command.set;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsTreeBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class BlockSetCommand extends BlockDropsTreeBaseCommand
{
    public BlockSetCommand()
    {
        this.addSubcommand(new BlockSetFortuneChanceCommand());
    }

    @Override
    public String getName()
    {
        return "set";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.set";
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
            player.sendMessage(ChatUtil.getNormalMessage("Select a block first using '/bd block select'"));

            return false;
        }

        String key = args[0];
        String value = args[1];

        switch (key.toLowerCase())
        {
            case "replace":
            {
                boolean replace = Boolean.parseBoolean(value);

                blockDropData.replace = replace;

                player.sendMessage(ChatUtil.getNormalMessage("'replace' set to '" + replace + "'"));

                return false;
            }

            case "silktouch":
            {
                boolean silkTouch = Boolean.parseBoolean(value);

                blockDropData.allowsilktouch = silkTouch;

                player.sendMessage(ChatUtil.getNormalMessage("'silktouch' set to '" + silkTouch + "'"));

                return false;
            }

            case "alwayssilktouch":
            {
                boolean alwaysSilkTouch = Boolean.parseBoolean(value);

                blockDropData.dropsilktouchalways = alwaysSilkTouch;

                player.sendMessage(ChatUtil.getNormalMessage("'alwayssilktouch' set to '" + alwaysSilkTouch + "'"));

                return false;
            }

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

                for(Integer fortuneLevel : blockDropData.fortunechance.keySet())
                {
                    blockDropData.fortunechance.put(fortuneLevel, chance);
                }

                player.sendMessage(ChatUtil.getNormalMessage("'chance' set to '" + chance + "' while ignoring any fortune."));

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

                blockDropData.minYLevel = minYLevel;
                blockDropData.maxYLevel = maxYLevel;

                player.sendMessage(ChatUtil.getNormalMessage("'ylevel' set to '" + minYLevel + ":" + maxYLevel + "'"));

                return false;
            }

            case "toolblacklist":
            {
                blockDropData.toolsBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'toolblacklist' set to '" + (blockDropData.toolsBlacklist) + "'"));


                return false;
            }

            case "biomeblacklist":
            {
                blockDropData.biomeBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'biomeblacklist' set to '" + (blockDropData.biomeBlacklist) + "'"));


                return false;
            }

            case "gamestageblacklist":
            {
                blockDropData.gamestageBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'gamestageblacklist' set to '" + (blockDropData.gamestageBlacklist) + "'"));


                return false;
            }

            case "gamestageall":
            {
                blockDropData.gamestageAll = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'gamestageall' set to '" + (blockDropData.gamestageAll) + "'"));


                return false;
            }

            case "modifierblacklist":
            {
                blockDropData.modifierBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'modifierblacklist' set to '" + (blockDropData.modifierBlacklist) + "'"));


                return false;
            }

            case "traitblacklist":
            {
                blockDropData.traitBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'traitblacklist' set to '" + (blockDropData.traitBlacklist) + "'"));


                return false;
            }

            case "enchantmentblacklist":
            {
                blockDropData.enchantmentBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'enchantmentblacklist' set to '" + (blockDropData.enchantmentBlacklist) + "'"));


                return false;
            }

            case "nbtblacklist":
            {
                blockDropData.nbtBlacklist = Boolean.parseBoolean(value);

                player.sendMessage(ChatUtil.getNormalMessage("'nbtblacklist' set to '" + (blockDropData.nbtBlacklist) + "'"));


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
            tabListItems.add("replace");
            tabListItems.add("silktouch");
            tabListItems.add("alwayssilktouch");
            tabListItems.add("chance");
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
