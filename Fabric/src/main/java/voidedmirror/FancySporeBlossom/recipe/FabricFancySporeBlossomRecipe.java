package voidedmirror.FancySporeBlossom.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import voidedmirror.FancySporeBlossom.FabricFancySporeBlossom;

public class FabricFancySporeBlossomRecipe extends AbstractFancySporeBlossomRecipe {
    public FabricFancySporeBlossomRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_RECIPE;
    }
}
