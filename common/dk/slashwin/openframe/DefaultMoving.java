package dk.slashwin.openframe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

/**
 * User: DelusionalLogic
 * Date: 24-04-13
 * Time: 23:25
 */
public class DefaultMoving extends Block
{
    public DefaultMoving(int id)
    {
        super(id, Material.wood);

        setUnlocalizedName("Default Block");
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon("openframe:movingFall");
    }
}
