package shadows.placebo.event;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class LoaderEvents {

	public static class PlaceboPreInit extends Event {
		public FMLPreInitializationEvent ev;

		public PlaceboPreInit(FMLPreInitializationEvent ev) {
			this.ev = ev;
		}
	}

	public static class PlaceboInit extends Event {
		public FMLInitializationEvent ev;

		public PlaceboInit(FMLInitializationEvent ev) {
			this.ev = ev;
		}
	}

	public static class PlaceboPostInit extends Event {
		public FMLPostInitializationEvent ev;

		public PlaceboPostInit(FMLPostInitializationEvent ev) {
			this.ev = ev;
		}
	}

}
