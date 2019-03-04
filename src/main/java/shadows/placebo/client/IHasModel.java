package shadows.placebo.client;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import shadows.placebo.util.PlaceboUtil;

public interface IHasModel {

	default public void onModelRegister(ModelRegistryEvent e) {
		if (this instanceof Item) PlaceboUtil.sMRL((Item) this, 0, "inventory");
		else if (this instanceof Block) PlaceboUtil.sMRL((Block) this, 0, "inventory");
		else throw new IllegalStateException("Default impl invoked for something that was not a Block or Item. This is not allowed.");
	}

}
