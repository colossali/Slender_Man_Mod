package colossali.Slender.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import java.util.EnumSet;
import java.util.Map;

import colossali.Slender.common.EntitySlenderMan;
import colossali.Slender.common.mod_slenderman;
import colossali.Slender.common.CommonProxy;
import colossali.Slender.hud.ClientStaticEffect;
import colossali.Slender.mobspawner.TileEntitySlenderSpawner;
import colossali.Slender.model.ModelSlenderman;
import colossali.Slender.render.RenderSlenderSpawnerItem;
import colossali.Slender.render.RenderSlenderSpawnerTileEntity;
import colossali.Slender.render.RenderSlenderman;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	
    public void registerRenderThings()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntitySlenderMan.class, new RenderSlenderman(new ModelSlenderman(), 0.5F));
    }

    public void load()
    {
        TickRegistry.registerTickHandler(new ClientTickHandler(EnumSet.of(TickType.CLIENT)), Side.CLIENT);
        TickRegistry.registerTickHandler(new ClientStaticEffect(), Side.CLIENT);
        
        LanguageRegistry.addName(mod_slenderman.ItemChildSoul, "Child Soul");
        LanguageRegistry.addName(mod_slenderman.ItemSlenderMask, "Slender Mask");
        LanguageRegistry.addName(mod_slenderman.ItemSlenderPants, "Slender Trousers");
        LanguageRegistry.addName(mod_slenderman.ItemSlenderShoes, "Slender Shoes");
        LanguageRegistry.addName(mod_slenderman.ItemSlenderSuit, "Slender Suit");
        LanguageRegistry.addName(mod_slenderman.ItemSlenderSword, "Slender Sword");       
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySlenderSpawner.class, new RenderSlenderSpawnerTileEntity());
        MinecraftForgeClient.registerItemRenderer(mod_slenderman.BlockSlenderSpawner.blockID, new RenderSlenderSpawnerItem());
        ModLoader.addAchievementDesc(mod_slenderman.killedSlenderMan, "Slender Slayer", "/u00a7kasfgfaw");
    }
    

    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
