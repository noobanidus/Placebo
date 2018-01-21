package shadows.placebo.block.base;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import shadows.placebo.Placebo;
import shadows.placebo.interfaces.IPropertyEnum;
import shadows.placebo.itemblock.ItemBlockEnum;
import shadows.placebo.registry.RegistryInformation;
import shadows.placebo.util.PlaceboUtil;

public abstract class BlockEnum<E extends Enum<E> & IPropertyEnum> extends BlockBasic implements IEnumBlock<E> {

	protected final E type;

	public BlockEnum(String name, Material material, SoundType sound, float hardness, float resistance, E type, RegistryInformation info) {
		super(name, material, hardness, resistance, info);
		this.setSoundType(sound);
		this.type = type;
		this.setDefaultState(getBlockState().getBaseState());
		type.set(this);
	}

	@Override
	public ItemBlock createItemBlock() {
		return new ItemBlockEnum<>(this);
	}

	@Override
	public void initModels(ModelRegistryEvent e) {
		PlaceboUtil.sMRL("blocks", this, 0, "type=" + type.getName());
		Placebo.PROXY.useRenamedMapper(this, "blocks", "", "type=" + type.getName());
	}

	@Override
	public E getType() {
		return type;
	}


	@Override
	public IBlockState getStateFor(E e) {
		return this.getDefaultState();
	}

}
