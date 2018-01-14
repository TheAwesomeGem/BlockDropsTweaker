package net.theawesomegem.blockdropstweaker.common.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.theawesomegem.blockdropstweaker.ModInfo;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class CapabilityHandler
{
    public static final ResourceLocation PLAYERDATA_CAP_RESOURCE = new ResourceLocation(ModInfo.MOD_ID, "playerdata");

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> e)
    {
        if(!(e.getObject() instanceof EntityPlayer))
            return;

        e.addCapability(PLAYERDATA_CAP_RESOURCE, new PlayerDataCapabilityProvider());
    }
}
