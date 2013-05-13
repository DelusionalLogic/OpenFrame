package dk.slashwin.openframe;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dk.slashwin.openframe.lib.BlockIDs;
import dk.slashwin.openframe.lib.Reference;
import dk.slashwin.openframe.proxy.CommonProxy;

/**
 * User: DelusionalLogic
 * Date: 19-04-13
 * Time: 19:12
 */

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class OpenFrame
{
    @SidedProxy(clientSide = "dk.slashwin.openframe.proxy.ClientProxy", serverSide = "dk.slashwin.openframe.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent event)
    {}

    @Mod.Init
    public void init(FMLInitializationEvent event)
    {
        BlockFrame blockFrame = new BlockFrame(BlockIDs.frameID);
        GameRegistry.registerBlock(blockFrame, "blockFrame");
        GameRegistry.registerTileEntity(TileFrame.class, "TileFrame");
        LanguageRegistry.addName(blockFrame, "Frame");

        BlockMotor blockMotor = new BlockMotor(BlockIDs.motorID);
        GameRegistry.registerBlock(blockMotor, "BlockMotor");
        GameRegistry.registerTileEntity(TileMotor.class, "TileMotor");
        LanguageRegistry.addName(blockMotor, "Frame Motor");

        BlockMovingBlock blockMovingBlock = new BlockMovingBlock(BlockIDs.movingID);
        GameRegistry.registerBlock(blockMovingBlock, "BlockMovingBlock");
        GameRegistry.registerTileEntity(TileMovingBlock.class, "TileMovingBlock");

        DefaultMoving defaultMoving = new DefaultMoving(BlockIDs.defaultMovingID);
        GameRegistry.registerBlock(defaultMoving, "DefaultMovingBlock");
    }

    @Mod.PostInit
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.registerTileEntitySpecialRenderers();
    }
}
