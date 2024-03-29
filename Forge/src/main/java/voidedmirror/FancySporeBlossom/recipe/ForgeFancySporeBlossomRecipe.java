package voidedmirror.FancySporeBlossom.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

import org.jetbrains.annotations.NotNull;

import voidedmirror.FancySporeBlossom.ForgeFancySporeBlossom;

public class ForgeFancySporeBlossomRecipe extends AbstractFancySporeBlossomRecipe {
    public ForgeFancySporeBlossomRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ForgeFancySporeBlossom.FANCY_SPORE_BLOSSOM_RECIPE.get();
    }
}
