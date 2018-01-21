package shadows.placebo.interfaces;

import net.minecraft.world.gen.feature.WorldGenerator;

public interface ITreeEnum extends IPropertyEnum {

	public WorldGenerator getTreeGen();

	public void setTreeGen(WorldGenerator k);

}
