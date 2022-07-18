package voidedmirror.FancySporeBlossom.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import org.jetbrains.annotations.NotNull;

import voidedmirror.FancySporeBlossom.ForgeFancySporeBlossom;

public class ForgeFancyAirParticleOptions extends AbstractFancyAirParticleOptions {
    public static final ForgeFancyAirParticleOptions DEFAULT = new ForgeFancyAirParticleOptions(WHITE, 1.0f, false);
    public static final Codec<ForgeFancyAirParticleOptions> CODEC = Codec.unit(DEFAULT);
    public static final ParticleOptions.Deserializer<ForgeFancyAirParticleOptions> PARAMETERS_FACTORY = new ParticleOptions.Deserializer<>() {
        @Override
        public @NotNull ForgeFancyAirParticleOptions fromCommand(@NotNull ParticleType<ForgeFancyAirParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
            Vector3f color = readColor(reader);
            reader.expect(' ');
            float scale = reader.readFloat();
            boolean glowing = reader.readBoolean();
            return new ForgeFancyAirParticleOptions(color, scale, glowing);
        }

        @Override
        public @NotNull ForgeFancyAirParticleOptions fromNetwork(@NotNull ParticleType<ForgeFancyAirParticleOptions> type, @NotNull FriendlyByteBuf buf) {
            return new ForgeFancyAirParticleOptions(readColor(buf), buf.readFloat(), buf.readBoolean());
        }
    };

    public ForgeFancyAirParticleOptions(Vector3f color, float scale, boolean glowing) {
        super(color, scale, glowing);
    }

    @Override
    public @NotNull ParticleType<ForgeFancyAirParticleOptions> getType() {
        return ForgeFancySporeBlossom.FANCY_SPORE_BLOSSOM_AIR.get();
    }
}
