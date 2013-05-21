package colossali.Slender.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.src.*;

public class ModelSlenderman extends ModelBase
{
  //fields
    ModelRenderer FootLeft;
    ModelRenderer LegLeft1;
    ModelRenderer LeftLeg2;
    ModelRenderer FootRight;
    ModelRenderer LegRight1;
    ModelRenderer LegRight2;
    ModelRenderer Torso;
    ModelRenderer Chest;
    ModelRenderer Head;
    ModelRenderer LA1;
    ModelRenderer LA2;
    ModelRenderer H1;
    ModelRenderer h2;
    ModelRenderer h3;
    ModelRenderer h4;
    ModelRenderer h5;
    ModelRenderer h6;
    ModelRenderer RA1;
    ModelRenderer RA2;
    ModelRenderer h7;
    ModelRenderer h8;
    ModelRenderer h9;
    ModelRenderer j1;
    ModelRenderer j2;
    ModelRenderer j3;

    
  public ModelSlenderman()
  {
    textureWidth = 64;
    textureHeight = 32;
    
     this.FootLeft = new ModelRenderer(this, 50, 26);
     this.FootLeft.addBox(-1F, -1F, -3F, 2, 2, 4);
     this.FootLeft.setRotationPoint(-1.5F, 23F, -4F);
     this.FootLeft.setTextureSize(64, 32);
     this.FootLeft.mirror = true;
     this.setRotation(FootLeft, 0F, 0F, 0F);
     this.LegLeft1 = new ModelRenderer(this, 56, 13);
     this.LegLeft1.addBox(-1F, 0F, -1.5F, 2, 10, 2);
     this.LegLeft1.setRotationPoint(-1.5F, 13F, -4F);
     this.LegLeft1.setTextureSize(64, 32);
     this.LegLeft1.mirror = true;
     this.setRotation(LegLeft1, 0F, 0F, 0F);
     this.LeftLeg2 = new ModelRenderer(this, 47, 5);
     this.LeftLeg2.addBox(-1F, 0F, -2F, 2, 13, 2);
     this.LeftLeg2.setRotationPoint(-1.5F, 0.4F, -2F);
     this.LeftLeg2.setTextureSize(64, 32);
     this.LeftLeg2.mirror = true;
     this.setRotation(LeftLeg2, -0.1115358F, 0F, 0F);
     this.FootRight = new ModelRenderer(this, 50, 26);
     this.FootRight.addBox(-1F, 0F, -3F, 2, 2, 4);
     this.FootRight.setRotationPoint(1.5F, 22F, -4F);
     this.FootRight.setTextureSize(64, 32);
     this.FootRight.mirror = true;
     this.setRotation(FootRight, 0F, 0F, 0F);
     this.LegRight1 = new ModelRenderer(this, 56, 13);
     this.LegRight1.addBox(-1F, 0F, -1.5F, 2, 10, 2);
     this.LegRight1.setRotationPoint(1.5F, 13F, -4F);
     this.LegRight1.setTextureSize(64, 32);
     this.LegRight1.mirror = true;
     this.setRotation(LegRight1, 0F, 0F, 0F);
     this.LegRight2 = new ModelRenderer(this, 47, 6);
     this.LegRight2.addBox(-1F, 0F, -2F, 2, 12, 2);
     this.LegRight2.setRotationPoint(1.5F, 1F, -2F);
     this.LegRight2.setTextureSize(64, 32);
     this.LegRight2.mirror = true;
     this.setRotation(LegRight2, -0.1116138F, 0F, 0F);
     this.Torso = new ModelRenderer(this, 25, 19);
     this.Torso.addBox(-3F, -7F, -2F, 6, 8, 4);
     this.Torso.setRotationPoint(0F, 0F, -3F);
     this.Torso.setTextureSize(64, 32);
     this.Torso.mirror = true;
     this.setRotation(Torso, 0.0743572F, 0F, 0F);
     this.Chest = new ModelRenderer(this, 2, 17);
     this.Chest.addBox(-3F, -8F, -2F, 6, 10, 4);
     this.Chest.setRotationPoint(0F, -8F, -4F);
     this.Chest.setTextureSize(64, 32);
     this.Chest.mirror = true;
     this.setRotation(Chest, 0.2602503F, 0F, 0F);
     this.Head = new ModelRenderer(this, 7, 3);
     this.Head.addBox(-2F, -2F, -2F, 4, 5, 4);
     this.Head.setRotationPoint(0F, -19F, -8F);
     this.Head.setTextureSize(64, 32);
     this.Head.mirror = true;
     this.setRotation(Head, 0.2974289F, 0F, 0F);
     this.LA1 = new ModelRenderer(this, 36, 0);
     this.LA1.addBox(0F, -1F, -1F, 2, 12, 2);
     this.LA1.setRotationPoint(3F, -15F, -5F);
     this.LA1.setTextureSize(64, 32);
     this.LA1.mirror = true;
     this.setRotation(LA1, 0.0850557F, 0.185895F, -0.1069887F);
     this.LA2 = new ModelRenderer(this, 27, 0);
     this.LA2.addBox(1F, 10F, 0F, 2, 12, 2);
     this.LA2.setRotationPoint(3F, -15F, -5F);
     this.LA2.setTextureSize(64, 32);
     this.LA2.mirror = true;
     this.setRotation(LA2, 0.0326377F, 0.4332256F, 0.0151844F);
     this.H1 = new ModelRenderer(this, 0, 0);
     this.H1.addBox(0F, 0F, -1F, 1, 5, 1);
     this.H1.setRotationPoint(5F, 6F, -4F);
     this.H1.setTextureSize(64, 32);
     this.H1.mirror = true;
     this.setRotation(H1, -0.1115358F, -0.1487144F, -0.1858931F);
     this.h2 = new ModelRenderer(this, 0, 0);
     this.h2.addBox(0.75F, 4F, -2F, 1, 4, 1);
     this.h2.setRotationPoint(5F, 6F, -4F);
     this.h2.setTextureSize(64, 32);
     this.h2.mirror = true;
     this.setRotation(h2, 0.0743572F, -0.1487144F, -0.0371786F);
     this.h3 = new ModelRenderer(this, 0, 0);
     this.h3.addBox(-1F, 0F, -1F, 1, 5, 1);
     this.h3.setRotationPoint(5F, 6F, -4F);
     this.h3.setTextureSize(64, 32);
     this.h3.mirror = true;
     this.setRotation(h3, 0.2230717F, -1.449966F, 0.0371786F);
     this.h4 = new ModelRenderer(this, 0, 0);
     this.h4.addBox(-1F, 4F, -1F, 1, 4, 1);
     this.h4.setRotationPoint(5F, 6F, -4F);
     this.h4.setTextureSize(64, 32);
     this.h4.mirror = true;
     this.setRotation(h4, 0F, -0.1858931F, 0.0371786F);
     this.h5 = new ModelRenderer(this, 0, 0);
     this.h5.addBox(0F, 4F, 0F, 1, 4, 1);
     this.h5.setRotationPoint(5F, 6F, -4F);
     this.h5.setTextureSize(64, 32);
     this.h5.mirror = true;
     this.setRotation(h5, 0F, 0F, 0F);
     this.h6 = new ModelRenderer(this, 0, 0);
     this.h6.addBox(0F, 0F, 0F, 1, 4, 1);
     this.h6.setRotationPoint(5F, 6F, -4F);
     this.h6.setTextureSize(64, 32);
     this.h6.mirror = true;
     this.setRotation(h6, 0F, 0F, 0F);
     this.RA1 = new ModelRenderer(this, 36, 0);
     this.RA1.addBox(-2F, -1F, -1F, 2, 12, 2);
     this.RA1.setRotationPoint(-3F, -15F, -5F);
     this.RA1.setTextureSize(64, 32);
     this.RA1.mirror = true;
     this.setRotation(RA1, 0.0743572F, -0.1858931F, 0.1069918F);
     this.RA2 = new ModelRenderer(this, 27, 0);
     this.RA2.addBox(-3F, 10F, 0F, 2, 12, 2);
     this.RA2.setRotationPoint(-3F, -15F, -5F);
     this.RA2.setTextureSize(64, 32);
     this.RA2.mirror = true;
     this.setRotation(RA2, 0.0326346F, -0.4332341F, -0.0151813F);
     this.h7 = new ModelRenderer(this, 0, 0);
     this.h7.addBox(1F, 0F, -1F, 1, 5, 1);
     this.h7.setRotationPoint(-6F, 6F, -4F);
     this.h7.setTextureSize(64, 32);
     this.h7.mirror = true;
     this.setRotation(h7, -0.0371786F, -0.3717861F, -0.0743572F);
     this.h8 = new ModelRenderer(this, 0, 0);
     this.h8.addBox(0F, 3F, 0F, 1, 4, 1);
     this.h8.setRotationPoint(-6F, 6F, -4F);
     this.h8.setTextureSize(64, 32);
     this.h8.mirror = true;
     this.setRotation(h8, 0F, 0F, 0F);
     this.h9 = new ModelRenderer(this, 0, 0);
     this.h9.addBox(0F, 0F, -0.7333333F, 1, 5, 1);
     this.h9.setRotationPoint(-6F, 6F, -4F);
     this.h9.setTextureSize(64, 32);
     this.h9.mirror = true;
     this.setRotation(h9, -0.3346075F, 0.2602503F, 0.1115358F);
     this.j1 = new ModelRenderer(this, 0, 0);
     this.j1.addBox(-1.5F, 4F, -2F, 1, 4, 1);
     this.j1.setRotationPoint(-6F, 6F, -4F);
     this.j1.setTextureSize(64, 32);
     this.j1.mirror = true;
     this.setRotation(j1, -0.1115358F, 0.2230717F, -0.1858931F);
     this.j2 = new ModelRenderer(this, 0, 0);
     this.j2.addBox(1.5F, 4F, -1.5F, 1, 4, 1);
     this.j2.setRotationPoint(-6F, 6F, -4F);
     this.j2.setTextureSize(64, 32);
     this.j2.mirror = true;
     this.setRotation(j2, 0.0743572F, -0.4833219F, 0.0743572F);
     this.j3 = new ModelRenderer(this, 0, 0);
     this.j3.addBox(0F, 0F, 0F, 1, 4, 1);
     this.j3.setRotationPoint(-6F, 6F, -4F);
     this.j3.setTextureSize(64, 32);
     this.j3.mirror = true;
     this.setRotation(j3, 0F, 0F, 0F);

  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    this.setRotationAngles(f, f1, f2, f3, f4, f5);
    this.FootLeft.render(f5);
    this.LegLeft1.render(f5);
    this.LeftLeg2.render(f5);
    this.FootRight.render(f5);
    this.LegRight1.render(f5);
    this.LegRight2.render(f5);
    this.Torso.render(f5);
    this.Chest.render(f5);
    this.Head.render(f5);
    this.LA1.render(f5);
    this.LA2.render(f5);
    this.H1.render(f5);
    this.h2.render(f5);
    this.h3.render(f5);
    this.h4.render(f5);
    this.h5.render(f5);
    this.h6.render(f5);
    this.RA1.render(f5);
    this.RA2.render(f5);
    this.h7.render(f5);
    this.h8.render(f5);
    this.h9.render(f5);
    this.j1.render(f5);
    this.j2.render(f5);
    this.j3.render(f5);

  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  float var7;
  float var8;
  
   public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6)
    {
        super.setRotationAngles(var1, var2, var3, var4, var5, var6, null);
        this.Head.rotateAngleY = var4 / (180F / (float)Math.PI);
        this.Head.rotateAngleX = var5 / (180F / (float)Math.PI);
        

        
  }

}