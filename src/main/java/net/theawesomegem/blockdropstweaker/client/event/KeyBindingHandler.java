package net.theawesomegem.blockdropstweaker.client.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.theawesomegem.blockdropstweaker.client.keybind.KeybindManager;
import net.theawesomegem.blockdropstweaker.networking.PrimaryPacketHandler;
import net.theawesomegem.blockdropstweaker.networking.packet.PacketKeybindS;
import net.theawesomegem.blockdropstweaker.networking.packet.PacketKeybindS.Keybind;


/**
 * Created by TheAwesomeGem on 12/27/2017.
 */
public class KeyBindingHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onInput(KeyInputEvent e)
    {
        if(KeybindManager.isSaveBlockDropsPressed())
            PrimaryPacketHandler.INSTANCE.sendToServer(new PacketKeybindS(Keybind.SAVE_BLOCK_DROPS));
        else if(KeybindManager.isReloadBlockDropsPressed())
            PrimaryPacketHandler.INSTANCE.sendToServer(new PacketKeybindS(Keybind.RELOAD_BLOCK_DROPS));
    }
}
