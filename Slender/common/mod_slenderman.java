package colossali.Slender.common;

import java.util.Random;
import java.util.Map;
import java.util.List;
import java.util.jar.*;
import java.util.*;
import java.util.logging.*;
import java.util.concurrent.*;
import java.awt.datatransfer.*;
import java.awt.geom.*;
import java.util.zip.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.src.*;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.sound.PlaySoundEffectEvent;
import net.minecraftforge.client.event.sound.PlaySoundEffectSourceEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import colossali.Slender.client.ClientProxy;
import colossali.Slender.mobspawner.BlockSlenderSpawner;
import colossali.Slender.mobspawner.TileEntitySlenderSpawner;
import paulscode.sound.SoundSystem;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;


@Mod(
        modid = "colossali_SlenderMan",
        name = "SlenderMan",
        version = "v3 [1.5.2]"
)

@NetworkMod(
        clientSideRequired = true,
        serverSideRequired = true
)

public class mod_slenderman 
{
    @SidedProxy(
            clientSide = "colossali.Slender.client.ClientProxy",
            serverSide = "colossali.Slender.common.CommonProxy"
    )
	    public static CommonProxy proxy;
    
    //Item IDs
    public static int ChildSoulID = 4150;
    public static int SlenderMaskID = 4151;
    public static int SlenderSuitID = 4152;
    public static int SlenderPantsID = 4153;
    public static int SlenderShoesID = 4154;
    public static int SlenderSwordID = 4155;
    public static int SlenderNoteID = 4156;
    public static int SlenderManID = 123;
    public static int MaskyID = 146;
    public static int SlenderSpawnerID = 3102;
    
    //Items
    public static Item ItemChildSoul;
    public static Item ItemSlenderMask;
    public static Item ItemSlenderSuit;
    public static Item ItemSlenderPants;
    public static Item ItemSlenderShoes;
    public static Item ItemSlenderSword;
    
    //Blocks
    public static Block BlockSlenderSpawner;
    
    //Achievements
    public static Achievement killedSlenderMan;
    
    public static boolean canSpawn = false;
    public static boolean spawned = true;
    

    //Configuration file
    
    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent var1)
    {
        proxy.preInit();
        
        if(FMLCommonHandler.instance().getSide().isClient())
        {
        MinecraftForge.EVENT_BUS.register(new SoundsSlenderman());//if your class is something other than Sounds change Sounds to your class
        }
        
        Configuration var2 = new Configuration(var1.getSuggestedConfigurationFile());

        try
        {
            var2.load();
            Property var3 = var2.get("ChildSoul", "item", 4150);
            ChildSoulID = var3.getInt(4150);
            var3 = var2.get("SlenderMask", "item", 4151);
            SlenderMaskID = var3.getInt(4151);
            var3 = var2.get("SlenderSuit", "item", 4152);
            SlenderSuitID = var3.getInt(4152);
            var3 = var2.get("SlenderPants", "item", 4153);
            SlenderPantsID = var3.getInt(4153);
            var3 = var2.get("SlenderShoes", "item", 4154);
            SlenderShoesID = var3.getInt(4154);
            var3 = var2.get("SlenderSword", "item", 4155);
            SlenderSwordID = var3.getInt(4155);
            var3 = var2.get("SlenderNote", "item", 4156);
            SlenderNoteID = var3.getInt(4156);
            var3 = var2.get("Slender Man", "general", 123);
            SlenderManID = var3.getInt(123);
            var3 = var2.get("Masky", "general", 146);
            MaskyID = var3.getInt(146);
            var3 = var2.get("SlenderSpawnerID", "block", 3102);
            SlenderSpawnerID = var3.getInt(3102);
            
        }
        catch (Exception var7)
        {
            FMLLog.log(Level.SEVERE, var7, "Slender Man Fucked Up", new Object[0]);
            FMLLog.severe(var7.getMessage(), new Object[0]);
        }
        finally
        {
            var2.save();
        }
    }
    
    @Init
    public void load(FMLInitializationEvent event)

    {	

    proxy.registerRenderThings();    
	ItemChildSoul = new ItemChildSoul(ChildSoulID, 1, false).setPotionEffect(Potion.blindness.id, 20, 1, 0.9F).setUnlocalizedName("Child Soul");
    ItemSlenderMask = (new ItemSlenderArmor(SlenderMaskID, EnumArmorMaterial.DIAMOND, 1, 0, "slendermask")).setUnlocalizedName("Slender Mask");
    ItemSlenderSuit = (new ItemSlenderArmor(SlenderSuitID, EnumArmorMaterial.DIAMOND, 1, 1, "slendersuit")).setUnlocalizedName("Slender Suit");
    ItemSlenderPants = (new ItemSlenderArmor(SlenderPantsID, EnumArmorMaterial.DIAMOND, 1, 2,"slenderpants")).setUnlocalizedName("Slender Pants");
    ItemSlenderShoes = (new ItemSlenderArmor(SlenderShoesID, EnumArmorMaterial.DIAMOND, 1, 3,"slendershoes")).setUnlocalizedName("Slender Shoes");
    ItemSlenderSword = (new ItemSlenderSword(SlenderSwordID, EnumToolMaterialSlender.SLENDER, "slendersword")).setUnlocalizedName("Slender Sword");
    BlockSlenderSpawner = new BlockSlenderSpawner(SlenderSpawnerID, 8).setHardness(3F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("blockTutorial").setCreativeTab(CreativeTabs.tabBlock);
    killedSlenderMan = new Achievement(1023, "/u00a7kasdfwea", 7, 3, mod_slenderman.ItemChildSoul, AchievementList.buildSword).registerAchievement().setSpecial();
        
    proxy.load();
        
        ModLoader.addRecipe(new ItemStack(ItemSlenderSword, 1), new Object[] {"090", "090", " 8 ", '0', Block.obsidian, '9', ItemChildSoul, '8', Item.diamond});

        ModLoader.addRecipe(new ItemStack(ItemSlenderMask, 1), new Object[] {"797", "969", "797", '7', Item.leather, '9', ItemChildSoul, '6', Item.ghastTear});

        ModLoader.addRecipe(new ItemStack(ItemSlenderSuit, 1), new Object[] {"595", "555", "434", '5', Item.diamond, '9', ItemChildSoul, '4', Block.cloth, '3', Item.redstone});

        ModLoader.addRecipe(new ItemStack(ItemSlenderPants, 1), new Object[] {"454", "5 5", "9 9", '5', Item.diamond, '9', ItemChildSoul, '4', Block.cloth});

        ModLoader.addRecipe(new ItemStack(ItemSlenderShoes, 1), new Object[] {"   ", "595", "7 7", '5', Item.diamond, '9', ItemChildSoul, '7', Item.leather});
       
        
        EntityRegistry.registerGlobalEntityID(EntitySlenderMan.class, "Slender Man", SlenderManID, 16260, 11020932);
        EntityRegistry.registerModEntity(EntitySlenderMan.class, "Slender Man", SlenderManID, this, 1000, 1, false);               
        LanguageRegistry.instance().addStringLocalization("entity.Slender Man.name", "en_US", "SlenderMan");
        
        EntityRegistry.registerGlobalEntityID(EntityMasky.class, "Masky", MaskyID, 1460, 91089032);
        EntityRegistry.registerModEntity(EntityMasky.class, "Masky", MaskyID, this, 1000, 1, false);               
        LanguageRegistry.instance().addStringLocalization("entity.Masky.name", "en_US", "Masky");
        
        GameRegistry.registerTileEntity(TileEntitySlenderSpawner.class, "Slender Spawner");   

        
    LanguageRegistry.addName(mod_slenderman.ItemChildSoul, "Child Soul");
    LanguageRegistry.addName(mod_slenderman.ItemSlenderMask, "Slender Mask");
    LanguageRegistry.addName(mod_slenderman.ItemSlenderPants, "Slender Trousers");
    LanguageRegistry.addName(mod_slenderman.ItemSlenderShoes, "Slender Shoes");
    LanguageRegistry.addName(mod_slenderman.ItemSlenderSuit, "Slender Suit");
    LanguageRegistry.addName(mod_slenderman.ItemSlenderSword, "Slender Sword");
    GameRegistry.registerBlock(BlockSlenderSpawner);
    GameRegistry.registerWorldGenerator(new ShrineWorldGen());
    GameRegistry.registerWorldGenerator(new ShrineWorldGen());
    
    ModLoader.addSpawn(EntityMasky.class, 1, 0, 1, EnumCreatureType.monster);

    }

    


	 @SideOnly(Side.CLIENT)
	public static void play(SoundManager var0, SoundSystem var1, String var2)
	{
	    SoundPoolEntry var3 = var0.soundPoolSounds.getRandomSoundFromSoundPool(var2);
	    var3 = SoundEvent.getResult(new PlaySoundEffectEvent(var0, var3, var2, 0.1F, 1.0F));
	
	    if (var3 != null)
	    {
	        var1.newSource(false, var2, var3.soundUrl, var3.soundName, false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
	        var1.setPitch(var2, 1.0F);
	        var1.setVolume(var2, 0.0F);
	        MinecraftForge.EVENT_BUS.post(new PlaySoundEffectSourceEvent(var0, var2));
	        var1.play(var2);
	    }
	}

    @Mod.PostInit
    public void modsLoaded(FMLPostInitializationEvent var1) {}

    @Mod.ServerStarted
    public void serverStarted(FMLServerStartedEvent var1) {}
    


}

    

