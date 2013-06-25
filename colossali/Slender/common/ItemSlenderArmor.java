package colossali.Slender.common;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IArmorTextureProvider;



public class ItemSlenderArmor extends ItemArmor implements IArmorTextureProvider
{
	
	private String itemTexture;


    public ItemSlenderArmor(int var1, EnumArmorMaterial var2, int var3, int var4, String texture)
    {
        super(var1, var2, 0, var4);

        this.setCreativeTab(CreativeTabs.tabCombat);
        itemTexture = texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
    	
             itemIcon = iconRegister.registerIcon("Slender:"+itemTexture);
             
    }
    
    @Override
    public String getArmorTextureFile(ItemStack itemstack)
    {
                    if(itemstack.itemID == mod_slenderman.ItemSlenderMask.itemID || itemstack.itemID == mod_slenderman.ItemSlenderSuit.itemID || itemstack.itemID == mod_slenderman.ItemSlenderShoes.itemID)
                    {
                                    return "/slenderman/slender_1.png";
                    }
                    if(itemstack.itemID == mod_slenderman.ItemSlenderPants.itemID)
                    {
                                    return "/slenderman/slender_2.png";
                    }
                   

                    else
                    {
                    	return null;
                    }
    }


}
