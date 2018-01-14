package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.BlockBiomeAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockBiomeClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockBiomeRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/9/2018.
 */
public class BlockBiomeCommand extends BlockDropsTreeBaseCommand
{
    public BlockBiomeCommand()
    {
        addSubcommand(new BlockBiomeAddCommand());
        addSubcommand(new BlockBiomeRemoveCommand());
        addSubcommand(new BlockBiomeClearCommand());
    }

    @Override
    public String getName()
    {
        return "biome";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.biome";
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

        player.sendMessage(ChatUtil.getNormalMessage("-----Biomes-----"));

        for(String biomeInfo : blockDropData.biomes)
        {
            player.sendMessage(ChatUtil.getNormalMessage(biomeInfo));
        }

        player.sendMessage(ChatUtil.getNormalMessage("----------------"));
    }
}
