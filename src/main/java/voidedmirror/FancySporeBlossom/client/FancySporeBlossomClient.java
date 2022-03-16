package voidedmirror.FancySporeBlossom.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import voidedmirror.FancySporeBlossom.FancySporeBlossom;
import voidedmirror.FancySporeBlossom.client.particle.FancyAirParticle;
import voidedmirror.FancySporeBlossom.client.particle.FancyFallingParticle;
import voidedmirror.FancySporeBlossom.item.FancyDyeableItem;

@Environment(EnvType.CLIENT)
public class FancySporeBlossomClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(FancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK, RenderLayer.getCutout());

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return 0xFFFFFF;
            }
            return this.getBlockEntityColor(world, pos);
        }, FancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                ((FancyDyeableItem)stack.getItem()).getColor(stack),
                FancySporeBlossom.FANCY_SPORE_BLOSSOM_ITEM);

        ParticleFactoryRegistry.getInstance().register(FancySporeBlossom.FANCY_SPORE_BLOSSOM_AIR, FancyAirParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(FancySporeBlossom.FANCY_FALLING_SPORE_BLOSSOM, FancyFallingParticle.Factory::new);
    }

    public int getBlockEntityColor(BlockRenderView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof RenderAttachmentBlockEntity renderAttachmentBlockEntity) {
            Object data = renderAttachmentBlockEntity.getRenderAttachmentData();
            if (data instanceof Integer color) {
                return color;
            }
        }
        return 0xFFFFFF;
    }
}
