package voidedmirror.FancySporeBlossom.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import voidedmirror.FancySporeBlossom.FancySporeBlossom;
import voidedmirror.FancySporeBlossom.block.entity.FancySporeBlossomBlockEntity;

import java.util.List;

public class FancySporeBlossomItem extends BlockItem implements FancyDyeableItem {
    public FancySporeBlossomItem(Settings settings) {
        super(FancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (((FancyDyeableItem)stack.getItem()).isGlowing(stack)) {
            tooltip.add(new TranslatableText("item.fancysporeblossom.fancy_spore_blossom.tooltip.glowing").formatted(Formatting.AQUA));
        }
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof FancySporeBlossomBlockEntity FancySporeBlossomEntity) {
            FancySporeBlossomEntity.readStackNbt(stack);
        }
        return super.postPlacement(pos, world, player, stack, state);
    }
}
