package colossali.Slender.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTwardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMasky extends EntityMob
{
	private int[] seeThrough = new int[] {20, 5, 1, 2, 3, 4, 12, 14, 17, 24, 35, 8, 9, 10, 11, 18, 27, 28, 30, 31, 32, 37, 38, 39, 40, 44, 50, 51, 52, 59, 64, 65, 66, 67, 69, 70, 71, 72, 75, 76, 77, 78, 83, 85, 90, 92, 96, 101, 102, 106, 107, 108, 109, 111, 113, 114, 114, 117};


    public EntityMasky(World par1World)
    {
        super(par1World);
        this.texture = "/slenderman/masky.png";
        this.moveSpeed = 0.63F;
        this.stepHeight = 4;
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
        this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
        this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
    }

    protected int func_96121_ay()
    {
        return 30;
    }

	private boolean isBlockTransparent(int var1)
	{
		for (int var2 = 0; var2 < this.seeThrough.length; ++var2)
		{
			if (var1 == this.seeThrough[var2])
			{
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
	 * (Animals, Spiders at day, peaceful PigZombies).
	 */
	protected Entity findPlayerToAttack()
	{
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 200.0D);
		return var1;
	}
	
	private MovingObjectPosition rayTraceBlocks(Vec3 var1, Vec3 var2)
	{
		boolean var3 = false;
		boolean var4 = false;

		if (!Double.isNaN(var1.xCoord) && !Double.isNaN(var1.yCoord) && !Double.isNaN(var1.zCoord))
		{
			if (!Double.isNaN(var2.xCoord) && !Double.isNaN(var2.yCoord) && !Double.isNaN(var2.zCoord))
			{
				int var5 = MathHelper.floor_double(var2.xCoord);
				int var6 = MathHelper.floor_double(var2.yCoord);
				int var7 = MathHelper.floor_double(var2.zCoord);
				int var8 = MathHelper.floor_double(var1.xCoord);
				int var9 = MathHelper.floor_double(var1.yCoord);
				int var10 = MathHelper.floor_double(var1.zCoord);
				int var11 = this.worldObj.getBlockId(var8, var9, var10);
				int var12 = this.worldObj.getBlockMetadata(var8, var9, var10);
				Block var13 = Block.blocksList[var11];

				if ((!var4 || var13 == null || var13.getCollisionBoundingBoxFromPool(this.worldObj, var8, var9, var10) != null) && var11 > 0 && var13.canCollideCheck(var12, var3))
				{
					MovingObjectPosition var14 = var13.collisionRayTrace(this.worldObj, var8, var9, var10, var1, var2);

					if (var14 != null)
					{
						return var14;
					}
				}

				int var42 = 200;

				while (var42-- >= 0)
				{
					if (Double.isNaN(var1.xCoord) || Double.isNaN(var1.yCoord) || Double.isNaN(var1.zCoord))
					{
						return null;
					}

					if (var8 == var5 && var9 == var6 && var10 == var7)
					{
						return null;
					}

					boolean var15 = true;
					boolean var16 = true;
					boolean var17 = true;
					double var18 = 999.0D;
					double var20 = 999.0D;
					double var22 = 999.0D;

					if (var5 > var8)
					{
						var18 = (double)var8 + 1.0D;
					}
					else if (var5 < var8)
					{
						var18 = (double)var8 + 0.0D;
					}
					else
					{
						var15 = false;
					}

					if (var6 > var9)
					{
						var20 = (double)var9 + 1.0D;
					}
					else if (var6 < var9)
					{
						var20 = (double)var9 + 0.0D;
					}
					else
					{
						var16 = false;
					}

					if (var7 > var10)
					{
						var22 = (double)var10 + 1.0D;
					}
					else if (var7 < var10)
					{
						var22 = (double)var10 + 0.0D;
					}
					else
					{
						var17 = false;
					}

					double var24 = 999.0D;
					double var26 = 999.0D;
					double var28 = 999.0D;
					double var30 = var2.xCoord - var1.xCoord;
					double var32 = var2.yCoord - var1.yCoord;
					double var34 = var2.zCoord - var1.zCoord;

					if (var15)
					{
						var24 = (var18 - var1.xCoord) / var30;
					}

					if (var16)
					{
						var26 = (var20 - var1.yCoord) / var32;
					}

					if (var17)
					{
						var28 = (var22 - var1.zCoord) / var34;
					}

					boolean var36 = false;
					byte var43;

					if (var24 < var26 && var24 < var28)
					{
						if (var5 > var8)
						{
							var43 = 4;
						}
						else
						{
							var43 = 5;
						}

						var1.xCoord = var18;
						var1.yCoord += var32 * var24;
						var1.zCoord += var34 * var24;
					}
					else if (var26 < var28)
					{
						if (var6 > var9)
						{
							var43 = 0;
						}
						else
						{
							var43 = 1;
						}

						var1.xCoord += var30 * var26;
						var1.yCoord = var20;
						var1.zCoord += var34 * var26;
					}
					else
					{
						if (var7 > var10)
						{
							var43 = 2;
						}
						else
						{
							var43 = 3;
						}

						var1.xCoord += var30 * var28;
						var1.yCoord += var32 * var28;
						var1.zCoord = var22;
					}

					Vec3 var37 = Vec3.createVectorHelper(var1.xCoord, var1.yCoord, var1.zCoord);
					var8 = (int)(var37.xCoord = (double)MathHelper.floor_double(var1.xCoord));

					if (var43 == 5)
					{
						--var8;
						++var37.xCoord;
					}

					var9 = (int)(var37.yCoord = (double)MathHelper.floor_double(var1.yCoord));

					if (var43 == 1)
					{
						--var9;
						++var37.yCoord;
					}

					var10 = (int)(var37.zCoord = (double)MathHelper.floor_double(var1.zCoord));

					if (var43 == 3)
					{
						--var10;
						++var37.zCoord;
					}

					int var38 = this.worldObj.getBlockId(var8, var9, var10);
					int var39 = this.worldObj.getBlockMetadata(var8, var9, var10);
					Block var40 = Block.blocksList[var38];

					if ((!var4 || var40 == null || var40.getCollisionBoundingBoxFromPool(this.worldObj, var8, var9, var10) != null) && var38 > 0 && var40.canCollideCheck(var39, var3) && !this.isBlockTransparent(var38))
					{
						MovingObjectPosition var41 = var40.collisionRayTrace(this.worldObj, var8, var9, var10, var1, var2);

						if (var41 != null)
						{
							return var41;
						}
					}
				}

				return null;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}


    protected void entityInit()
    {
        super.entityInit();
        this.getDataWatcher().addObject(12, Byte.valueOf((byte)0));
        this.getDataWatcher().addObject(13, Byte.valueOf((byte)0));
        this.getDataWatcher().addObject(14, Byte.valueOf((byte)0));
    }

    public int getMaxHealth()
    {
        return 30;
    }


    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }


    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
    }

    public boolean attackEntityAsMob(Entity par1Entity)
    {
        boolean flag = super.attackEntityAsMob(par1Entity);

        if (flag && this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < (float)this.worldObj.difficultySetting * 0.3F)
        {
            par1Entity.setFire(2 * this.worldObj.difficultySetting);
        }

        return flag;
    }

    /**
     * Returns the amount of damage a mob should deal.
     */
    public int getAttackStrength(Entity par1Entity)
    {
        ItemStack itemstack = this.getHeldItem();
        float f = (float)(this.getMaxHealth() - this.getHealth()) / (float)this.getMaxHealth();
        int i = 3 + MathHelper.floor_float(f * 4.0F);

        if (itemstack != null)
        {
            i += itemstack.getDamageVsEntity(this);
        }

        return i;
    }


    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound("mob.zombie.step", 0.15F, 1.0F);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected void dropRareDrop(int par1)
    {
        switch (this.rand.nextInt(3))
        {
            case 0:
                this.dropItem(Item.ingotIron.itemID, 1);
                break;
            case 1:
                this.dropItem(Item.carrot.itemID, 1);
                break;
            case 2:
                this.dropItem(Item.potato.itemID, 1);
        }
    }


    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLiving par1EntityLiving)
    {
        super.onKillEntity(par1EntityLiving);

        if (this.worldObj.difficultySetting >= 2 && par1EntityLiving instanceof EntityVillager)
        {
            if (this.worldObj.difficultySetting == 2 && this.rand.nextBoolean())
            {
                return;
            }

            EntityMasky entityzombie = new EntityMasky(this.worldObj);
            entityzombie.func_82149_j(par1EntityLiving);
            this.worldObj.removeEntity(par1EntityLiving);
            entityzombie.initCreature();
            this.worldObj.spawnEntityInWorld(entityzombie);
            this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        }
    }


}
