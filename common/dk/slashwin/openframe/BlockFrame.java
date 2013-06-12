package dk.slashwin.openframe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * User: DelusionalLogic
 * Date: 19-04-13
 * Time: 20:23
 */
public class BlockFrame extends BlockContainer
{
	Icon frameStick;

    public BlockFrame(int id)
    {
        super(id, Material.wood);

        setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileFrame();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float relX, float relY, float relZ)
    {
        if (world.isRemote)
            return true;

	    TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

	    if(tileEntity instanceof TileFrame)
	    {
			((TileFrame) tileEntity).setSide(side);
	    }

        return true;
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
	    frameStick = iconRegister.registerIcon("openframe:frameOn");
        blockIcon = iconRegister.registerIcon("openFrame:frameOff");
    }

    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

	    if(tileEntity instanceof TileFrame)
	    {
		    if(((TileFrame) tileEntity).getSide(side))
			    return frameStick;
	    }
	    return blockIcon;
    }
}
