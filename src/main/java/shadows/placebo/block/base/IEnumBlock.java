package shadows.placebo.block.base;

import shadows.placebo.interfaces.IItemBlock;
import shadows.placebo.interfaces.IPropertyEnum;
import shadows.placebo.interfaces.ISpecialPlacement;

public interface IEnumBlock<E extends Enum<E> & IPropertyEnum> extends IEnumBlockAccess<E>, ISpecialPlacement, IItemBlock {

	public E getType();
	
}
