package dk.slashwin.openframe;

import dk.slashwin.openframe.common.CoordTuple;

import java.util.Comparator;

/**
 * User: DelusionalLogic
 * Date: 25-04-13
 * Time: 23:20
 */
public class xComparator implements Comparator<CoordTuple>
{
    boolean reversed;
    public xComparator(boolean reversed)
    {
        this.reversed = reversed;
    }

    @Override
    public int compare(CoordTuple o1, CoordTuple o2)
    {
        if(reversed)
            return o1.x - o2.x;
        return o2.x - o1.x;
    }
}

