package shadows.placebo.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import shadows.placebo.interfaces.IPropertyEnum;

public class StackPrimer {

	Item item = null;
	Block block;
	final int size;
	final int meta;
	IPropertyEnum prop;

	public StackPrimer(Item item, int size, int meta) {
		this.item = item;
		this.size = size;
		this.meta = meta;
	}

	public StackPrimer(Item item, int size) {
		this(item, size, 0);
	}

	public StackPrimer(Item item) {
		this(item, 1, 0);
	}

	public StackPrimer(Block block, int size, int meta) {
		this.block = block;
		this.size = size;
		this.meta = meta;
	}

	public StackPrimer(Block item, int size) {
		this(item, size, 0);
	}

	public StackPrimer(Block item) {
		this(item, 1, 0);
	}

	public StackPrimer(IPropertyEnum e) {
		prop = e;
		size = meta = 0;
	}

	public ItemStack genStack() {
		if (prop != null) return prop.getAsStack();
		return new ItemStack(item == null ? item = Item.getItemFromBlock(block) : item, size, meta);
	}

	public boolean isEmpty() {
		if (prop != null) return prop.getAsStack().isEmpty();
		return item == null || size <= 0;
	}

}
