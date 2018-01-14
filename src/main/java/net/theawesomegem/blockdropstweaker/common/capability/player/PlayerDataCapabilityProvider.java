package net.theawesomegem.blockdropstweaker.common.capability.player;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class PlayerDataCapabilityProvider implements ICapabilityProvider
{
    @CapabilityInject(IPlayerData.class)
    public static final Capability<IPlayerData> PLAYERDATA_CAP = null;

    private IPlayerData instance = PLAYERDATA_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == PLAYERDATA_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == PLAYERDATA_CAP ? PLAYERDATA_CAP.cast(this.instance) : null;
    }
}
