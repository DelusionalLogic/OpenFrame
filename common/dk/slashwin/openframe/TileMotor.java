package dk.slashwin.openframe;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: DelusionalLogic
 * Date: 20-04-13
 * Time: 19:00
 */
public class TileMotor extends TileEntity implements ISpecialFrameBlock
{
    private int topSide;
    private int orientation;

    public void setTopSide(int topSide)
    {
        this.topSide = topSide;
    }

    public void setOrientation(int orientation)
    {
        this.orientation = orientation % 4;
    }

    public int getTopSide()
    {
        return this.topSide;
    }

    public int getOrientation()
    {
        return this.orientation;
    }

    public ForgeDirection getPushDirection()
    {
        switch(ForgeDirection.getOrientation(this.getTopSide()))
        {
            case DOWN:
                switch(orientation)
                {
                    case 0:
                        return ForgeDirection.SOUTH;
                    case 1:
                        return ForgeDirection.EAST;
                    case 2:
                        return ForgeDirection.WEST;
                    case 3:
                        return ForgeDirection.NORTH;
                }
                break;
            case UP:
                switch(orientation)
                {
                    case 0:
                        return ForgeDirection.SOUTH;
                    case 1:
                        return ForgeDirection.WEST;
                    case 2:
                        return ForgeDirection.EAST;
                    case 3:
                        return ForgeDirection.NORTH;
                }
                break;
            case NORTH:
                switch(orientation)
                {
                    case 0:
                        return ForgeDirection.DOWN;
                    case 1:
                        return ForgeDirection.EAST;
                    case 2:
                        return ForgeDirection.WEST;
                    case 3:
                        return ForgeDirection.UP;
                }
                break;
            case SOUTH:
                switch(orientation)
                {
                    case 0:
                        return ForgeDirection.DOWN;
                    case 1:
                        return ForgeDirection.WEST;
                    case 2:
                        return ForgeDirection.EAST;
                    case 3:
                        return ForgeDirection.UP;
                }
                break;
            case WEST:
                switch(orientation)
                {
                    case 0:
                        return ForgeDirection.DOWN;
                    case 1:
                        return ForgeDirection.NORTH;
                    case 2:
                        return ForgeDirection.SOUTH;
                    case 3:
                        return ForgeDirection.UP;
                }
                break;
            case EAST:
                switch(orientation)
                {
                    case 0:
                        return ForgeDirection.DOWN;
                    case 1:
                        return ForgeDirection.SOUTH;
                    case 2:
                        return ForgeDirection.NORTH;
                    case 3:
                        return ForgeDirection.UP;
                }
                break;
        }
        return ForgeDirection.UNKNOWN;
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        topSide = par1NBTTagCompound.getInteger("topSide");
        orientation = par1NBTTagCompound.getInteger("orientation");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("topSide", topSide);
        par1NBTTagCompound.setInteger("orientation", orientation);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt){
        readFromNBT(pkt.customParam1);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 3, nbttagcompound);
    }

    @Override
    public boolean stickOnSide(int side)
    {
        if(side == topSide)
            return false;
        return true;
    }
}
