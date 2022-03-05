package voidedmirror.FancySporeBlossom.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.DyeableItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import voidedmirror.FancySporeBlossom.FancySporeBlossom;

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
                ((DyeableItem)stack.getItem()).getColor(stack),
                FancySporeBlossom.FANCY_SPORE_BLOSSOM_ITEM);
    }

    public int getBlockEntityColor(BlockRenderView world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof  RenderAttachmentBlockEntity) {
            Object data = ((RenderAttachmentBlockEntity)entity).getRenderAttachmentData();
            if (data instanceof Integer) {
                return (Integer) data;
            }
        }
        return 0xFFFFFF;
    }
}
