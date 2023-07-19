package voidedmirror.FancySporeBlossom.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.joml.Vector3f;
import com.mojang.serialization.Codec;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import org.jetbrains.annotations.NotNull;

import voidedmirror.FancySporeBlossom.ForgeFancySporeBlossom;

public class ForgeFancyFallingParticleOptions extends AbstractFancyFallingParticleOptions {
    public static final ForgeFancyFallingParticleOptions DEFAULT = new ForgeFancyFallingParticleOptions(WHITE, false);
    public static final Codec<ForgeFancyFallingParticleOptions> CODEC = Codec.unit(DEFAULT);
    public static final ParticleOptions.Deserializer<ForgeFancyFallingParticleOptions> PARAMETERS_FACTORY = new ParticleOptions.Deserializer<>() {
        @Override
        public @NotNull ForgeFancyFallingParticleOptions fromCommand(@NotNull ParticleType<ForgeFancyFallingParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
            Vector3f color = readColor(reader);
            reader.expect(' ');
            boolean glowing = reader.readBoolean();
            return new ForgeFancyFallingParticleOptions(color, glowing);
        }

        @Override
        public @NotNull ForgeFancyFallingParticleOptions fromNetwork(@NotNull ParticleType<ForgeFancyFallingParticleOptions> type, @NotNull FriendlyByteBuf buf) {
            return new ForgeFancyFallingParticleOptions(readColor(buf), buf.readBoolean());
        }
    };

    public ForgeFancyFallingParticleOptions(Vector3f color, boolean glowing) {
        super(color, glowing);
    }

    @Override
    public @NotNull ParticleType<ForgeFancyFallingParticleOptions> getType() {
        return ForgeFancySporeBlossom.FANCY_FALLING_SPORE_BLOSSOM.get();
    }
}