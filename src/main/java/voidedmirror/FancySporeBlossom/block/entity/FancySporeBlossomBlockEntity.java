package voidedmirror.FancySporeBlossom.block.entity;

import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import voidedmirror.FancySporeBlossom.FancySporeBlossom;

public class FancySporeBlossomBlockEntity extends BlockEntity implements RenderAttachmentBlockEntity {
    public static final String DISPLAY_KEY = "display";
    public static final String COLOR_KEY = "color";
    public static final int DEFAULT_COLOR = 0xFFFFFF;

    private int color = DEFAULT_COLOR;

    public FancySporeBlossomBlockEntity(BlockPos pos, BlockState state) {
        super(FancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        color = tag.getInt(COLOR_KEY);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        tag.putInt(COLOR_KEY, color);
        super.writeNbt(tag);
    }

    public void readStackNbt(ItemStack stack) {
        NbtCompound compound = stack.getSubNbt(DISPLAY_KEY);
        if (compound != null && compound.contains(COLOR_KEY, 99)) {
            color = compound.getInt(COLOR_KEY);
        }
    }

    public void writeStackNbt(ItemStack stack) {
        NbtCompound compound = new NbtCompound();
        compound.putInt(COLOR_KEY, color);
        stack.setSubNbt(DISPLAY_KEY, compound);
    }

    public int getColor() {
        return color;
    }

    @Override
    public @Nullable Object getRenderAttachmentData() {
        return getColor();
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
