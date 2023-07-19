package voidedmirror.FancySporeBlossom.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.NotNull;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import voidedmirror.FancySporeBlossom.particle.FabricFancyFallingParticleOptions;

@Environment(EnvType.CLIENT)
public class FabricFancyFallingParticle extends AbstractFancyFallingParticle {
    protected FabricFancyFallingParticle(ClientWorld clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, FabricFancyFallingParticleOptions parameters, SpriteProvider spriteSet) {
        super(clientLevel, x, y, z, velocityX, velocityY, velocityZ, parameters, spriteSet);
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleFactory<FabricFancyFallingParticleOptions> {
        private final SpriteProvider spriteSet;

        public Provider(SpriteProvider spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull FabricFancyFallingParticleOptions fancyFallingParticleOptions, @NotNull ClientWorld clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FabricFancyFallingParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, fancyFallingParticleOptions, this.spriteSet);
        }
    }
}
