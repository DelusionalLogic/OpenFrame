package dk.slashwin.openframe;

import dk.slashwin.openframe.lib.BlockIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: DelusionalLogic
 * Date: 20-04-13
 * Time: 23:26
 */
public class TileMovingBlock extends TileEntity
{
    public NBTTagCompound savedNBTData;

    private int storedBlockID;
    private int storedMetadata;

    private ForgeDirection moveDirection;

    private float interpolation;
    private float lastInterpolation;
    private ArrayList pushedObjects;

    public void setNBTData(NBTTagCompound nbtData)
    {
        this.savedNBTData = nbtData;
        savedNBTData.setInteger("x", this.xCoord);
        savedNBTData.setInteger("y", this.yCoord);
        savedNBTData.setInteger("z", this.zCoord);
    }

    public void setBlockData(int blockID, int metaData)
    {
        this.storedBlockID = blockID;
        this.storedMetadata = metaData;

	    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metaData, 3);
	    worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    public void setMoveDirection(ForgeDirection direction)
    {
        moveDirection = direction;
    }

    public int getStoredBlockID()
    {
        return this.storedBlockID;
    }

    public int getBlockMetadata()
    {
        return this.storedMetadata;
    }

    public float getProgress(float par1)
    {
        par1 = (par1 > 1F ? 1F : par1);
        return lastInterpolation + (interpolation - lastInterpolation) * par1;
    }

    public float getOffsetX(float par1)
    {
        int offset = 1;
        if(moveDirection != null)
            offset = moveDirection.getOpposite().offsetX;
        return (1F - this.getProgress(par1)) * offset;
    }

    public float getOffsetY(float par1)
    {
        int offset = 0;
        if(moveDirection != null)
            offset = moveDirection.getOpposite().offsetY;
        return (1F - this.getProgress(par1)) * offset;
    }

    public float getOffsetZ(float par1)
    {
        int offset = 0;
        if(moveDirection != null)
            offset = moveDirection.getOpposite().offsetZ;
        return (1F - this.getProgress(par1)) * offset;
    }

    public AxisAlignedBB getBoundingBox(float progress)
    {
        AxisAlignedBB bb;
        try{
            bb = Block.blocksList[storedBlockID].getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord);

        if(moveDirection.offsetX < 0)
            bb.minX -= moveDirection.offsetX * progress;
        else
            bb.maxX -= moveDirection.offsetX * progress;

        if(moveDirection.offsetY < 0)
            bb.minY -= moveDirection.offsetY * progress;
        else
            bb.maxY -= moveDirection.offsetY * progress;

        if(moveDirection.offsetZ < 0)
            bb.minZ -= moveDirection.offsetZ * progress;
        else
            bb.maxZ -= moveDirection.offsetZ * progress;

        return bb;
        }catch(Exception e){
            //This is a really, really, horrible way to stop the crashing
            //Caused by ArrayOutOfIndex
            return null;
        }
    }

    public void updateEntity()
    {
        lastInterpolation = interpolation;
        if (interpolation < 1f)
        {
            interpolation += 0.1f;
            pushEntities(interpolation - lastInterpolation + 0.0625f);
        }
        else
        {
            worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
            this.invalidate();
            if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == BlockIDs.movingID)
            {
                worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, getStoredBlockID(), this.storedMetadata, 3);
                this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID);
                TileEntity tileEntity = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
                if(savedNBTData != null && tileEntity != null)
                {
                    tileEntity.readFromNBT(savedNBTData);
                }
            }
        }
    }

    public void pushEntities(float amount)
    {
        float progress = 1f - interpolation;

        AxisAlignedBB bb = getBoundingBox(progress);

        if(bb != null)
        {
            List entityList = worldObj.getEntitiesWithinAABBExcludingEntity(null, bb);
            for(Object e : entityList)
            {
                Entity entity = (Entity)e;
                entity.moveEntity(amount * (float)moveDirection.offsetX, amount * moveDirection.offsetY, amount * (float)moveDirection.offsetZ);
            }
        }
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.storedBlockID = par1NBTTagCompound.getInteger("blockId");
        this.storedMetadata = par1NBTTagCompound.getInteger("blockData");
        if(par1NBTTagCompound.getBoolean("hasNBT"))
            this.savedNBTData = par1NBTTagCompound.getCompoundTag("tileData");
        this.moveDirection = ForgeDirection.getOrientation(par1NBTTagCompound.getInteger("moveDirection"));
        this.lastInterpolation = this.interpolation = par1NBTTagCompound.getFloat("interpolation");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("blockId", this.storedBlockID);
        par1NBTTagCompound.setInteger("blockData", this.storedMetadata);
        par1NBTTagCompound.setBoolean("hasNBT", savedNBTData != null);
        if(savedNBTData != null)
            par1NBTTagCompound.setCompoundTag("tileData", this.savedNBTData);
        par1NBTTagCompound.setInteger("moveDirection", moveDirection.ordinal());
        par1NBTTagCompound.setFloat("interpolation", this.interpolation);
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

}
