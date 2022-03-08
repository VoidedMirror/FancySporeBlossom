package voidedmirror.FancySporeBlossom.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import voidedmirror.FancySporeBlossom.FancySporeBlossom;

import java.util.Locale;

public class FancyFallingParticleEffect implements ParticleEffect {
    public static final Vec3f WHITE = new Vec3f(Vec3d.unpackRgb(0xFFFFFF));
    public static final FancyFallingParticleEffect DEFAULT = new FancyFallingParticleEffect(WHITE);
    public static final Codec<FancyFallingParticleEffect> CODEC = Codec.unit(DEFAULT);
    public static final Factory<FancyFallingParticleEffect> PARAMETERS_FACTORY = new Factory<FancyFallingParticleEffect>(){

        @Override
        public FancyFallingParticleEffect read(ParticleType<FancyFallingParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            Vec3f vec3f = readColor(reader);
            reader.expect(' ');
            float f = reader.readFloat();
            return new FancyFallingParticleEffect(vec3f);
        }

        @Override
        public FancyFallingParticleEffect read(ParticleType<FancyFallingParticleEffect> type, PacketByteBuf packetByteBuf) {
            return new FancyFallingParticleEffect(readColor(packetByteBuf));
        }
    };

    private final Vec3f color;

    public FancyFallingParticleEffect(Vec3f color) {
        this.color = color;
    }

    public static Vec3f readColor(StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        float red = reader.readFloat();
        reader.expect(' ');
        float green = reader.readFloat();
        reader.expect(' ');
        float blue = reader.readFloat();
        return new Vec3f(red, green, blue);
    }

    public static Vec3f readColor(PacketByteBuf buf) {
        return new Vec3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    @Override
    public ParticleType<FancyFallingParticleEffect> getType() {
        return FancySporeBlossom.FANCY_FALLING_SPORE_BLOSSOM;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.color.getX());
        buf.writeFloat(this.color.getY());
        buf.writeFloat(this.color.getZ());
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getId(this.getType()), this.color.getX(), this.color.getY(), this.color.getZ());
    }

    public Vec3f getColor() {
        return this.color;
    }

}
