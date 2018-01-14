package net.theawesomegem.blockdropstweaker.common.capability.player;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public interface IPlayerData
{
    String getSelectedBlock();
    void setSelectedBlock(String value);

    String getSelectedDrop();
    void setSelectedDrop(String value);
}
