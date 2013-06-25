package colossali.Slender.client;


import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import org.lwjgl.input.Keyboard;

import colossali.Slender.common.EntitySlenderMan;
import colossali.Slender.common.mod_slenderman;


public class ClientTickHandler implements ITickHandler
{
    private final EnumSet ticksToGet;
    public static int musictimer = 2400;
	public static boolean StaticLoop = false;
	private boolean isTeleportKeyDown = false;
	private double randXonCircle;
	private double randZonCircle;
    
    public ClientTickHandler(EnumSet var1)
    {
        this.ticksToGet = var1;
    }    
    

    
    public void tickStart(EnumSet var1, Object ... var2)
    {
        EntityClientPlayerMP var3 = Minecraft.getMinecraft().thePlayer;        
   
        
        if (var3 != null)
        {
        if (EntitySlenderMan.musictimer < 2400)
        {
        	EntitySlenderMan.musictimer++;
        }
        
        if (var3.inventory.armorItemInSlot(2) != null)
        {
            this.handlePlate(var3.inventory.armorItemInSlot(2).itemID);
        }
        

		//static effect loop
		if (!StaticLoop) StaticLoop = true;
		else if (StaticLoop) StaticLoop = false;
		
	     isTeleportKeyDown = Keyboard.isKeyDown(19);
        }
                
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


	protected boolean teleportTo1(double var1, double var3, double var5)
	{
		Minecraft var22 = Minecraft.getMinecraft();
		   EntityClientPlayerMP var130 = Minecraft.getMinecraft().thePlayer;   
		   WorldClient var40 = var22.theWorld;
		   
		double var7 = var130.posX;
		double var9 = var130.posY;
		double var11 = var130.posZ;
		var130.posX = var1;
		var130.posY = var3;
		var130.posZ = var5;
		boolean var13 = false;
		int var14 = MathHelper.floor_double(var130.posX);
		int var15 = MathHelper.floor_double(var130.posY);
		int var16 = MathHelper.floor_double(var130.posZ);
		boolean var17;

		if (var40.blockExists(var14, var15, var16))
		{
			var17 = false;

			while (!var17 && var15 > 0)
			{
				int var18 = var40.getBlockId(var14, var15 - 1, var16);

				if (var18 != 0 && Block.blocksList[var18].blockMaterial.isSolid())
				{
					var17 = true;
				}
				else
				{
					--var130.posY;
					--var15;
				}
			}

			if (var17)
			{
				var130.setPosition(var130.posX, var130.posY, var130.posZ);
				var13 = true;
			}
		}

		if (!var13)
		{
			var130.setPosition(var7, var9, var11);
			return false;
		}
		else
		{
			var17 = true;
			return true;
		}
	}


	 private void handlePlate(int var1)
	    {
	        Minecraft var2 = Minecraft.getMinecraft();
	        EntityClientPlayerMP var3 = var2.thePlayer;
	        WorldClient var4 = var2.theWorld;

	        if (var1 == mod_slenderman.ItemSlenderSuit.itemID)
	        {
	        	if(Keyboard.isKeyDown(19) && !isTeleportKeyDown)
	        	{
	        		int newX =(int) randomXonCircle(40, 100, var3.posX);
	        		int newZ =(int) randomZonCircle(40, 100, var3.posZ);  
	        		
	        		this.teleportTo1(newX, var3.posY, newZ);
	        	}

	        }
	        
	    }
	
    private void sendBolt(int var2, int var3, int var4)
    {
        ByteArrayOutputStream var6 = new ByteArrayOutputStream();
        DataOutputStream var7 = new DataOutputStream(var6);
        int[] var8 = new int[] {2, var2, var3, var4};

        try
        {
            for (int var9 = 0; var9 < var8.length; ++var9)
            {
                var7.writeInt(var8[var9]);
            }
        }
        catch (IOException var10)
        {
            ;
        }

        Packet250CustomPayload var11 = new Packet250CustomPayload();
        var11.channel = "IronMan_C";
        var11.data = var6.toByteArray();
        var11.length = var6.size();
        PacketDispatcher.sendPacketToServer(var11);
    }



    public void tickEnd(EnumSet var1, Object ... var2) {}

    public EnumSet ticks()
    {
        return this.ticksToGet;
    }

    public String getLabel()
    {
        return "IronManClientTick";
    }
}
