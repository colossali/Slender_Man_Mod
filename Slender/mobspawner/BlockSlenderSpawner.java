package colossali.Slender.mobspawner;

import java.util.Random;

import colossali.Slender.common.EntitySlenderMan;
import colossali.Slender.common.mod_slenderman;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSlenderSpawner extends BlockContainer
{
    private static int[] tex;
    private static int texuse;

    
    public BlockSlenderSpawner(int var1, int var2)
    {
        super(var1, Material.iron);
        super.setTickRandomly(this.getTickRandomly());

    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        var1.scheduleBlockUpdate(var2, var3, var4, this.blockID, this.tickRate());

    }


    /**
    * The type of render function that is called for this block
    */
    public int getRenderType()
    {
    return -2;
    }
    
    /**
    * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
    */
    public boolean renderAsNormalBlock()
    {
    return false;
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
    
    //Update nearby blocks on removal
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
     * Check if there are two wool below
     * If yes, sets "can spawn" to true and spawns lightning
     */
    public void updateTick(World var1, int var2, int var3, int var4, Random var5)
    {

                if (var1.getBlockId(var2, var3 - 1, var4) == Block.obsidian.blockID)
                {
                    if (var1.getBlockId(var2, var3 - 2, var4) == Block.obsidian.blockID)
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
                            if(var7 != null)
                            var7.addChatMessage("\u00a7k\u00a7412948f0f2gad");
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

    
    //Unsetting the block
    public void unset(World var1, int var2, int var3, int var4)
    {
        if (mod_slenderman.canSpawn || texuse == 1)
        {
            var1.getBlockMetadata(var2, var3, var4);
            texuse = 0;
            byte var5 = 0;
            var1.spawnEntityInWorld(new EntitySlenderMan(var1));
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
    
    //Bind Slender Spawner Tile entity to this block
    public TileEntity createNewTileEntity(World world)
    {
       return new TileEntitySlenderSpawner();
    }
    
  //This will tell minecraft not to render any side of our cube.
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
       return false;
    }

    //And this tell it that you can see through this block, and neighbor blocks should be rendered.
    public boolean isOpaqueCube()
    {
       return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
             this.blockIcon = par1IconRegister.registerIcon("Slender:soulstone");
    }


}
