package shadows.placebo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import shadows.placebo.Placebo;

/**
 * This is a helper class for the utilization of in-code recipes.
 * This class should be instantiated, subclassed, and subscribed to the {@link MinecraftForge#EVENT_BUS}
 * @author Shadows
 *
 */
public abstract class RecipeHelper {

	private int j = 0;
	private final String modid;
	private final String modname;
	private final List<IRecipe> recipes = new ArrayList<>();

	public RecipeHelper(String modid, String modname) {
		this.modid = modid;
		this.modname = modname;
	}

	@SubscribeEvent
	public final void register(IForgeRegistry<IRecipe> reg) {
		addRecipes();
		reg.registerAll(recipes.toArray(new IRecipe[0]));
	}

	/**
	 * This method is called directly before recipe registration.
	 * Add recipes from inside this method, and nowhere else.
	 */
	public abstract void addRecipes();

	/**
	 * This adds a recipe to the list of crafting recipes.  Fails if the recipe does not have a registry name.
	 */
	public void addRecipe(IRecipe rec) {
		if (rec.getRegistryName() == null) Placebo.LOGGER.error("The mod {} has attempted to add a recipe with no name to a Recipe Helper", modname);
		else recipes.add(rec);
	}

	/**
	 * This adds a recipe to the list of crafting recipes.  Auto-generates a recipe name based upon a static recipe index counter.
	 */
	public void addRecipe(int j, IRecipe rec) {
		addRecipe(rec.setRegistryName(new ResourceLocation(modid, "recipe" + j)));
	}

	/**
	 * Adds a shapeless recipe with X output using an array of inputs. Use Strings for OreDictionary support. This array is not ordered.
	 * Valid inputs are {@link Ingredient}, {@link Item}, {@link String}, {@link ItemStack} and {@link Block}
	 */
	public void addShapeless(Object output, Object... inputs) {
		addRecipe(j++, new FastShapelessRecipe(modid + ":" + j, makeStack(output), createInput(false, inputs)));
	}

	/**
	 * Adds a shapeless recipe with X output on a crafting grid that is W x H, using an array of inputs.  Use null for nothing, use Strings for OreDictionary support, this array must have a length of width * height.
	 * This array is ordered, and items must follow from left to right, top to bottom of the crafting grid.
	 */
	public void addShaped(Object output, int width, int height, Object... input) {
		addRecipe(j++, makeShaped(makeStack(output), width, height, createInput(true, input)));
	}

	/**
	 * Generates a {@link ShapedRecipes} with a specific width and height. The Object... is the ingredients, in order from left to right, top to bottom.  Uses a custom group.
	 */
	public ShapedRecipes makeShaped(ItemStack output, int l, int w, NonNullList<Ingredient> input) {
		if (l * w != input.size()) throw new UnsupportedOperationException("The mod " + modname + " attempted to create an invalid shaped recipe.");
		return new ShapedRecipes(null, l, w, input, output);
	}

	/**
	 * Creates a List<Ingredient> based on an Object[].  Valid types are {@link String}, {@link ItemStack}, {@link Ingredient}, {@link Item}, and {@link Block}.
	 * If shaped is true, other types (usually null) will be converted to {@link Ingredient#EMPTY}
	 */
	public NonNullList<Ingredient> createInput(boolean shaped, Object... input) {
		NonNullList<Ingredient> inputL = NonNullList.create();
		for (int i = 0; i < input.length; i++) {
			Object k = input[i];
			if (k instanceof String) inputL.add(i, CachedOreIngredient.create((String) k));
			else if (k instanceof ItemStack && !((ItemStack) k).isEmpty()) inputL.add(i, CachedIngredient.create((ItemStack) k));
			else if (k instanceof IForgeRegistryEntry) inputL.add(i, CachedIngredient.create(makeStack((IForgeRegistryEntry<?>) k)));
			else if (k instanceof Ingredient) inputL.add(i, (Ingredient) k);
			else if (shaped) inputL.add(i, Ingredient.EMPTY);
			else throw new UnsupportedOperationException("The mod " + modname + " attempted to create an invalid shapeless recipe.");
		}
		return inputL;
	}

	/**
	 * Adds a shapeless recipe with one output and x inputs, all inputs are the same.
	 */
	public void addSimpleShapeless(Object output, Object input, int numInputs) {
		addShapeless(output, NonNullList.withSize(numInputs, makeStack(input)));
	}

	/**
	 * Makes an ItemStack from something that could be an ItemStack.
	 * @param thing The thing to convert.  Must be either {@link Item}, {@link Block}, {@link StackPrimer} or {@link ItemStack}
	 * @param size How big the stack should be.  Ignored if thing is a stack.
	 * @param meta Stack metadata.  Ignored if thing is a stack.
	 * @return An itemstack, either new or casted if thing was a stack.
	 */
	public static ItemStack makeStack(Object thing, int size, int meta) {
		if (thing instanceof Item) return new ItemStack((Item) thing, size, meta);
		if (thing instanceof Block) return new ItemStack((Block) thing, size, meta);
		if (thing instanceof StackPrimer) return ((StackPrimer) thing).genStack();
		return (ItemStack) thing;
	}

	/**
	 * Makes an ItemStack from something that could be an ItemStack.
	 * @param thing The thing to convert.  Must be either {@link Item}, {@link Block}, {@link StackPrimer} or {@link ItemStack}
	 * @param size How big the stack should be.  Ignored if thing is a stack.
	 * @return An itemstack, either new or casted if thing was a stack.
	 */
	public static ItemStack makeStack(Object thing, int size) {
		return makeStack(thing, size, 0);
	}

	/**
	 * Makes an ItemStack from something that could be an ItemStack.
	 * @param thing The thing to convert.  Must be either {@link Item}, {@link Block}, {@link StackPrimer} or {@link ItemStack}
	 * @return An itemstack, either new or casted if thing was a stack.
	 */
	public static ItemStack makeStack(Object thing) {
		return makeStack(thing, 1, 0);
	}

	/**
	 * Cache class to avoid creating extra OreIngredient instances.
	 * @author Shadows
	 *
	 */
	public static class CachedOreIngredient extends OreIngredient {

		public static HashMap<String, CachedOreIngredient> ing = new HashMap<>();

		private CachedOreIngredient(String ore) {
			super(ore);
			ing.put(ore, this);
		}

		public static CachedOreIngredient create(String ore) {
			CachedOreIngredient coi = ing.get(ore);
			return coi != null ? coi : new CachedOreIngredient(ore);
		}

	}

	/**
	 * Cache class to avoid creating extra Ingredient instances.  Only caches simple (single stack, no nbt) ingredients.
	 * @author Shadows
	 *
	 */
	public static class CachedIngredient extends Ingredient {

		public static Int2ObjectMap<CachedIngredient> ing = new Int2ObjectOpenHashMap<>();

		private CachedIngredient(ItemStack... matches) {
			super(matches);
			if (matches.length == 1) ing.put(RecipeItemHelper.pack(matches[0]), this);
		}

		public static CachedIngredient create(ItemStack... matches) {
			if (matches.length == 1) {
				CachedIngredient coi = ing.get(RecipeItemHelper.pack(matches[0]));
				return coi != null ? coi : new CachedIngredient(matches);
			} else return new CachedIngredient(matches);
		}

	}

}