package colossali.Slender.mobspawner;

import net.minecraft.block.Block;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.world.World;

class TileEntityMobSpawnerLogic extends SlenderSpawnerBaseLogic
{
    /** The mob spawner we deal with */
    final TileEntitySlenderSpawner mobSpawnerEntity;

    TileEntityMobSpawnerLogic(TileEntitySlenderSpawner par1TileEntityMobSpawner)
    {
        this.mobSpawnerEntity = par1TileEntityMobSpawner;
    }

    public void func_98267_a(int par1)
    {
        this.mobSpawnerEntity.worldObj.addBlockEvent(this.mobSpawnerEntity.xCoord, this.mobSpawnerEntity.yCoord, this.mobSpawnerEntity.zCoord, Block.mobSpawner.blockID, par1, 0);
    }

    public World getSpawnerWorld()
    {
        return this.mobSpawnerEntity.worldObj;
    }

    public int getSpawnerX()
    {
        return this.mobSpawnerEntity.xCoord;
    }

    public int getSpawnerY()
    {
        return this.mobSpawnerEntity.yCoord;
    }

    public int getSpawnerZ()
    {
        return this.mobSpawnerEntity.zCoord;
    }

}
