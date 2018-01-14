package net.theawesomegem.blockdropstweaker.hook;

import net.darkhax.gamestages.capabilities.PlayerDataHandler;
import net.darkhax.gamestages.capabilities.PlayerDataHandler.IStageData;
import net.minecraft.entity.player.EntityPlayer;
import net.theawesomegem.blockdropstweaker.Primary;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;

/**
 * Created by TheAwesomeGem on 1/11/2018.
 */
public class GameStageHook
{
    public static boolean isGameStage(EntityPlayer player, DropData dropData)
    {
        if(!Primary.Instance.isGameStagesLoaded())
            return true;

        if(dropData.gameStageList.isEmpty() && dropData.gamestageBlacklist)
            return true;

        if(player == null)
            return false;

        IStageData stageData = PlayerDataHandler.getStageData(player);

        if(stageData == null)
            return false;

        if(dropData.gamestageBlacklist)
        {
            if(dropData.gamestageAll && stageData.hasUnlockedAll(dropData.gameStageList))
                return false;

            if((!dropData.gamestageAll) && stageData.hasUnlockedAnyOf(dropData.gameStageList))
                return false;
        }
        else
        {
            if(dropData.gamestageAll && !stageData.hasUnlockedAll(dropData.gameStageList))
                return false;

            if((!dropData.gamestageAll) && !stageData.hasUnlockedAnyOf(dropData.gameStageList))
                return false;
        }

        return true;
    }

    public static boolean isGameStage(EntityPlayer player, BlockDropData blockDropData)
    {
        if(!Primary.Instance.isGameStagesLoaded())
            return true;

        if(blockDropData.gameStageList.isEmpty() && blockDropData.gamestageBlacklist)
            return true;

        if(player == null)
            return false;

        IStageData stageData = PlayerDataHandler.getStageData(player);

        if(stageData == null)
            return false;

        if(blockDropData.gamestageBlacklist)
        {
            if(blockDropData.gamestageAll && stageData.hasUnlockedAll(blockDropData.gameStageList))
                return false;

            if((!blockDropData.gamestageAll) && stageData.hasUnlockedAnyOf(blockDropData.gameStageList))
                return false;
        }
        else
        {
            if(blockDropData.gamestageAll && !stageData.hasUnlockedAll(blockDropData.gameStageList))
                return false;

            if((!blockDropData.gamestageAll) && !stageData.hasUnlockedAnyOf(blockDropData.gameStageList))
                return false;
        }

        return true;
    }
}
