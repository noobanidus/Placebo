package shadows.placebo.util;

import java.util.function.Supplier;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {

	Supplier<ItemStack> icon;

	public CreativeTab(String modid, Supplier<ItemStack> icon) {
		super(modid);
	}

	@Override
	public ItemStack createIcon() {
		return icon.get();
	}
}
