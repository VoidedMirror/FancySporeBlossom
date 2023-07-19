package voidedmirror.FancySporeBlossom.recipe;

import com.google.common.collect.Lists;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import voidedmirror.FancySporeBlossom.item.FancyDyeableItem;

import java.util.ArrayList;


public abstract class AbstractFancySporeBlossomRecipe extends CustomRecipe {
    private static final Ingredient GLOW_ON = Ingredient.of(Items.GLOW_BERRIES);
    private static final Ingredient GLOW_OFF = Ingredient.of(Items.SWEET_BERRIES);

    public AbstractFancySporeBlossomRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, @NotNull Level level) {
        ItemStack fancyDyeableItemStack = ItemStack.EMPTY;
        boolean isDyes = false;
        boolean isGlowOn = false;
        boolean isGlowOff = false;
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
            if (GLOW_ON.test(itemStack)) {
                if (isGlowOn) { return false; }
                isGlowOn = true;
                continue;
            }
            if (GLOW_OFF.test(itemStack)) {
                if (isGlowOff) { return false; }
                isGlowOff = true;
                continue;
            }
            return false;
        }
        if (fancyDyeableItemStack.isEmpty()) { return false; }

        boolean isGlowing = ((FancyDyeableItem)fancyDyeableItemStack.getItem()).isGlowing(fancyDyeableItemStack);
        boolean canGlowOn = isGlowOn && !isGlowOff && !isGlowing;
        boolean canGlowOff = isGlowOff && !isGlowOn && isGlowing;

        return canGlowOn || canGlowOff || (isDyes && !isGlowOn && !isGlowOff);
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack fancyDyeableItemStack = ItemStack.EMPTY;
        ItemStack glowOnItemStack = ItemStack.EMPTY;
        ItemStack glowOffItemStack = ItemStack.EMPTY;
        ArrayList<DyeItem> dyeList = Lists.newArrayList();
        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemStack = container.getItem(i);
            if (itemStack.isEmpty()) { continue; }
            if (itemStack.getItem() instanceof FancyDyeableItem) {
                if (!fancyDyeableItemStack.isEmpty()) { return ItemStack.EMPTY; }
                fancyDyeableItemStack = itemStack.copy();
                continue;
            }
            if (itemStack.getItem() instanceof DyeItem) {
                dyeList.add((DyeItem)itemStack.getItem());
                continue;
            }
            if (GLOW_ON.test(itemStack)) {
                if (!glowOnItemStack.isEmpty()) { return ItemStack.EMPTY; }
                glowOnItemStack = itemStack;
                continue;
            }
            if (GLOW_OFF.test(itemStack)) {
                if (!glowOffItemStack.isEmpty()) { return ItemStack.EMPTY; }
                glowOffItemStack = itemStack;
                continue;
            }
            return ItemStack.EMPTY;
        }
        if (fancyDyeableItemStack.isEmpty()) { return ItemStack.EMPTY; }

        boolean isGlowing = ((FancyDyeableItem)fancyDyeableItemStack.getItem()).isGlowing(fancyDyeableItemStack);
        if (!glowOnItemStack.isEmpty() && glowOffItemStack.isEmpty()) {
            if (isGlowing) { return ItemStack.EMPTY; }
            isGlowing = true;
        } else if (glowOnItemStack.isEmpty() && !glowOffItemStack.isEmpty()) {
            if (!isGlowing) { return ItemStack.EMPTY; }
            isGlowing = false;
        }

        return FancyDyeableItem.buildItemStack(fancyDyeableItemStack, dyeList, isGlowing);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return width * height >= 2; }
}