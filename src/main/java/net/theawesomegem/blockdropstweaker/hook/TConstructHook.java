package net.theawesomegem.blockdropstweaker.hook;

import net.minecraft.item.ItemStack;
import net.theawesomegem.blockdropstweaker.Primary;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.Collection;

/**
 * Created by TheAwesomeGem on 1/11/2018.
 */
public class TConstructHook
{
    public static boolean isTrait(ItemStack toolItem, DropData dropData)
    {
        if(!Primary.Instance.isTConstructLoaded())
            return true;

        if(dropData.traitList.isEmpty() && dropData.traitBlacklist)
            return true;

        if(toolItem.isEmpty())
            return dropData.traitBlacklist;

        if(!toolItem.hasTagCompound())
            return dropData.traitBlacklist;

        //TODO: Maybe add support for 'All' or 'Any'

        boolean traitExist = false;

        for(String trait : dropData.traitList)
        {
            if (TinkerUtil.hasTrait(toolItem.getTagCompound(), trait))
            {
                traitExist = true;

                break;
            }
        }

        if(dropData.traitBlacklist && traitExist)
            return false;

        if((!dropData.traitBlacklist) && (!traitExist))
            return false;

        return true;
    }

    public static boolean isTrait(ItemStack toolItem, BlockDropData blockDropData)
    {
        if(!Primary.Instance.isTConstructLoaded())
            return true;

        if(blockDropData.traitList.isEmpty() && blockDropData.traitBlacklist)
            return true;

        if(toolItem.isEmpty())
            return blockDropData.traitBlacklist;

        if(!toolItem.hasTagCompound())
            return blockDropData.traitBlacklist;

        //TODO: Maybe add support for 'All' or 'Any'

        boolean traitExist = false;

        for(String trait : blockDropData.traitList)
        {
            if (TinkerUtil.hasTrait(toolItem.getTagCompound(), trait))
            {
                traitExist = true;

                break;
            }
        }

        if(blockDropData.traitBlacklist && traitExist)
            return false;

        if((!blockDropData.traitBlacklist) && (!traitExist))
            return false;

        return true;
    }

    public static boolean isModifier(ItemStack toolItem, DropData dropData)
    {
        if(!Primary.Instance.isTConstructLoaded())
            return true;

        if(dropData.modifierList.isEmpty() && dropData.modifierBlacklist)
            return true;

        if(toolItem.isEmpty())
            return dropData.modifierBlacklist;

        if(!toolItem.hasTagCompound())
            return dropData.modifierBlacklist;

        //TODO: Maybe add support for 'All' or 'Any'

        boolean modifierExist = false;

        for(String modifier : dropData.modifierList)
        {
            if (TinkerUtil.hasModifier(toolItem.getTagCompound(), modifier))
            {
                modifierExist = true;

                break;
            }
        }

        if(dropData.modifierBlacklist && modifierExist)
            return false;

        if((!dropData.modifierBlacklist) && (!modifierExist))
            return false;

        return true;
    }

    public static boolean isModifier(ItemStack toolItem, BlockDropData blockDropData)
    {
        if(!Primary.Instance.isTConstructLoaded())
            return true;

        if(blockDropData.modifierList.isEmpty() && blockDropData.modifierBlacklist)
            return true;

        if(toolItem.isEmpty())
            return blockDropData.modifierBlacklist;

        if(!toolItem.hasTagCompound())
            return blockDropData.modifierBlacklist;

        //TODO: Maybe add support for 'All' or 'Any'

        boolean modifierExist = false;

        for(String modifier : blockDropData.modifierList)
        {
            if (TinkerUtil.hasModifier(toolItem.getTagCompound(), modifier))
            {
                modifierExist = true;

                break;
            }
        }

        if(blockDropData.modifierBlacklist && modifierExist)
            return false;

        if((!blockDropData.modifierBlacklist) && (!modifierExist))
            return false;

        return true;
    }

    public static boolean hasModifier(String modifier)
    {
        return TinkerRegistry.getModifier(modifier) != null;
    }

    public static Collection<IModifier> getAllModifiers()
    {
        return TinkerRegistry.getAllModifiers();
    }

    public static boolean hasTrait(String trait)
    {
        return TinkerRegistry.getTrait(trait) != null;
    }
}
