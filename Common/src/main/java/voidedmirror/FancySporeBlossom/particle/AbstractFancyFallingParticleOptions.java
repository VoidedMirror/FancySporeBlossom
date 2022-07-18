package voidedmirror.FancySporeBlossom.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import voidedmirror.FancySporeBlossom.item.FancyDyeableItem;

public abstract class AbstractFancyFallingParticleOptions implements ParticleOptions {
    public static final Vector3f WHITE = new Vector3f(Vec3.fromRGB24(FancyDyeableItem.DEFAULT_COLOR));

    private final Vector3f color;
    private final boolean glowing;

    public AbstractFancyFallingParticleOptions(Vector3f color, boolean glowing) {
        this.color = color;
        this.glowing = glowing;
    }

    public static Vector3f readColor(StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        float red = reader.readFloat();
        reader.expect(' ');
        float green = reader.readFloat();
        reader.expect(' ');
        float blue = reader.readFloat();
        return new Vector3f(red, green, blue);
    }

    public static Vector3f readColor(FriendlyByteBuf buf) {
        return new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(color.x());
        buf.writeFloat(color.y());
        buf.writeFloat(color.z());
        buf.writeBoolean(glowing);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %b", Registry.PARTICLE_TYPE.getId(getType()), color.x(), color.y(), color.z(), glowing);
    }

    public Vector3f getColor() {
        return color;
    }

    public boolean isGlowing() {
        return glowing;
    }
}
