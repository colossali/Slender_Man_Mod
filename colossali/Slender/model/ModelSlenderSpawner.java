package colossali.Slender.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSlenderSpawner extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer Head;
    ModelRenderer Spinny;
  
  public ModelSlenderSpawner()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(-5F, 22F, -5F, 10, 2, 10);
      Base.setRotationPoint(0F, 0F, 0F);
      Base.setTextureSize(64, 32);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      Head = new ModelRenderer(this, 0, 12);
      Head.addBox(-2F, 19F, -2F, 4, 5, 4);
      Head.setRotationPoint(0F, -2F, 0F);
      Head.setTextureSize(64, 32);
      Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      Spinny = new ModelRenderer(this, 16, 12);
      Spinny.addBox(-5F, 24F, -5F, 10, 3, 10);
      Spinny.setRotationPoint(0F, -5F, 0F);
      Spinny.setTextureSize(64, 32);
      Spinny.mirror = true;
      setRotation(Spinny, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Base.render(f5);
    Head.render(f5);
    Spinny.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;

  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
    
  }

}
