package voidedmirror.FancySporeBlossom.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

import voidedmirror.FancySporeBlossom.item.FancyDyeableItem;

public abstract class AbstractFancySporeBlossomBlockEntity extends BlockEntity {
    public static final String DISPLAY_KEY = FancyDyeableItem.DISPLAY_KEY;
    public static final String COLOR_KEY = FancyDyeableItem.COLOR_KEY;
    public static final String GLOWING_KEY = FancyDyeableItem.GLOWING_KEY;
    public static final int DEFAULT_COLOR = FancyDyeableItem.DEFAULT_COLOR;

    private int color = DEFAULT_COLOR;
    private boolean glowing = false;

    public AbstractFancySporeBlossomBlockEntity(BlockEntityType<?> type,  BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        color = tag.getInt(COLOR_KEY);
        glowing = tag.getBoolean(GLOWING_KEY);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putInt(COLOR_KEY, color);
        tag.putBoolean(GLOWING_KEY, glowing);
        super.saveAdditional(tag);
    }

    public void readStackNbt(ItemStack stack) {
        CompoundTag tag = stack.getTagElement(DISPLAY_KEY);
        if (tag != null) {
            if (tag.contains(COLOR_KEY, 99)) {
                color = tag.getInt(COLOR_KEY);
            }
            if (tag.contains(GLOWING_KEY)) {
                glowing = tag.getBoolean(GLOWING_KEY);
            }
        }
    }

    public void writeStackNbt(ItemStack stack) {
        CompoundTag tag = new CompoundTag();
        tag.putInt(COLOR_KEY, color);
        tag.putBoolean(GLOWING_KEY, glowing);
        stack.addTagElement(DISPLAY_KEY, tag);
    }

    public int getColor() {
        return color;
    }

    public boolean isGlowing() {
        return glowing;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
