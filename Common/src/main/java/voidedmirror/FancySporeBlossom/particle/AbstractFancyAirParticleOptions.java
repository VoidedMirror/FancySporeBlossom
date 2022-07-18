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

public abstract class AbstractFancyAirParticleOptions implements ParticleOptions {
    public static final Vector3f WHITE = new Vector3f(Vec3.fromRGB24(FancyDyeableItem.DEFAULT_COLOR));

    private final Vector3f color;
    private final float scale;
    private final boolean glowing;

    public AbstractFancyAirParticleOptions(Vector3f color, float scale, boolean glowing) {
        this.color = color;
        this.scale = scale;
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
        buf.writeFloat(scale);
        buf.writeBoolean(glowing);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %b", Registry.PARTICLE_TYPE.getId(getType()), color.x(), color.y(), color.z(), scale, glowing);
    }

    public Vector3f getColor() {
        return color;
    }

    public float getScale() { return scale; }

    public boolean isGlowing() {
        return glowing;
    }
}
