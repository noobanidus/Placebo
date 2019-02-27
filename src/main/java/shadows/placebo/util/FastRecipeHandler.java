package shadows.placebo.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import shadows.placebo.Placebo;

public class FastRecipeHandler {

	public static void enableFastShapeless() {
		Placebo.LOGGER.info("Beginning replacement of all shapeless recipes...");
		Placebo.LOGGER.info("Expect log spam from FML!");
		List<IRecipe> fastRecipes = new ArrayList<>();
		for (IRecipe r : ForgeRegistries.RECIPES) {
			if (r.getClass() == ShapelessRecipes.class || r.getClass() == ShapelessOreRecipe.class) {
				FastShapelessRecipe res = new FastShapelessRecipe(r.getGroup(), r.getRecipeOutput(), r.getIngredients());
				res.setRegistryName(r.getRegistryName());
				fastRecipes.add(res);
			}
		}
		for (IRecipe r : fastRecipes)
			ForgeRegistries.RECIPES.register(r);
		Placebo.LOGGER.info("Successfully replaced {} recipes with fast recipes.", fastRecipes.size());
	}

}
