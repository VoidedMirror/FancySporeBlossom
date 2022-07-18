package voidedmirror.FancySporeBlossom.recipe;

import com.google.common.collect.Lists;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import voidedmirror.FancySporeBlossom.CommonClass;
import voidedmirror.FancySporeBlossom.item.FancyDyeableItem;

import java.util.ArrayList;


public abstract class AbstractFancySporeBlossomRecipe extends CustomRecipe {
    private static final Ingredient GLOW = Ingredient.of(Items.GLOW_BERRIES);

    public AbstractFancySporeBlossomRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingContainer container, @NotNull Level level) {
        ItemStack fancyDyeableItemStack = ItemStack.EMPTY;
        boolean isDyes = false;
        boolean isGlow = false;
        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemStack = container.getItem(i);
            if (itemStack.isEmpty()) { continue; }
            if (itemStack.getItem() instanceof FancyDyeableItem) {
                if (!fancyDyeableItemStack.isEmpty()) { return false; }
                fancyDyeableItemStack = itemStack.copy();
                continue;
            }
            if (itemStack.getItem() instanceof DyeItem) {
                isDyes = true;
                continue;
            }
            if (GLOW.test(itemStack)) {
                if (isGlow) { return false; }
                isGlow = true;
                continue;
            }
            return false;
        }
        return (!fancyDyeableItemStack.isEmpty()) && (isDyes || (isGlow && !((FancyDyeableItem)fancyDyeableItemStack.getItem()).isGlowing(fancyDyeableItemStack)));
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer container) {
        ItemStack fancyDyeableItemStack = ItemStack.EMPTY;
        ItemStack glowItemStack = ItemStack.EMPTY;
        ArrayList<DyeItem> dyeList = Lists.newArrayList();
        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemStack = container.getItem(i);
            if (itemStack.isEmpty()) { continue; }
            if (itemStack.getItem() instanceof FancyDyeableItem) {
                if (!fancyDyeableItemStack.isEmpty()) { return ItemStack.EMPTY; }
                fancyDyeableItemStack = itemStack.copy();
                continue;
            }
            if (itemStack.getItem() instanceof DyeItem dyeItem) {
                dyeList.add(dyeItem);
                continue;
            }
            if (GLOW.test(itemStack)) {
                if (!glowItemStack.isEmpty()) { return ItemStack.EMPTY; }
                glowItemStack = itemStack.copy();
                continue;
            }
            return ItemStack.EMPTY;
        }
        if (fancyDyeableItemStack.isEmpty()) { return ItemStack.EMPTY; }
        boolean isGlowing = ((FancyDyeableItem)fancyDyeableItemStack.getItem()).isGlowing(fancyDyeableItemStack);
        if (!glowItemStack.isEmpty()) {
            if (isGlowing) { return ItemStack.EMPTY; }
            isGlowing = true;
        }
        return FancyDyeableItem.buildItemStack(fancyDyeableItemStack, dyeList, isGlowing);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return width * height >= 2; }
}