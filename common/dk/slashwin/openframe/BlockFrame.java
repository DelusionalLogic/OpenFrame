package dk.slashwin.openframe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
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

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        super.registerIcons(iconRegister);
    }

    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
        return Block.cobblestone.getBlockTexture(world, x ,y, z, side);
    }
}
