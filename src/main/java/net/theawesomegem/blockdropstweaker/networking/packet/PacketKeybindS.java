package net.theawesomegem.blockdropstweaker.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.theawesomegem.blockdropstweaker.Primary;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/5/2018.
 */
public class PacketKeybindS implements IMessage
{
    public enum Keybind
    {
        SAVE_BLOCK_DROPS,
        RELOAD_BLOCK_DROPS,
        NONE
    }

    private Keybind bind;

    public Keybind getKeyBind()
    {
        return bind;
    }

    public PacketKeybindS()
    {
        this.bind = Keybind.NONE;
    }

    public PacketKeybindS(Keybind keybind)
    {
        this.bind = keybind;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.bind = Keybind.values()[buf.readShort()];
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeShort(bind.ordinal());
    }

    public static class KeybindMessageHandler implements IMessageHandler<PacketKeybindS, IMessage>
    {
        @Override
        public IMessage onMessage(PacketKeybindS message, MessageContext ctx)
        {
            final EntityPlayer player = Primary.proxy.getPlayer(ctx);
            final Keybind bind = message.getKeyBind();

            if(player == null)
                return null;

            IThreadListener thread = Primary.proxy.getListener(ctx);

            thread.addScheduledTask(() -> // Game Thread
            {
                if(bind.equals(Keybind.NONE))
                    return;

                if(!Primary.proxy.hasPermission(player))
                {
                    player.sendMessage(new TextComponentString("You don't have permission to save block drops data."));

                    return;
                }

                if(bind.equals(Keybind.SAVE_BLOCK_DROPS))
                {
                    ConfigurationHandler.saveConfig();
                    player.sendMessage(ChatUtil.getNormalMessage("Saved current data to JSON."));
                }
                else if(bind.equals(Keybind.RELOAD_BLOCK_DROPS))
                {
                    ConfigurationHandler.loadConfig();

                    player.sendMessage(ChatUtil.getNormalMessage("Reloaded from the JSON."));
                    player.sendMessage(ChatUtil.getNormalMessage("Any unsaved changes will be lost."));
                }
            });

            return null;
        }
    }
}
