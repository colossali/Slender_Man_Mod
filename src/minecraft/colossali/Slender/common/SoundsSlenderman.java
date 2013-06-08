package colossali.Slender.common;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPool;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
public class SoundsSlenderman
{
@ForgeSubscribe
public void onSound(SoundLoadEvent event)
{
         try
         {
                 String [] soundFiles = {
                 //these are your sounds
                 "scare.ogg",
                 "static.ogg",
                 "attack.ogg",
                 "death.ogg",
                 "close.ogg",
                 "living.ogg",
                 "lights.ogg",//this was my music disc that didn't work
                 };
                 for (int i = 0; i < soundFiles.length; i++){
                 event.manager.soundPoolSounds.addSound(soundFiles[i], mod_slenderman.class.getResource("/slenderman/sounds/" + soundFiles[i]));//the string "/sounds/" is your sound folder in the .jar or .zip
                 }
        
         }
         catch (Exception e)
         {
                 System.err.println("Failed to register one or more sounds.");
         }
}
}