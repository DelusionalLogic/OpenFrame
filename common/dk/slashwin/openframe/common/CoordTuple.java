package dk.slashwin.openframe.common;

/**
 * User: DelusionalLogic
 * Date: 25-04-13
 * Time: 20:21
 * Note: Stolen from mDiyo
 */
public class CoordTuple
{
    public int x;
    public int y;
    public int z;

    public CoordTuple(int posX, int posY, int posZ)
    {
        x = posX;
        y = posY;
        z = posZ;
    }

    @Override
    public boolean equals(Object coord)
    {
        if(coord instanceof CoordTuple)
        {
            CoordTuple toCompare = (CoordTuple)coord;
            if(this.x == toCompare.x && this.y == toCompare.y && this.z == toCompare.z)
                return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.x + this.y + this.z;
    }

    public String toString()
    {
        return "X: "+x+", Y: "+y+", Z: "+z;
    }
}