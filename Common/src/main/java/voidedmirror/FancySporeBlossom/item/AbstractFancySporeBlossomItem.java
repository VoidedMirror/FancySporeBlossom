package voidedmirror.FancySporeBlossom.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import voidedmirror.FancySporeBlossom.block.entity.AbstractFancySporeBlossomBlockEntity;

public abstract class AbstractFancySporeBlossomItem extends BlockItem implements FancyDyeableItem {
    public AbstractFancySporeBlossomItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (((FancyDyeableItem)stack.getItem()).isGlowing(stack)) {
            tooltip.add(Component.translatable("item.fancysporeblossom.fancy_spore_blossom.tooltip.glowing").withStyle(ChatFormatting.AQUA));
        }
    }

    @Override
    protected boolean updateCustomBlockEntityTag(@NotNull BlockPos pos, Level level, @Nullable Player player, @NotNull ItemStack stack, @NotNull BlockState state) {
        if (level.getBlockEntity(pos) instanceof AbstractFancySporeBlossomBlockEntity blockEntity) {
            blockEntity.readStackNbt(stack);
        }
        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }
}
