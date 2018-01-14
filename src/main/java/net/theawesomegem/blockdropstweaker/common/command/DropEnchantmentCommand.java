package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.DropEnchantAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropEnchantClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropEnchantRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class DropEnchantmentCommand extends BlockDropsTreeBaseCommand
{
    public DropEnchantmentCommand()
    {
        addSubcommand(new DropEnchantAddCommand());
        addSubcommand(new DropEnchantRemoveCommand());
        addSubcommand(new DropEnchantClearCommand());
    }

    @Override
    public String getName()
    {
        return "enchant";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.enchant";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
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

        player.sendMessage(ChatUtil.getNormalMessage("-----Enchants-----"));

        for(String enchant : dropData.enchantmentList)
        {
            player.sendMessage(ChatUtil.getNormalMessage(enchant));
        }

        player.sendMessage(ChatUtil.getNormalMessage("----------------"));
    }
}
