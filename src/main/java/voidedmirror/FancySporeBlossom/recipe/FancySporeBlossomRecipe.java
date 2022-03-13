package voidedmirror.FancySporeBlossom.recipe;

import com.google.common.collect.Lists;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import voidedmirror.FancySporeBlossom.FancySporeBlossom;
import voidedmirror.FancySporeBlossom.item.FancyDyeableItem;

import java.util.ArrayList;


public class FancySporeBlossomRecipe extends SpecialCraftingRecipe {
    private static final Ingredient GLOW = Ingredient.ofItems(Items.GLOW_BERRIES);

    public FancySporeBlossomRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        ItemStack fancyDyeableItemStack = ItemStack.EMPTY;
        boolean isDyes = false;
        boolean isGlow = false;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() instanceof FancyDyeableItem) {
                if (!fancyDyeableItemStack.isEmpty()) return false;
                fancyDyeableItemStack = itemStack.copy();
                continue;
            }
            if (itemStack.getItem() instanceof DyeItem) {
                isDyes = true;
                continue;
            }
            if (GLOW.test(itemStack)) {
                if (isGlow) return false;
                isGlow = true;
                continue;
            }
            return false;
        }
        return (!fancyDyeableItemStack.isEmpty()) && (isDyes || (isGlow && !((FancyDyeableItem)fancyDyeableItemStack.getItem()).isGlowing(fancyDyeableItemStack)));
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack fancyDyeableItemStack = ItemStack.EMPTY;
        ItemStack glowItemStack = ItemStack.EMPTY;
        ArrayList<DyeItem> dyeList = Lists.newArrayList();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() instanceof FancyDyeableItem) {
                if (!fancyDyeableItemStack.isEmpty()) return ItemStack.EMPTY;
                fancyDyeableItemStack = itemStack.copy();
                continue;
            }
            if (itemStack.getItem() instanceof DyeItem dyeItem) {
                dyeList.add(dyeItem);
                continue;
            }
            if (GLOW.test(itemStack)) {
                if (!glowItemStack.isEmpty()) return ItemStack.EMPTY;
                glowItemStack = itemStack.copy();
                continue;
            }
            return ItemStack.EMPTY;
        }

        if (fancyDyeableItemStack.isEmpty()) return ItemStack.EMPTY;
        if (!glowItemStack.isEmpty()) {
            if (((FancyDyeableItem)fancyDyeableItemStack.getItem()).isGlowing(fancyDyeableItemStack)) {
                return ItemStack.EMPTY;
            }
            ((FancyDyeableItem)fancyDyeableItemStack.getItem()).setGlowing(fancyDyeableItemStack);
        }
        if (!dyeList.isEmpty()) {
            return FancyDyeableItem.blendAndSetColor(fancyDyeableItemStack, dyeList);
        }
        return fancyDyeableItemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FancySporeBlossom.FANCY_SPORE_BLOSSOM_RECIPE;
    }
}