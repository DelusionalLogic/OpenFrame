package dk.slashwin.openframe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.slashwin.openframe.lib.BlockIDs;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * User: DelusionalLogic
 * Date: 02-05-13
 * Time: 21:38
 */
public class TileMotorRenderer extends TileEntitySpecialRenderer
{
    RenderBlocks blockRenderer;

    @SideOnly(Side.CLIENT)
    public void render(TileMotor tileMotor, double x, double y, double z, float f)
    {
        int topSide = tileMotor.getTopSide();

        Tessellator tessellator = Tessellator.instance;
        this.bindTextureByName("/terrain.png");
        RenderHelper.disableStandardItemLighting();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GL11.glShadeModel(GL11.GL_SMOOTH);
        }
        else
        {
            GL11.glShadeModel(GL11.GL_FLAT);
        }

        tessellator.startDrawingQuads();
        tessellator.setTranslation((double) ((float) x - (float) tileMotor.xCoord), (double) ((float) y - (float) tileMotor.yCoord), (double) ((float) z - (float) tileMotor.zCoord));
        tessellator.setColorOpaque(1, 1, 1);

        blockRenderer.setRenderBoundsFromBlock(Block.blocksList[BlockIDs.motorID]);
        switch (topSide)
        {
            case 0:
                blockRenderer.uvRotateEast = 3;
                blockRenderer.uvRotateWest = 3;
                blockRenderer.uvRotateSouth = 3;
                blockRenderer.uvRotateNorth = 3;
                blockRenderer.uvRotateBottom = tileMotor.getOrientation();
            case 1:
                blockRenderer.uvRotateTop = tileMotor.getOrientation();
                break;
            default:
                break;
            case 2:
                blockRenderer.uvRotateSouth = 1;
                blockRenderer.uvRotateNorth = 2;
                blockRenderer.uvRotateEast = tileMotor.getOrientation();
                break;
            case 3:
                blockRenderer.uvRotateSouth = 2;
                blockRenderer.uvRotateNorth = 1;
                blockRenderer.uvRotateTop = 3;
                blockRenderer.uvRotateBottom = 3;
                blockRenderer.uvRotateWest = tileMotor.getOrientation();
                break;
            case 4:
                blockRenderer.uvRotateEast = 1;
                blockRenderer.uvRotateWest = 2;
                blockRenderer.uvRotateTop = 2;
                blockRenderer.uvRotateBottom = 1;
                blockRenderer.uvRotateNorth = tileMotor.getOrientation();
                break;
            case 5:
                blockRenderer.uvRotateEast = 2;
                blockRenderer.uvRotateWest = 1;
                blockRenderer.uvRotateTop = 1;
                blockRenderer.uvRotateBottom = 2;
                blockRenderer.uvRotateSouth = tileMotor.getOrientation();
        }

        blockRenderer.renderStandardBlock(Block.blocksList[BlockIDs.motorID], tileMotor.xCoord, tileMotor.yCoord, tileMotor.zCoord);
        blockRenderer.uvRotateEast = 0;
        blockRenderer.uvRotateWest = 0;
        blockRenderer.uvRotateSouth = 0;
        blockRenderer.uvRotateNorth = 0;
        blockRenderer.uvRotateTop = 0;
        blockRenderer.uvRotateBottom = 0;

        tessellator.setTranslation(0.0D, 0.0D, 0.0D);
        tessellator.draw();
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
    {
        render((TileMotor)tileentity, x, y, z, f);
    }

    public void onWorldChange(World par1World)
    {
        this.blockRenderer = new RenderBlocks(par1World);
    }
}
