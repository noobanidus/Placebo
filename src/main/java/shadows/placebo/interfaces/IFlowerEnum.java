package shadows.placebo.interfaces;

import net.minecraft.item.EnumDyeColor;

public interface IFlowerEnum extends IPropertyEnum {

	public boolean hasFlowers();

	public EnumDyeColor getColor();

	default public boolean useForRecipes() {
		return false;
	}

}
