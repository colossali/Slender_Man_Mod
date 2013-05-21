package colossali.Slender.common;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

public class BlockSlenderSpawner extends Block
{
    private static int[] tex;
    private static int texuse;

    protected BlockSlenderSpawner(int var1, int var2)
    {
        super(var1, Material.rock);
        super.setTickRandomly(this.getTickRandomly());

    }

    public String getTextureFile()
    {
            return "/slenderman/items.png";
    }
    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
     */
    public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5)
    {
        mod_slenderman.canSpawn = false;
        EntityPlayer var6 = var1.getClosestPlayer((double)var2, (double)var3, (double)var4, 3000.0D);
        
        if (!var1.isRemote)
        {
            var6.addChatMessage("\u00a74This is only the beginning...\u00a7kdhagyfw");
        }


    }

    public void onBlockRemoval(World var1, int var2, int var3, int var4)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());
    }

    /**
     * Called upon the block being destroyed by an explosion
     */
    public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4)
    {
        mod_slenderman.canSpawn = false;
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5)
    {
        this.updateTick(var1, var2, var3, var4, var5);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {

                if (var1.getBlockId(var2, var3 - 1, var4) == Block.cloth.blockID)
                {
                    if (var1.getBlockId(var2, var3 - 2, var4) == Block.cloth.blockID)
                    {
                        if (!mod_slenderman.canSpawn || texuse == 0)
                        {
                            var1.getBlockMetadata(var2, var3, var4);
                            texuse = 1;
                            mod_slenderman.canSpawn = true;
                            byte var6 = 1;
                            var1.setBlockMetadataWithNotify(var2, var3, var4, var6, 10);
                            var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
                            var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
                            var1.markBlocksDirtyVertical(var2, var3, var4, var2);
                            var1.scheduleBlockUpdate(var2, var3, var4, mod_slenderman.BlockSlenderSpawner.blockID, 10);
                            EntityPlayer var7 = var1.getClosestPlayer((double)var2, (double)var3, (double)var4, 3000.0D);
                            var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "ambient.cave.cave", 35.0F, 0.8F);
                            var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "ambient.cave.cave", 35.0F, 0.8F);
                            var7.addChatMessage("\u00a7k12948f0f2gad");
                            var1.spawnEntityInWorld(new EntityLightningBolt(var1, (double)var2, (double)(var3 + 2), (double)var4));
                            var1.addWeatherEffect(new EntityLightningBolt(var1, (double)var2, (double)(var3 + 2), (double)var4));
                        }
                    }
                    else
                    {
                        this.unset(var1, var2, var3, var4);
                    }
                }
                else
                {
                    this.unset(var1, var2, var3, var4);
                }
            }

    

    public void unset(World var1, int var2, int var3, int var4)
    {
        if (mod_slenderman.canSpawn || texuse == 1)
        {
            var1.getBlockMetadata(var2, var3, var4);
            texuse = 0;
            byte var5 = 0;
            mod_slenderman.canSpawn = false;
            var1.setBlockMetadataWithNotify(var2, var3, var4, var5, 10);
            var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
            var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
            var1.markBlocksDirtyVertical(var2, var3, var4, var2);
            var1.scheduleBlockUpdate(var2, var3, var4, mod_slenderman.BlockSlenderSpawner.blockID, 10);
        }
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate()
    {
        return 2;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
             this.blockIcon = par1IconRegister.registerIcon("Slender:soulstone");
    }


}
