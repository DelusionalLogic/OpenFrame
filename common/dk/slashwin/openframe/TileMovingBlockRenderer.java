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
 * Date: 20-04-13
 * Time: 23:59
 */
public class TileMovingBlockRenderer extends TileEntitySpecialRenderer
{

    RenderBlocks blockRenderer;

    public TileMovingBlockRenderer()
    {
    }

    @SideOnly(Side.CLIENT)
    public void render(TileMovingBlock tileMovingBlock, double x, double y, double z, float f)
    {
        if (tileMovingBlock.getStoredBlockID() == 0)
            return;

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
        tessellator.setTranslation((double)((float)x - (float)tileMovingBlock.xCoord + tileMovingBlock.getOffsetX(f)), (double)((float)y - (float)tileMovingBlock.yCoord + tileMovingBlock.getOffsetY(f)), (double)((float)z - (float)tileMovingBlock.zCoord + tileMovingBlock.getOffsetZ(f)));
        tessellator.setColorOpaque(1, 1, 1);

        try{
            this.blockRenderer.renderAllFaces = true;
            if (!this.blockRenderer.renderBlockByRenderType(Block.blocksList[tileMovingBlock.getStoredBlockID()], tileMovingBlock.xCoord, tileMovingBlock.yCoord, tileMovingBlock.zCoord))
                this.blockRenderer.renderBlockAllFaces(Block.blocksList[BlockIDs.defaultMovingID], tileMovingBlock.xCoord, tileMovingBlock.yCoord, tileMovingBlock.zCoord);
            this.blockRenderer.renderAllFaces = false;
        }catch(ArrayIndexOutOfBoundsException e){
            this.blockRenderer.renderBlockAllFaces(Block.blocksList[BlockIDs.defaultMovingID], tileMovingBlock.xCoord, tileMovingBlock.yCoord, tileMovingBlock.zCoord);
        }

        tessellator.setTranslation(0.0D, 0.0D, 0.0D);
        tessellator.draw();
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
    {
        render((TileMovingBlock)tileentity, x, y, z, f);
    }

    public void onWorldChange(World par1World)
    {
        this.blockRenderer = new RenderBlocks(par1World);
    }
}
