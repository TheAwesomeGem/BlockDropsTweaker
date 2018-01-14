package net.theawesomegem.blockdropstweaker.client.keybind;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

/**
 * Created by TheAwesomeGem on 12/27/2017.
 */
public class KeybindManager
{
    private static KeyBinding saveBlockDrops;
    private static KeyBinding reloadBlockDrops;

    public static void load()
    {
        saveBlockDrops = new KeyBinding("key.blockdropstweaker.saveblockdrops", Keyboard.KEY_G, "key.blockdropstweaker.category");
        reloadBlockDrops = new KeyBinding("key.blockdropstweaker.reloadblockdrops", Keyboard.KEY_H, "key.blockdropstweaker.category");

        ClientRegistry.registerKeyBinding(saveBlockDrops);
        ClientRegistry.registerKeyBinding(reloadBlockDrops);
    }

    public static boolean isSaveBlockDropsPressed()
    {
        return saveBlockDrops.isPressed();
    }

    public static boolean isReloadBlockDropsPressed()
    {
        return reloadBlockDrops.isPressed();
    }
}
