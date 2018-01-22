package shadows.placebo;

import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class Proxy {

	@SuppressWarnings("deprecation")
	public String translate(String lang, Object... args) {
		return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(lang, args);
	}

	public void useRenamedMapper(Block b, String path) {

	}

	public void useRenamedMapper(Block b, String path, String append) {

	}

	public void useRenamedMapper(Block b, String path, String append, String variant) {

	}

	public void useRenamedMapper(Block b, String path, String append, Function<IBlockState, String> variant) {

	}

}
