package colossali.Slender.common;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemChildSoul extends ItemFood
{
    protected ItemChildSoul(int i, int j, boolean B)
    {
    	super(i, j, B);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
    	
             itemIcon = iconRegister.registerIcon("Slender:childsoul");
             
    }
    
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
    entityPlayer.getFoodStats().addStats(this);
                    world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
    itemStack.stackSize--;
            
    entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 20 * 20, 6));
    entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20 * 20, 2));
    entityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 10 * 20, 13));

    return itemStack;
    }
    
}

