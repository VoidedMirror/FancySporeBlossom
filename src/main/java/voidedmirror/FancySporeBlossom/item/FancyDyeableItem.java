package voidedmirror.FancySporeBlossom.item;

import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.List;

public interface FancyDyeableItem {
    String DISPLAY_KEY = "display";
    String COLOR_KEY = "color";
    String GLOWING_KEY = "glowing";
    int DEFAULT_COLOR = 0xFFFFFF;

    default boolean hasColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
        return nbtCompound != null && nbtCompound.contains(COLOR_KEY, 99);
    }

    default int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
        if (nbtCompound != null && nbtCompound.contains(COLOR_KEY, 99)) {
            return nbtCompound.getInt(COLOR_KEY);
        }
        return DEFAULT_COLOR;
    }

    default boolean isGlowing(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
        if (nbtCompound != null && nbtCompound.contains(GLOWING_KEY)) {
            return nbtCompound.getBoolean(GLOWING_KEY);
        }
        return false;
    }

    default void removeColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt(DISPLAY_KEY);
        if (nbtCompound != null && nbtCompound.contains(COLOR_KEY)) {
            nbtCompound.remove(COLOR_KEY);
        }
    }

    default void setColor(ItemStack stack, int color) {
        stack.getOrCreateSubNbt(DISPLAY_KEY).putInt(COLOR_KEY, color);
    }

    default void setGlowing(ItemStack stack) {
        stack.getOrCreateSubNbt(DISPLAY_KEY).putBoolean(GLOWING_KEY, true);
    }

    static ItemStack blendAndSetColor(ItemStack stack, List<DyeItem> colors) {
        ItemStack itemStack = ItemStack.EMPTY;
        int[] colorArray = new int[3];
        int maxValue = 0;
        int dyeNum = 0;
        FancyDyeableItem fancyDyeableItem = null;
        Item item = stack.getItem();
        if (item instanceof FancyDyeableItem) {
            fancyDyeableItem = (FancyDyeableItem) item;
            itemStack = stack.copy();
            itemStack.setCount(1);
            if (fancyDyeableItem.hasColor(stack)) {
                int color = fancyDyeableItem.getColor(itemStack);
                float blue = (float)(color >> 16 & 0xFF) / 255.0f;
                float green = (float)(color >> 8 & 0xFF) / 255.0f;
                float red = (float)(color & 0xFF) / 255.0f;
                maxValue += (int)(Math.max(blue, Math.max(green, red)) * 255.0f);
                colorArray[0] = colorArray[0] + (int)(blue * 255.0f);
                colorArray[1] = colorArray[1] + (int)(green * 255.0f);
                colorArray[2] = colorArray[2] + (int)(red * 255.0f);
                ++dyeNum;
            }
            for (DyeItem dyeItem : colors) {
                float[] fs = dyeItem.getColor().getColorComponents();
                int r = (int)(fs[0] * 255.0f);
                int g = (int)(fs[1] * 255.0f);
                int b = (int)(fs[2] * 255.0f);
                maxValue += Math.max(r, Math.max(g, b));
                colorArray[0] = colorArray[0] + r;
                colorArray[1] = colorArray[1] + g;
                colorArray[2] = colorArray[2] + b;
                ++dyeNum;
            }
        }
        if (fancyDyeableItem == null) {
            return ItemStack.EMPTY;
        }
        int redNorm = colorArray[0] / dyeNum;
        int greenNorm = colorArray[1] / dyeNum;
        int blueNorm = colorArray[2] / dyeNum;
        float maxNormValue = (float)maxValue / (float)dyeNum;
        float maxNorm = Math.max(redNorm, Math.max(greenNorm, blueNorm));
        redNorm = (int)((float)redNorm * maxNormValue / maxNorm);
        greenNorm = (int)((float)greenNorm * maxNormValue / maxNorm);
        blueNorm = (int)((float)blueNorm * maxNormValue / maxNorm);
        int colorOut = redNorm;
        colorOut = (colorOut << 8) + greenNorm;
        colorOut = (colorOut << 8) + blueNorm;
        fancyDyeableItem.setColor(itemStack, colorOut);
        return itemStack;
    }
}
