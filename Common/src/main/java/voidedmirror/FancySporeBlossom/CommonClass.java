package voidedmirror.FancySporeBlossom;

import net.minecraft.resources.ResourceLocation;

public class CommonClass {
    public static void init() {}

    public static ResourceLocation getID(String string) {
        return new ResourceLocation(Constants.MOD_ID, string);
    }
}