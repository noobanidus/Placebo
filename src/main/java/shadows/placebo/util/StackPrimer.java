package shadows.placebo.util;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * A class that allows for a blueprint of stacks to be made.  Allows for easier lazy-loading.
 * @author Shadows
 *
 */
public class StackPrimer implements Supplier<ItemStack> {

	Item item;
	Block block;
	String name;
	final int size;
	final int meta;
	NBTTagCompound tag;

	/**
	 * Creates a StackPrimer from an Item instance.
	 * @param item The item to make the stack from.
	 * @param size Stack size.
	 * @param meta Stack metadata.
	 */
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

	/**
	 * Creates a StackPrimer from a Block instance.
	 * @param block The block to make the stack from.  Will be converted to an Item when {@link StackPrimer#getItem()} is invoked.
	 * @param size Stack size.
	 * @param meta Stack metadata.
	 */
	public StackPrimer(Block block, int size, int meta) {
		this.block = block;
		this.size = size;
		this.meta = meta;
	}

	public StackPrimer(Block block, int size) {
		this(block, size, 0);
	}

	public StackPrimer(Block block) {
		this(block, 1, 0);
	}

	/**
	 * Creates a StackPrimer from a String name.
	 * @param name The registry name of the item to make the stack from.  Will be converted to an Item when {@link StackPrimer#getItem()} is invoked.
	 * @param size Stack size.
	 * @param meta Stack metadata.
	 */
	public StackPrimer(String name, int size, int meta) {
		this.name = name;
		this.size = size;
		this.meta = meta;
	}

	public StackPrimer(String name, int size) {
		this(name, size, 0);
	}

	public StackPrimer(String name) {
		this(name, 1, 0);
	}

	@Override
	public ItemStack get() {
		ItemStack s = new ItemStack(getItem(), size, meta);
		if (tag != null) s.setTagCompound(tag.copy());
		return s;
	}

	public boolean isEmpty() {
		return item == null || size <= 0;
	}

	public Item getItem() {
		if (item == null) {
			if (block != null) item = Item.getItemFromBlock(block);
			else item = PlaceboUtil.getItemByName(name);
		}
		return item;
	}

	public int getCount() {
		return size;
	}

	public int getMeta() {
		return meta;
	}

	public StackPrimer setTag(NBTTagCompound tag) {
		this.tag = tag;
		return this;
	}

}
