package voidedmirror.FancySporeBlossom.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import voidedmirror.FancySporeBlossom.FabricFancySporeBlossom;
import voidedmirror.FancySporeBlossom.block.entity.AbstractFancySporeBlossomBlockEntity;
import voidedmirror.FancySporeBlossom.client.particle.FabricFancyAirParticle;
import voidedmirror.FancySporeBlossom.client.particle.FabricFancyFallingParticle;
import voidedmirror.FancySporeBlossom.item.FancyDyeableItem;

@Environment(EnvType.CLIENT)
public class FabricFancySporeBlossomClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK, RenderLayer.getCutout());

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) { return FancyDyeableItem.DEFAULT_COLOR; }
            if (world.getBlockEntity(pos) instanceof AbstractFancySporeBlossomBlockEntity blockEntity) {
                return blockEntity.getColor();
            }
            return FancyDyeableItem.DEFAULT_COLOR;
        }, FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((FancyDyeableItem)stack.getItem()).getDyeColor(stack), FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_ITEM);

        ParticleFactoryRegistry.getInstance().register(FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_AIR, FabricFancyAirParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FabricFancySporeBlossom.FANCY_FALLING_SPORE_BLOSSOM, FabricFancyFallingParticle.Provider::new);
    }
}
