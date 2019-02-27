package shadows.placebo.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import shadows.placebo.client.IHasModel;
import shadows.placebo.util.PlaceboUtil;

public class ItemBigEnum<T extends IStringSerializable> extends Item implements IHasModel {

	public final T[] values;

	public ItemBigEnum(String name, T[] values) {
		setHasSubtypes(true);
		this.values = values;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) for (int i = 0; i < values.length; i++)
			items.add(new ItemStack(this, 1, i));
	}

	@Override
	public void initModels(ModelRegistryEvent ev) {
		for (int i = 0; i < values.length; i++)
			PlaceboUtil.sMRL("items", this, i, "item=" + values[i].getName());
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return "item." + this.getRegistryName().getNamespace() + "." + values[stack.getMetadata() % values.length].getName();
	}

}
