package shadows.placebo.interfaces;

import java.util.Locale;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Only implement this on an enum.
 * @author Shadows
 */
public interface IPropertyEnum extends IStringSerializable {

	@Override
	default public String getName() {
		return ((Enum<?>) this).name().toLowerCase(Locale.ENGLISH);
	}

	/**
	 * @return The value of this enum constant as an ItemStack, or ItemStack.EMPTY if invalid.
	 */
	public ItemStack getAsStack();

	/**
	 * Util method to return getAsStack with specified size.
	 */
	default public ItemStack getAsStack(int size) {
		ItemStack s = getAsStack();
		s.setCount(size);
		return s;
	}

	/**
	 * @return The value of this enum constant as an IBlockState, or null if invalid.
	 */
	public IBlockState getAsState();

	/**
	 * Allows an enum to handle an IForgeRegistryEntry for storage.  Useful for getAsStack/getAsState
	 * @param blockOrItem A {@link net.minecraft.block.Block} or {@link net.minecraft.item.Item}
	 */
	default public void set(IForgeRegistryEntry<?> blockOrItem) {
	}

}
