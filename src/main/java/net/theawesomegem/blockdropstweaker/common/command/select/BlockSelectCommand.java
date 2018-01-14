package net.theawesomegem.blockdropstweaker.common.command.select;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheAwesomeGem on 1/6/2018.
 */
public class BlockSelectCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "select";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.select";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        if (args.length < 1)
        {
            throw new WrongUsageException(this.getUsage(player));
        }

        String itemData = args[0];

        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);

        if (!ConfigurationHandler.blockDropMap.containsKey(itemData))
        {
            player.sendMessage(ChatUtil.getNormalMessage("The block does not exist. Please add it first to configure the drops."));

            return;
        }

        playerData.setSelectedBlock(itemData);

        player.sendMessage(ChatUtil.getNormalMessage("Selected Block:" + itemData));

    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        List<String> tabList = new ArrayList<>();

        if (!(sender instanceof EntityPlayer))
            return tabList;

        tabList.addAll(ConfigurationHandler.blockDropMap.keySet());

        return getListOfStringsMatchingLastWord(args, tabList);
    }
}
