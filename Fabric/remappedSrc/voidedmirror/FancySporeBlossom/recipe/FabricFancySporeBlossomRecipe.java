package voidedmirror.FancySporeBlossom.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import voidedmirror.FancySporeBlossom.FabricFancySporeBlossom;

public class FabricFancySporeBlossomRecipe extends AbstractFancySporeBlossomRecipe {
    public FabricFancySporeBlossomRecipe(Identifier id) {
        super(id);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_RECIPE;
    }
}
