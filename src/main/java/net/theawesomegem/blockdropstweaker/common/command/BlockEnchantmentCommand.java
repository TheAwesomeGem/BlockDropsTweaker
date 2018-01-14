package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.BlockEnchantAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockEnchantClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockEnchantRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class BlockEnchantmentCommand extends BlockDropsTreeBaseCommand
{
    public BlockEnchantmentCommand()
    {
        addSubcommand(new BlockEnchantAddCommand());
        addSubcommand(new BlockEnchantRemoveCommand());
        addSubcommand(new BlockEnchantClearCommand());
    }

    @Override
    public String getName()
    {
        return "enchant";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.enchant";
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

        player.sendMessage(ChatUtil.getNormalMessage("-----Enchants-----"));

        for(String enchant : blockDropData.enchantmentList)
        {
            player.sendMessage(ChatUtil.getNormalMessage(enchant));
        }

        player.sendMessage(ChatUtil.getNormalMessage("----------------"));
    }
}
