package net.theawesomegem.blockdropstweaker.common.capability.player;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class PlayerData implements IPlayerData
{
    private String selectedBlock = null;
    private String selectedDrop = null;

    @Override
    public String getSelectedBlock()
    {
        return this.selectedBlock;
    }

    @Override
    public void setSelectedBlock(String value)
    {
        if(value != null && value.equals(getSelectedBlock()))
            return;

        setSelectedDrop(null);

        this.selectedBlock = value;
    }

    @Override
    public String getSelectedDrop()
    {
        return this.selectedDrop;
    }

    @Override
    public void setSelectedDrop(String value)
    {
        if(value != null && value.equals(getSelectedDrop()))
            return;

        this.selectedDrop = value;
    }
}
