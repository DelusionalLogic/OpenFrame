package dk.slashwin.openframe;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * User: DelusionalLogic
 * Date: 19-04-13
 * Time: 21:53
 */
public class RenderFrame extends TileEntitySpecialRenderer
{

    public RenderFrame()
    {
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
    {
        //TODO: Render frame based on covers
    }
}
