package shadows.placebo.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageButtonClick implements IMessage {

	byte button;

	public MessageButtonClick(int button) {
		this.button = (byte) button;
	}

	public MessageButtonClick() {

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		button = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(button);
	}

	public int getButton() {
		return button;
	}

	public static class ButtonClickHandler implements IMessageHandler<MessageButtonClick, IMessage> {

		@Override
		public MessageButtonClick onMessage(MessageButtonClick message, MessageContext ctx) {
			FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
				Container c = ctx.getServerHandler().player.openContainer;
				if (c instanceof IClickableContainer) {
					((IClickableContainer) c).handleClick(message.getButton());
				}
			});
			return null;
		}

	}

}
