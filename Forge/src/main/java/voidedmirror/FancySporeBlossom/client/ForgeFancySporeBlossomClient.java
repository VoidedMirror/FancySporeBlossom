package voidedmirror.FancySporeBlossom.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import voidedmirror.FancySporeBlossom.Constants;
import voidedmirror.FancySporeBlossom.ForgeFancySporeBlossom;
import voidedmirror.FancySporeBlossom.block.entity.AbstractFancySporeBlossomBlockEntity;
import voidedmirror.FancySporeBlossom.client.particle.ForgeFancyAirParticle;
import voidedmirror.FancySporeBlossom.client.particle.ForgeFancyFallingParticle;
import voidedmirror.FancySporeBlossom.item.FancyDyeableItem;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeFancySporeBlossomClient {
    @SubscribeEvent
    public static void onInitializeClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(ForgeFancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK.get(), RenderType.cutout()));
    }

    @SubscribeEvent
    public static void blockColorHandler(RegisterColorHandlersEvent.Block event) {
        BlockColor fancySporeBlossomBlockColor = (state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return FancyDyeableItem.DEFAULT_COLOR;
            }
            if (world.getBlockEntity(pos) instanceof AbstractFancySporeBlossomBlockEntity blockEntity) {
                return blockEntity.getColor();
            }
            return FancyDyeableItem.DEFAULT_COLOR;
        };
        event.getBlockColors().register(fancySporeBlossomBlockColor, ForgeFancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK.get());
    }

    @SubscribeEvent
    public static void itemColorHandler(RegisterColorHandlersEvent.Item event) {
        ItemColor fancySporeBlossomItemColor = (stack, tintIndex) -> ((FancyDyeableItem) stack.getItem()).getDyeColor(stack);
        event.getItemColors().register(fancySporeBlossomItemColor, ForgeFancySporeBlossom.FANCY_SPORE_BLOSSOM_ITEM.get());
    }

    @SubscribeEvent
    public static void particleProviders(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ForgeFancySporeBlossom.FANCY_SPORE_BLOSSOM_AIR.get(), ForgeFancyAirParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ForgeFancySporeBlossom.FANCY_FALLING_SPORE_BLOSSOM.get(), ForgeFancyFallingParticle.Provider::new);
    }
}
