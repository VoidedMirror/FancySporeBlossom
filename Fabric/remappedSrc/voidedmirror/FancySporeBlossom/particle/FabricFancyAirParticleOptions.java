package voidedmirror.FancySporeBlossom.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import org.jetbrains.annotations.NotNull;

import voidedmirror.FancySporeBlossom.FabricFancySporeBlossom;

public class FabricFancyAirParticleOptions extends AbstractFancyAirParticleOptions {
    public static final FabricFancyAirParticleOptions DEFAULT = new FabricFancyAirParticleOptions(WHITE, 1.0f, false);
    public static final Codec<FabricFancyAirParticleOptions> CODEC = Codec.unit(DEFAULT);
    public static final ParticleEffect.Factory<FabricFancyAirParticleOptions> PARAMETERS_FACTORY = new ParticleEffect.Factory<>() {
        @Override
        public @NotNull FabricFancyAirParticleOptions read(@NotNull ParticleType<FabricFancyAirParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
            Vector3f color = readColor(reader);
            reader.expect(' ');
            float scale = reader.readFloat();
            boolean glowing = reader.readBoolean();
            return new FabricFancyAirParticleOptions(color, scale, glowing);
        }

        @Override
        public @NotNull FabricFancyAirParticleOptions read(@NotNull ParticleType<FabricFancyAirParticleOptions> type, @NotNull PacketByteBuf buf) {
            return new FabricFancyAirParticleOptions(readColor(buf), buf.readFloat(), buf.readBoolean());
        }
    };

    public FabricFancyAirParticleOptions(Vector3f color, float scale, boolean glowing) {
        super(color, scale, glowing);
    }

    @Override
    public ParticleType<FabricFancyAirParticleOptions> getType() {
        return FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_AIR;
    }

}
