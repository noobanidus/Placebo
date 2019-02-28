package shadows.placebo;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import shadows.placebo.data.IPostInitUpdate;
import shadows.placebo.event.MessageRegistryEvent;
import shadows.placebo.loot.PlaceboLootSystem;
import shadows.placebo.net.MessageButtonClick;
import shadows.placebo.net.MessageButtonClick.ButtonClickHandler;
import shadows.placebo.util.FastRecipeHandler;
import shadows.placebo.util.RecipeHelper.CachedIngredient;
import shadows.placebo.util.RecipeHelper.CachedOreIngredient;

@Mod(modid = Placebo.MODID, name = Placebo.MODNAME, version = Placebo.VERSION, acceptableRemoteVersions = "*")
public class Placebo {

	public static final String MODID = "placebo";
	public static final String MODNAME = "Placebo";
	public static final String VERSION = "2.0.0";

	public static final List<IPostInitUpdate> UPDATES = new LinkedList<>();

	@SidedProxy(serverSide = "shadows.placebo.Proxy", clientSide = "shadows.placebo.ClientProxy")
	public static Proxy PROXY;

	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	static boolean fastRecipes = false;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		fastRecipes = config.getBoolean("Fast Shapeless Recipes", "general", true, "If placebo will replace all ShapelessRecipes and ShapelessOreRecipes with FastShapelessRecipes.");
		if (config.hasChanged()) config.save();
		MinecraftForge.EVENT_BUS.register(new PlaceboLootSystem());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.post(new MessageRegistryEvent());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		for (IPostInitUpdate i : UPDATES)
			i.postInit(e);
		UPDATES.clear();
		if (fastRecipes) FastRecipeHandler.enableFastShapeless();
		CachedOreIngredient.ing = null;
		CachedIngredient.ing = null;
	}

	@SubscribeEvent
	public void messages(MessageRegistryEvent e) {
		e.registerMessage(MessageButtonClick.class, ButtonClickHandler::new, Side.SERVER);
	}

}
