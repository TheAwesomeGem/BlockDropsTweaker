package net.theawesomegem.blockdropstweaker.networking;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.theawesomegem.blockdropstweaker.ModInfo;
import net.theawesomegem.blockdropstweaker.networking.packet.PacketKeybindS;
import net.theawesomegem.blockdropstweaker.networking.packet.PacketTextCopyC;

/**
 * Created by TheAwesomeGem on 1/5/2018.
 */
public class PrimaryPacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MOD_ID);

    public static void registerPackets()
    {
        INSTANCE.registerMessage(PacketKeybindS.KeybindMessageHandler.class, PacketKeybindS.class, 1, Side.SERVER);

        INSTANCE.registerMessage(PacketTextCopyC.class, PacketTextCopyC.class, 3, Side.CLIENT);
    }
}
