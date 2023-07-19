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

public class FabricFancyFallingParticleOptions extends AbstractFancyFallingParticleOptions {
    public static final FabricFancyFallingParticleOptions DEFAULT = new FabricFancyFallingParticleOptions(WHITE, false);
    public static final Codec<FabricFancyFallingParticleOptions> CODEC = Codec.unit(DEFAULT);
    public static final ParticleEffect.Factory<FabricFancyFallingParticleOptions> PARAMETERS_FACTORY = new ParticleEffect.Factory<>() {
        @Override
        public @NotNull FabricFancyFallingParticleOptions read(@NotNull ParticleType<FabricFancyFallingParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
            Vector3f color = readColor(reader);
            reader.expect(' ');
            boolean glowing = reader.readBoolean();
            return new FabricFancyFallingParticleOptions(color, glowing);
        }

        @Override
        public @NotNull FabricFancyFallingParticleOptions read(@NotNull ParticleType<FabricFancyFallingParticleOptions> type, @NotNull PacketByteBuf buf) {
            return new FabricFancyFallingParticleOptions(readColor(buf), buf.readBoolean());
        }
    };
    
    public FabricFancyFallingParticleOptions(Vector3f color, boolean glowing) {
        super(color, glowing);
    }

    @Override
    public ParticleType<FabricFancyFallingParticleOptions> getType() {
        return FabricFancySporeBlossom.FANCY_FALLING_SPORE_BLOSSOM;
    }
}
