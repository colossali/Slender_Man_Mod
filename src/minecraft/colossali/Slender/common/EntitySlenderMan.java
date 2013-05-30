package colossali.Slender.common;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.src.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySlenderMan extends EntityMob
{
	private int breakLight;
	private int breakNextLight;
	public static int musictimer;
	public boolean normalArms;
	public boolean AttackingArms;
	private boolean destroyPerGo;
	private boolean canSeeSkyAndDay;
	private int petrifyTimer;
	private int damageTimer;
	private boolean timeLocked;
	private int teleportDelay;
	private int spawntimer;
	private int timeTillNextTeleport = 0;
	private int Teleport200 = 0;
	private int Teleport100 = 0;
	private int[] seeThrough = new int[] {20, 8, 9, 10, 11, 18, 27, 28, 30, 31, 32, 37, 38, 39, 40, 44, 50, 51, 52, 59, 64, 65, 66, 67, 69, 70, 71, 72, 75, 76, 77, 78, 83, 85, 90, 92, 96, 101, 102, 106, 107, 108, 109, 111, 113, 114, 114, 117};
	private int ScaryTimer = 0;
	private static int randdist;
	private int directlookscare = 0;
	private int potiontimer = 0;
	private int alternating = 0;
	private double randZonCircle;
	private double randXonCircle;
	public static float opacity = 0;

	private int staticsoundtimer = 20;


	public EntitySlenderMan(World var1)
	{
		super(var1);
		this.texture = "/slenderman/Slender.png";
		this.teleportDelay = 0;
		this.moveSpeed = 0.2F;
		this.stepHeight = 5F;
		this.setSize(0.8F, 2.5F);
		this.isImmuneToFire = true;
		this.breakNextLight = this.rand.nextInt(100);
		this.normalArms = false;
		this.spawntimer = 5;
		if (!mod_slenderman.canSpawn && !this.worldObj.isRemote)
		{
			this.setDead();
		}
	}


	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	protected int getDropItemId()
	{
		return mod_slenderman.ItemChildSoul.itemID;
	}


	public int getAttackStrength(Entity par1Entity)
	{
		return 18;
	}
	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean var1, int var2)
	{
		int var3 = this.getDropItemId();

		if (var3 > 0)
		{
			int var4 = this.rand.nextInt(2 + var2);

			for (int var5 = 0; var5 < var4; ++var5)
			{
				this.dropItem(var3, 1);
			}
		}
	}

	public int getMaxHealth()
	{
		return 100;
	}



	/**
	 * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
	 * (Animals, Spiders at day, peaceful PigZombies).
	 */
	protected Entity findPlayerToAttack()
	{
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 200.0D);
		return var1 != null && this.canSlendermanBeSeen(var1) ? var1 : null;
	}


	protected boolean teleportRandomly()
	{
		double var1 = this.posX + (this.rand.nextDouble() - 0.5D) * 12.0D;
		double var3 = this.posY + (double)(this.rand.nextInt(64) - 32);
		double var5 = this.posZ + (this.rand.nextDouble() - 0.5D) * 12.0D;
		return this.teleportTo1(var1, var3, var5);
	}

	/**
	 * Use to produce a random X value on the circumference of a range of circles
	 * 
	 * @param radius1 = lower range of distance to entity
	 * @param radius2 = higher range of distance to entity
	 * @param SlendyposX = actually the X value of the entity at the center of the circle
	 * @return randXonCircle = the random generated X value
	 */

	public double randomXonCircle(int radius1, int radius2, double SlendyposX)
	{		
		int radius = radius2 - radius1;
		double angle = Math.random()*Math.PI*2;
		randXonCircle = SlendyposX + (radius * Math.cos(angle));
		return randXonCircle;

	}

	/**
	 * Use to produce a random Z value on the circumference of a range of circles
	 * @param radius1 = lower range of radius (eg 60)
	 * @param radius2 = higher range of radius (eg 120)
	 * @param SlendyposZ = actually the Z value of the entity or object at center of the circle
	 * @return
	 */

	public double randomZonCircle(int radius1, int radius2, double SlendyposZ)
	{
		int radius = radius2 - radius1;
		double angle = Math.random()*Math.PI*2;
		randZonCircle = SlendyposZ + (radius * Math.sin(angle));
		return randZonCircle;

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if(!this.worldObj.isRemote)
		{					
			this.worldObj.spawnParticle("depthsuspend", posX + (rand.nextDouble() - 0.5D) * (double)width, (posY + rand.nextDouble() * (double)height) - 0.25D, posZ + (rand.nextDouble() - 0.5D) * (double)width, 1.0D + rand.nextDouble(), 1.0D + rand.nextDouble(), 1.0D + rand.nextDouble());
			EntityPlayer var3 = this.worldObj.getClosestPlayerToEntity(this, 200.0D);
			this.destroyPerGo = false;
			this.moveSpeed = this.entityToAttack == null ? 0.3F : 7.0F;
			this.isJumping = false;

			//timers
			if (timeTillNextTeleport < 100)	timeTillNextTeleport++;
			if (Teleport200 < 800) Teleport200++;
			if (Teleport100 < 400) Teleport100++;
			if (directlookscare < 60) directlookscare++;
			if (potiontimer < 200) potiontimer++;
			if(ScaryTimer < 300) ScaryTimer++;
			if(staticsoundtimer < 20) staticsoundtimer++;



			//teleporters
			if (this.getDistancetoEntityToAttack() >= 100.0D && this.getDistancetoEntityToAttack() <= 250.0D && this.getDistancetoEntityToAttack() <= 200.0D && Teleport200 == 800)
			{
				double newRandX = this.randomXonCircle(60, 120, var3.posX);
				double newRandZ = this.randomZonCircle(60,120, var3.posZ);
				this.teleportTo1(newRandX, var3.posY, newRandZ);
				Teleport200 = 0;
			}

			if (this.getDistancetoEntityToAttack() >= 65.0D && this.getDistancetoEntityToAttack() <= 99.0D && this.getDistancetoEntityToAttack() <= 200.0D && Teleport100 == 400)
			{
				double newRandX = this.randomXonCircle(40, 75, var3.posX);
				double newRandZ = this.randomZonCircle(40, 75, var3.posZ);
				teleportTo1(newRandX, var3.posY, newRandZ);
				Teleport100 = 0;		
				Random randomer = new Random();
				int playscarynoise = randomer.nextInt(5);
				if(ScaryTimer == 300 && playscarynoise == 5)
				{
					this.worldObj.playSoundAtEntity(var3, "scare", this.getSoundVolume(), 1.0F);
					ScaryTimer = 0;
				}
			}


			if (this.getDistancetoEntityToAttack() <= 64.0D && musictimer == 2400)
			{
				this.worldObj.playSoundAtEntity(this, "close", this.getSoundVolume() + 10F, 1.0F);
				musictimer = 0;
			}


			if (this.entityToAttack != null && this.entityToAttack instanceof EntityPlayer)
			{
				if (this.canSlendermanBeSeen((EntityPlayer)this.entityToAttack) && this.getDistancetoEntityToAttack() <= 100)
				{
					float level =(float)this.getDistancetoEntityToAttack();
					opacity = 1.5F/level;	
					if(staticsoundtimer == 20)
					{
						this.worldObj.playSoundAtEntity(this, "static", this.getSoundVolume() + opacity*4, 1.0F);
						staticsoundtimer = 0;
					}
				}
				else if (!this.canSlendermanBeSeen((EntityPlayer)this.entityToAttack))
				{
					opacity = 0;
				}

				if (!this.canSlendermanBeSeen((EntityPlayer)this.entityToAttack))
				{
					if (this.getDistancetoEntityToAttack() <= 64.0D && this.getDistancetoEntityToAttack() >= 16.0D && timeTillNextTeleport == 100)
					{
						double newRandX = this.randomXonCircle(10, 40, var3.posX);
						double newRandZ = this.randomZonCircle(10, 40, var3.posZ);

						teleportTo1(newRandX, var3.posY, newRandZ);
						timeTillNextTeleport = 0;
					}                

					if (this.getDistancetoEntityToAttack() <= 15.0D && timeTillNextTeleport == 100)
					{
						double newRandX = this.randomXonCircle(3, 10, var3.posX);
						double newRandZ = this.randomZonCircle(3, 10, var3.posZ);

						teleportTo1(newRandX, var3.posY, newRandZ);
						if(ScaryTimer == 300)
						{
							this.worldObj.playSoundAtEntity(var3, "scare", this.getSoundVolume(), 1.0F);
							ScaryTimer = 0;
						}
						timeTillNextTeleport = 0;
					}


					if (this.entityToAttack instanceof EntityPlayer && this.getDistancetoEntityToAttack() <= 5.0D)
					{

						this.texture = "/slenderman/SlenderInv.png";
						this.AttackingArms = true;

					}
					else
					{
						this.texture = "/slenderman/Slender.png";
						this.AttackingArms = false;
					}


				}

				if (this.isPlayerLooking(var3))
				{
					this.petrifyTimer = 20;
				}


				if (this.worldObj.getFullBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) < 1 && this.worldObj.getFullBlockLightValue(MathHelper.floor_double(this.entityToAttack.posX), MathHelper.floor_double(this.entityToAttack.posY), MathHelper.floor_double(this.entityToAttack.posZ)) < 1)
				{
					this.worldObj.playSoundAtEntity(this, "attack", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
				}

				if (this.petrifyTimer <= 20 && this.petrifyTimer >= 15 && this.potiontimer == 200)
				{

					var3.motionX *= 0.01D;
					var3.motionZ *= 0.01D;

					((EntityLiving)this.entityToAttack).addPotionEffect(new PotionEffect(Potion.confusion.id, 10 * 10, 2));
					((EntityLiving)this.entityToAttack).addPotionEffect(new PotionEffect(Potion.blindness.id, 10 * 10, 2));
					this.moveStrafing = this.moveForward = 5.0F;
					this.moveSpeed = 5.0F;
					this.teleportToEntity(var3);

					this.petrifyTimer--;
					this.potiontimer--;
					this.potiontimer--;
					this.potiontimer--;

					if (this.directlookscare == 60)
					{
						this.worldObj.playSoundAtEntity(var3, "lights", this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
						this.directlookscare = 0;
					}
				}

				if (this.entityToAttack instanceof EntityPlayer && (this.canSlendermanBeSeen((EntityPlayer)this.entityToAttack) || this.timeLocked))
				{
					this.slendermanDirectLook((EntityPlayer)this.entityToAttack);
					this.moveStrafing = this.moveForward = 0.0F;
					this.moveSpeed = 0.0F;
					++this.breakLight;

					if (this.breakLight >= this.breakNextLight && !this.canSeeSkyAndDay)
					{
						this.breakLight = 0;
						this.breakNextLight = this.rand.nextInt(100);
						this.findNearestTorch();
					}
				}
				else
				{
					this.faceEntity(this.entityToAttack, 100.0F, 100.0F);
				}
			}

		}

	}



	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	public boolean canBePushed()
	{
		return true;
	}

	private boolean isPlayerLooking(EntityPlayer par1EntityPlayer)
	{
		if (par1EntityPlayer != null)
		{
			ItemStack var2 = par1EntityPlayer.inventory.armorInventory[3];

			if (var2 != null && var2.itemID == mod_slenderman.ItemSlenderMask.itemID)
			{
				return false;
			}

			else
			{
				Vec3 var3 = par1EntityPlayer.getLook(1.0F).normalize();
				Vec3 var4 = Vec3.fakePool.getVecFromPool(this.posX - par1EntityPlayer.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - (par1EntityPlayer.posY + (double)par1EntityPlayer.getEyeHeight()), this.posZ - par1EntityPlayer.posZ);
				double var5 = var4.lengthVector();
				var4 = var4.normalize();
				double var7 = var3.dotProduct(var4);
				return var7 > 1.0D - 0.025D / var5 ? par1EntityPlayer.canEntityBeSeen(this) : false;
			}
		}
		else return false;
	}

	private boolean slendermanDirectLook(EntityPlayer var1)
	{
		if (this.worldObj.getFullBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) < 1)
		{
			return false;
		}
		else
		{
			Vec3 var2 = var1.getLook(1.0F).normalize();
			Vec3 var3 = Vec3.createVectorHelper(this.posX - var1.posX, this.boundingBox.minY + (double)this.height - var1.posY + (double)var1.getEyeHeight(), this.posZ - var1.posZ);
			double var4 = var3.lengthVector();
			var3 = var3.normalize();
			double var6 = var2.dotProduct(var3);

			if (var6 > 1.0D - 0.025D / var4)
			{

				if (var1.canEntityBeSeen(this))
				{
					this.petrifyTimer = this.rand.nextInt(100);
				}

				return var1.canEntityBeSeen(this);

			}
			else
			{
				return false;
			}
		}
	}

	private boolean canSlendermanBeSeen(EntityPlayer var1)
	{
		return !var1.canEntityBeSeen(this) && !this.LineOfSightCheck(var1) ? false : this.isInFieldOfVision(this, var1, 65.0F, 65.0F);
	}

	private boolean LineOfSightCheck(EntityLiving var1)
	{
		return this.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3.createVectorHelper(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null || this.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double)this.height, this.posZ), Vec3.createVectorHelper(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null || this.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double)this.height * 0.1D, this.posZ), Vec3.createVectorHelper(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null || this.rayTraceBlocks(Vec3.createVectorHelper(this.posX + 0.7D, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3.createVectorHelper(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null || this.rayTraceBlocks(Vec3.createVectorHelper(this.posX - 0.7D, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3.createVectorHelper(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null || this.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ + 0.7D), Vec3.createVectorHelper(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null || this.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ - 0.7D), Vec3.createVectorHelper(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null || this.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double)this.height * 1.2D, this.posZ - 0.7D), Vec3.createVectorHelper(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null || this.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double)this.height * 1.2D + 1.0D, this.posZ), Vec3.createVectorHelper(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null;
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

	protected boolean canDespawn()
	{
		return true;
	}

	protected boolean teleportTo1(double var1, double var3, double var5)
	{
		double var7 = this.posX;
		double var9 = this.posY;
		double var11 = this.posZ;
		this.posX = var1;
		this.posY = var3;
		this.posZ = var5;
		boolean var13 = false;
		int var14 = MathHelper.floor_double(this.posX);
		int var15 = MathHelper.floor_double(this.posY);
		int var16 = MathHelper.floor_double(this.posZ);
		boolean var17;

		if (this.worldObj.blockExists(var14, var15, var16))
		{
			var17 = false;

			while (!var17 && var15 > 0)
			{
				int var18 = this.worldObj.getBlockId(var14, var15 - 1, var16);

				if (var18 != 0 && Block.blocksList[var18].blockMaterial.isSolid())
				{
					var17 = true;
				}
				else
				{
					--this.posY;
					--var15;
				}
			}

			if (var17)
			{
				this.setPosition(this.posX, this.posY, this.posZ);
				if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox))
				{
					var13 = true;
				}
			}
		}

		if (!var13)
		{
			this.setPosition(var7, var9, var11);
			return false;
		}
		else
		{
			var17 = true;
			return true;
		}
	}

	protected boolean teleportToEntity(Entity var1)
	{
		Vec3 var2 = Vec3.createVectorHelper(this.posX - var1.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - var1.posY + (double)var1.getEyeHeight(), this.posZ - var1.posZ);
		var2 = var2.normalize();
		double var3 = 6.0D;
		double var5 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - var2.xCoord * var3;
		double var7 = this.posY + (double)(this.rand.nextInt(16) - 8) - var2.yCoord * var3;
		double var9 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - var2.zCoord * var3;
		return this.teleportTo1(var5, var7, var9);
	}

	private boolean isInFieldOfVision(EntitySlenderMan var1, EntityLiving var2, float var3, float var4)
	{
		float var5 = var2.rotationYaw;
		float var6 = var2.rotationPitch;
		var2.faceEntity(var1, 360.0F, 360.0F);
		float var7 = var2.rotationYaw;
		float var8 = var2.rotationPitch;
		var2.rotationYaw = var5;
		var2.rotationPitch = var6;
		float var11 = var2.rotationYaw - var3;
		float var12 = var2.rotationYaw + var3;
		float var13 = var2.rotationPitch - var4;
		float var14 = var2.rotationPitch + var4;
		boolean var15 = this.GetFlag(var11, var12, var7, 0.0F, 360.0F);
		boolean var16 = this.GetFlag(var13, var14, var8, -180.0F, 180.0F);
		return var15 && var16 && (var2.canEntityBeSeen(var1) || this.LineOfSightCheck(var2));

	}

	public boolean GetFlag(float var1, float var2, float var3, float var4, float var5)
	{
		if (var1 < var4)
		{
			if (var3 >= var1 + var5)
			{
				return true;
			}

			if (var3 <= var2)
			{
				return true;
			}
		}

		if (var2 >= var5)
		{
			if (var3 <= var2 - var5)
			{
				return true;
			}

			if (var3 >= var1)
			{
				return true;
			}
		}

		return var2 < var5 && var1 >= var4 ? var3 <= var2 && var3 > var1 : false;
	}

	public double getDistancetoEntityToAttack()
	{
		if (this.entityToAttack instanceof EntityPlayer)
		{
			double var8 = this.entityToAttack.posX - this.posX;
			double var3 = this.entityToAttack.posY - this.posY;
			double var5 = this.entityToAttack.posZ - this.posZ;

			return (double)MathHelper.sqrt_double(var8 * var8 + var3 * var3 + var5 * var5);
		}
		else
		{
			EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 200.0D);

			if (var1 != null)
			{
				double var2 = var1.posX - this.posX;
				double var4 = var1.posY - this.posY;
				double var6 = var1.posZ - this.posZ;
				return (double)MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
			}
			else
			{
				return 10.0D;
			}
		}
	}

	public double getDistance(int var1, int var2, int var3, int var4, int var5, int var6)
	{
		int var7 = var4 - var1;
		int var8 = var5 - var2;
		int var9 = var6 - var3;
		return Math.sqrt((double)(var7 * var7 + var8 * var8 + var9 * var9));
	}

	private void findNearestTorch()
	{
		int var1 = (int)this.posX;
		int var2 = (int)this.posY;
		int var3 = (int)this.posZ;
		int var4 = var1 + 10;
		int var5 = var2 + 10;
		int var6 = var3 + 10;
		int var7 = var1 - 10;
		int var8 = var2 - 10;
		int var9 = var3 - 10;
		byte var10 = 100;

		for (int var11 = var7; var11 < var4; ++var11)
		{
			int var12 = var8;

			while (var12 < var5)
			{
				int var13 = var9;

				while (true)
				{
					if (var13 < var6)
					{
						label70:
						{
						if (this.getDistance(var1, var2, var3, var11, var12, var13) <= (double)var10)
						{
							int var14 = this.worldObj.getBlockId(var11, var12, var13);
							Block var15 = var14 <= 0 ? null : Block.blocksList[var14];

							if (var15 != null && (var15 == Block.torchWood || var15 == Block.torchRedstoneActive || var15 == Block.planks || var15 == Block.brick || var15 == Block.doorWood || var15 == Block.glass || var15 == Block.doorIron || var15 == Block.trapdoor || var15 == Block.redstoneLampActive || var15 == Block.redstoneRepeaterActive || var15 == Block.glowStone) && this.worldObj.rayTraceBlocks(Vec3.createVectorHelper(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3.createVectorHelper((double)var11, (double)var12, (double)var13)) == null && this.worldObj.rayTraceBlocks(Vec3.createVectorHelper(this.entityToAttack.posX, this.entityToAttack.posY + (double)this.entityToAttack.getEyeHeight(), this.entityToAttack.posZ), Vec3.createVectorHelper((double)var11, (double)var12, (double)var13)) == null)
							{
								if (!this.destroyPerGo)
								{
									if( var15 == Block.glass)
									{
										this.worldObj.playSoundAtEntity(this, "random.glass", this.getSoundVolume() + 10F, 1.0F);
									}

									if( var15 == Block.doorWood || var15 == Block.doorIron)

									{
										this.worldObj.playSoundAtEntity(this, "mob.zombie.woodbreak", this.getSoundVolume() + 10F, 1.0F);
									}
									var15.dropBlockAsItem(this.worldObj, var11, var12, var13, 1, 1);
									this.worldObj.setBlockMetadataWithNotify(var11, var12, var13, 0, 10);
									this.worldObj.setBlockToAir(var11, var12, var13);
									this.destroyPerGo = true;
								}

								break label70;
							}
						}

						++var13;
						continue;
						}
					}

					++var12;
					break;
				}
			}
		}
	}

	public boolean slendermanSeeSlenderman(EntitySlenderMan var1)
	{
		return this.worldObj.getFullBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) < 1 ? false : (this.normalArms && this.AttackingArms ? this.isInFieldOfVision(var1, this, 40.0F, 65.0F) : false);
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource var1, int var2)
	{
		if (var1 == null)
		{
			return false;
		}
		else
		{
			if (var1.getSourceOfDamage() instanceof EntityPlayer)
			{
				EntityPlayer var3 = (EntityPlayer)var1.getSourceOfDamage();
				ItemStack var4 = var3.inventory.getCurrentItem();

				if (this.worldObj.difficultySetting > 2)
				{
					if (var4 != null && (var4.itemID == Item.swordDiamond.itemID || var4.canHarvestBlock(Block.obsidian) || var4.itemID == mod_slenderman.ItemSlenderSword.itemID))
					{
						super.attackEntityFrom(var1, var2);
						double newRandX = this.randomXonCircle(10, 40, var3.posX);
						double newRandZ = this.randomZonCircle(10, 40, var3.posZ);
						teleportTo1(newRandX, var3.posY, newRandZ);
						this.teleportDelay = 0;

					}
				}
				else if (var4 != null && (var4.itemID == Item.swordDiamond.itemID || var4.itemID == Item.swordWood.itemID || var4.itemID == Item.swordStone.itemID || var4.canHarvestBlock(Block.oreDiamond) && var4.itemID != Item.swordDiamond.itemID || var4.itemID == mod_slenderman.ItemSlenderSword.itemID))
				{
					super.attackEntityFrom(var1, var2);
					double newRandX = this.randomXonCircle(10, 40, var3.posX);
					double newRandZ = this.randomZonCircle(10, 40, var3.posZ);
					teleportTo1(newRandX, var3.posY, newRandZ);
					this.teleportDelay = 0;
				}
			}

			return false;
		}
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "death";
	}

	@Override
	public void setDead()
	{
		mod_slenderman.spawned = true;
		this.isDead = true;
		this.opacity = 0;
	}


}
