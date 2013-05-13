package dk.slashwin.openframe;

import cpw.mods.fml.common.Mod;
import dk.slashwin.openframe.common.CoordTuple;
import dk.slashwin.openframe.lib.BlockIDs;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * User: DelusionalLogic
 * Date: 19-04-13
 * Time: 21:02
 */
public class TileFrame extends TileEntity
{
    public LinkedHashSet<CoordTuple> getNeighbors(LinkedHashSet<CoordTuple> checked)
    {
        for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
        {
            int blockX = xCoord + direction.offsetX;
            int blockY = yCoord + direction.offsetY;
            int blockZ = zCoord + direction.offsetZ;

            int blockID = worldObj.getBlockId(blockX, blockY, blockZ);

            if(!checked.contains(new CoordTuple(blockX, blockY, blockZ)) && blockID != 0)
            {
                TileEntity tileEntity = worldObj.getBlockTileEntity(blockX, blockY, blockZ);
                if(tileEntity != null && tileEntity instanceof ISpecialFrameBlock)
                {
                    if(((ISpecialFrameBlock)tileEntity).stickOnSide(direction.getOpposite().ordinal()))
                        checked.add(new CoordTuple(blockX, blockY, blockZ));
                }
                else
                {
                    checked.add(new CoordTuple(blockX, blockY, blockZ));
                }

                if(blockID == BlockIDs.frameID)
                {
                    TileEntity te = worldObj.getBlockTileEntity(blockX, blockY, blockZ);
                    if(te instanceof TileFrame)
                    {
                        checked = ((TileFrame) te).getNeighbors(checked);
                    }
                }
            }
        }
        return checked;
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
    }
}
