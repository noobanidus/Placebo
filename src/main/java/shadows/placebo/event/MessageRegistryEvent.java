package shadows.placebo.event;

import com.google.common.base.Supplier;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;
import shadows.placebo.Placebo;

public class MessageRegistryEvent extends Event {

	static byte discrim = 0;

	public <T extends IMessage, K extends IMessageHandler<T, IMessage>> void registerMessage(Class<T> message, Supplier<K> handler, Side toHandle) {
		Placebo.NETWORK.registerMessage(handler.get(), message, discrim++, toHandle);
	}

}
