package net.theawesomegem.blockdropstweaker.common.command.remove;

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
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class BlockEnchantRemoveCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "remove";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.enchant.remove";
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

        String enchant = args[0];

        if(!blockDropData.enchantmentList.contains(enchant))
        {
            player.sendMessage(ChatUtil.getNormalMessage("That enchant does not exist."));

            return;
        }

        blockDropData.enchantmentList.remove(enchant);

        player.sendMessage(ChatUtil.getNormalMessage("Removed Enchantment '" + enchant + "'"));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        List<String> tabList = new ArrayList<>();

        if(args.length == 1)
        {
            if(sender instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) sender;
                IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);

                BlockDropData blockDropData = (playerData.getSelectedBlock() == null ? null : ConfigurationHandler.blockDropMap.get(playerData.getSelectedBlock()));

                if (blockDropData != null)
                {
                    tabList.addAll(getListOfStringsMatchingLastWord(args, blockDropData.enchantmentList));
                }
            }
        }

        return tabList;
    }
}
