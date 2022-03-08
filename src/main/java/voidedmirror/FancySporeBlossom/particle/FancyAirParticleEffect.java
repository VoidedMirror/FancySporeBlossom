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

public class FancyAirParticleEffect implements ParticleEffect {
    public static final Vec3f WHITE = new Vec3f(Vec3d.unpackRgb(0xFFFFFF));
    public static final FancyAirParticleEffect DEFAULT = new FancyAirParticleEffect(WHITE, 1.0f);
    public static final Codec<FancyAirParticleEffect> CODEC = Codec.unit(DEFAULT);
    public static final ParticleEffect.Factory<FancyAirParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<FancyAirParticleEffect>(){

        @Override
        public FancyAirParticleEffect read(ParticleType<FancyAirParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            Vec3f vec3f = readColor(reader);
            reader.expect(' ');
            float f = reader.readFloat();
            return new FancyAirParticleEffect(vec3f, f);
        }

        @Override
        public FancyAirParticleEffect read(ParticleType<FancyAirParticleEffect> type, PacketByteBuf packetByteBuf) {
            return new FancyAirParticleEffect(readColor(packetByteBuf), packetByteBuf.readFloat());
        }
    };

    private final Vec3f color;
    private final float scale;

    public FancyAirParticleEffect(Vec3f color, float scale) {
        this.color = color;
        this.scale = scale;
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
    public ParticleType<FancyAirParticleEffect> getType() {
        return FancySporeBlossom.FANCY_SPORE_BLOSSOM_AIR;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.color.getX());
        buf.writeFloat(this.color.getY());
        buf.writeFloat(this.color.getZ());
        buf.writeFloat(this.scale);
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getId(this.getType()), this.color.getX(), this.color.getY(), this.color.getZ(), this.scale);
    }

    public Vec3f getColor() {
        return this.color;
    }

    public float getScale() { return this.scale; }

}
