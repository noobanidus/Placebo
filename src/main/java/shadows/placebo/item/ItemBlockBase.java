package shadows.placebo.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * Helper ItemBlock that auto-sets registry name from the passed block.
 * @author Shadows
 *
 */
public class ItemBlockBase extends ItemBlock {

	public ItemBlockBase(Block block) {
		super(block);
		setRegistryName(block.getRegistryName());
	}

}
