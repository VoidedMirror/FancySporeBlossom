package voidedmirror.FancySporeBlossom.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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

    public void setColor(int color) {
        this.color = color;
        this.markUpdated();
    }

    public void mixColor(float[] diffuseColors) {
        int[] colorArray = new int[3];

        int oldColor = getColor();
        float blue = (float)(oldColor >> 16 & 0xFF) / 255.0f;
        float green = (float)(oldColor >> 8 & 0xFF) / 255.0f;
        float red = (float)(oldColor & 0xFF) / 255.0f;
        int maxValue = (int)(Math.max(blue, Math.max(green, red)) * 255.0f);
        colorArray[0] = colorArray[0] + (int)(blue * 255.0f);
        colorArray[1] = colorArray[1] + (int)(green * 255.0f);
        colorArray[2] = colorArray[2] + (int)(red * 255.0f);

        int r = (int)(diffuseColors[0] * 255.0f);
        int g = (int)(diffuseColors[1] * 255.0f);
        int b = (int)(diffuseColors[2] * 255.0f);
        maxValue += Math.max(r, Math.max(g, b));
        colorArray[0] = colorArray[0] + r;
        colorArray[1] = colorArray[1] + g;
        colorArray[2] = colorArray[2] + b;

        int redNorm = colorArray[0] / 2;
        int greenNorm = colorArray[1] / 2;
        int blueNorm = colorArray[2] / 2;
        float maxNormValue = (float)maxValue / (float)2;
        float maxNorm = Math.max(redNorm, Math.max(greenNorm, blueNorm));
        redNorm = (int)((float)redNorm * maxNormValue / maxNorm);
        greenNorm = (int)((float)greenNorm * maxNormValue / maxNorm);
        blueNorm = (int)((float)blueNorm * maxNormValue / maxNorm);
        int colorOut = redNorm;
        colorOut = (colorOut << 8) + greenNorm;
        colorOut = (colorOut << 8) + blueNorm;

        this.setColor(colorOut);
    }

    public boolean isGlowing() {
        return glowing;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    private void markUpdated() {
        this.setChanged();
        for (Player player : this.level.players()) {
            if (player instanceof ServerPlayer sPlayer) {
                sPlayer.connection.send(new ClientboundLevelChunkWithLightPacket(this.level.getChunkAt(this.getBlockPos()), this.level.getLightEngine(), null, null, false));
            }
        }
    }
}
