package shadows.placebo.recipe;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IIngredientSerializer;

public class TagIngredient extends Ingredient {

	public static final IIngredientSerializer<TagIngredient> SERIALIZER = new IIngredientSerializer<TagIngredient>() {

		@Override
		public TagIngredient parse(PacketBuffer buffer) {
			ResourceLocation tag = new ResourceLocation(buffer.readString(32767));
			return new TagIngredient(tag);
		}

		@Override
		public TagIngredient parse(JsonObject json) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void write(PacketBuffer buffer, TagIngredient ingredient) {
			buffer.writeString(ingredient.tag.getId().toString());
		}

	};

	protected ResourceLocation tagId;
	protected Tag<Item> tag;
	protected ItemStack[] stacks;

	public TagIngredient(Tag<Item> tag) {
		super(Stream.of(new Ingredient.TagList(tag)));
		this.tag = tag;
		this.stacks = new ItemStack[0];
	}

	public TagIngredient(ResourceLocation tag) {
		this(new ItemTags.Wrapper(tag));
	}

	@Override
	public boolean test(ItemStack stack) {
		return tag.contains(stack.getItem());
	}

	@Override
	public ItemStack[] getMatchingStacks() {
		if (tag.getAllElements().size() != stacks.length) {
			stacks = tag.getAllElements().stream().map(ItemStack::new).collect(Collectors.toList()).toArray(new ItemStack[0]);
		}
		return stacks;
	}

	@Override
	public boolean hasNoMatchingItems() {
		return tag.getAllElements().isEmpty();
	}

	@Override
	public IIngredientSerializer<? extends Ingredient> getSerializer() {
		return SERIALIZER;
	}

}
