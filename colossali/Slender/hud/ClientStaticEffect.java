package colossali.Slender.hud;

import java.util.EnumSet;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import colossali.Slender.client.ClientTickHandler;
import colossali.Slender.common.EntitySlenderMan;
import colossali.Slender.common.mod_slenderman;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientStaticEffect implements ITickHandler
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{ 
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer player = minecraft.thePlayer;
		
		if(type.equals(EnumSet.of(TickType.RENDER))) {
			
			if(minecraft.isGuiEnabled() && minecraft.inGameHasFocus)
			onRenderTick();
		}
		else if(type.equals(EnumSet.of(TickType.CLIENT))){
			GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
			if(guiScreen == null){
				onTickInGame();
			}
			else onTickInGui(guiScreen);
		}

		
		
	}

	private void onTickInGui(GuiScreen guiScreen) {
		
	}

	private void onTickInGame() {
		
	}

	private void onRenderTick() {
		if(Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().currentScreen != null || Minecraft.getMinecraft().gameSettings.keyBindPlayerList.pressed)
		{
			EntitySlenderMan.opacity = 0;
			return;
		}
		
		ItemStack helmet = Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(3);
		if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)
		{
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
			
			Tessellator t = Tessellator.instance;
			
			ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
			int width = scale.getScaledWidth();
			int height = scale.getScaledHeight();
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, EntitySlenderMan.opacity);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			
			if (ClientTickHandler.StaticLoop)
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("%blur%/slenderman/static.png"));
			else if (!ClientTickHandler.StaticLoop)
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("%blur%/slenderman/static2.png"));

			
			t.startDrawingQuads();
			t.addVertexWithUV(0.0D, (double)height, 90.0D, 0.0D, 1.0D);
			t.addVertexWithUV((double)width, (double)height, 90.0D, 1.0D, 1.0D);
			t.addVertexWithUV((double)width, 0.0D, 90.0D, 1.0D, 0.0D);
			t.addVertexWithUV(0.0D, 0.0D, 90.0D, 0.0D, 0.0D);
			t.draw();
			
			GL11.glPopAttrib();
		}

		
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel()
	{
		return "render hud tick handler";
	}

}
