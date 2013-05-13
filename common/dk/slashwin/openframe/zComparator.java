package dk.slashwin.openframe;

import dk.slashwin.openframe.common.CoordTuple;

import java.util.Comparator;

public class zComparator implements Comparator<CoordTuple>
{
    boolean reversed;
    public zComparator(boolean reversed)
    {
        this.reversed = reversed;
    }

    @Override
    public int compare(CoordTuple o1, CoordTuple o2)
    {
        if(reversed)
            return o1.z - o2.z;
        return o2.z - o1.z;
    }
}
