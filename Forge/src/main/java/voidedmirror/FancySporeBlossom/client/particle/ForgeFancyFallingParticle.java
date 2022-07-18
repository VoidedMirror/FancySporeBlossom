package voidedmirror.FancySporeBlossom.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;

import org.jetbrains.annotations.NotNull;

import voidedmirror.FancySporeBlossom.particle.ForgeFancyFallingParticleOptions;

public class ForgeFancyFallingParticle extends AbstractFancyFallingParticle {
    protected ForgeFancyFallingParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, ForgeFancyFallingParticleOptions parameters, SpriteSet spriteSet) {
        super(clientLevel, x, y, z, velocityX, velocityY, velocityZ, parameters, spriteSet);
    }

    public static class Provider implements ParticleProvider<ForgeFancyFallingParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull ForgeFancyFallingParticleOptions fancyFallingParticleOptions, @NotNull ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ForgeFancyFallingParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, fancyFallingParticleOptions, this.spriteSet);
        }
    }
}
