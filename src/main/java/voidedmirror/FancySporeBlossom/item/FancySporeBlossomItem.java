package voidedmirror.FancySporeBlossom.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import voidedmirror.FancySporeBlossom.FancySporeBlossom;
import voidedmirror.FancySporeBlossom.block.entity.FancySporeBlossomBlockEntity;

public class FancySporeBlossomItem extends BlockItem implements DyeableItem {
    public FancySporeBlossomItem(Settings settings) {
        super(FancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK, settings);
    }

    @Override
    public int getColor(ItemStack stack) {
        NbtCompound tag = stack.getSubNbt(DISPLAY_KEY);
        if (tag != null && tag.contains(COLOR_KEY, 99)) {
            return tag.getInt(COLOR_KEY);
        }
        return 0xFFFFFF;
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
