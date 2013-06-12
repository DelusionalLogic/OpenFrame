package dk.slashwin.openframe.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import dk.slashwin.openframe.*;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

/**
 * User: DelusionalLogic
 * Date: 19-04-13
 * Time: 21:51
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerTileEntitySpecialRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileFrame.class, new RenderFrame());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMovingBlock.class, new TileMovingBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMotor.class, new TileMotorRenderer());
    }

    public void registerTileEntityRenderer(Class tileClass, TileEntitySpecialRenderer renderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileClass, renderer);
    }
}
