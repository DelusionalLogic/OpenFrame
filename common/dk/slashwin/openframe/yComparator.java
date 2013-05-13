package dk.slashwin.openframe;

import dk.slashwin.openframe.common.CoordTuple;

import java.util.Comparator;

public class yComparator implements Comparator<CoordTuple>
{
    boolean reversed;
    public yComparator(boolean reversed)
    {
        this.reversed = reversed;
    }

    @Override
    public int compare(CoordTuple o1, CoordTuple o2)
    {
        if(reversed)
            return o1.y - o2.y;
        return o2.y - o1.y;
    }
}

