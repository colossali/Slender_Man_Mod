package colossali.Slender.common;
import java.util.Random;

import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class ShrineWorldGen implements IWorldGenerator 
{
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		switch (world.provider.dimensionId)
		{
		case -1: generateNether(world, random, chunkX*16, chunkZ*16);
		case 0: generateSurface(world, random, chunkX*16, chunkZ*16);
		}
	}



	public void generateSurface(World world, Random random, int blockX, int blockZ) 
	{
		Random rand = new Random();
		int nextRand = rand.nextInt(20);

		if(nextRand == 1)
		{
		for(int i = 0; i < 1; i++)
		{
			int Xcoord = blockX + random.nextInt(16);
			int Ycoord = random.nextInt(15);
			int Zcoord = blockZ + random.nextInt(16);    
			(new SlenderShrine()).generate(world, random, Xcoord, Ycoord, Zcoord);
			System.out.println("Generated");

		}
		}
	}

	private void generateNether(World world, Random random, int blockX, int blockZ) 
	{

	}

}
