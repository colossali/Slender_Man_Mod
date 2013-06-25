
package colossali.Slender.render;

import org.lwjgl.opengl.GL11;

import colossali.Slender.mobspawner.TileEntitySlenderSpawner;
import colossali.Slender.model.ModelSlenderSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;

public class RenderSlenderSpawnerItem implements IItemRenderer {

private ModelSlenderSpawner Model;

public RenderSlenderSpawnerItem() {

Model = new ModelSlenderSpawner();
}

@Override
public boolean handleRenderType(ItemStack item, ItemRenderType type) {

return true;
}

@Override
public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {

return true;
}

@Override
public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
TileEntityRenderer.instance.renderTileEntityAt(new TileEntitySlenderSpawner(), 0.0D, 0.0D, 0.0D, 0.0F);
}
}