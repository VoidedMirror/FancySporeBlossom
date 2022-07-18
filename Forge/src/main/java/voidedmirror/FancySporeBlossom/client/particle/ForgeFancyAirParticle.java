package voidedmirror.FancySporeBlossom.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;

import org.jetbrains.annotations.NotNull;

import voidedmirror.FancySporeBlossom.particle.ForgeFancyAirParticleOptions;

public class ForgeFancyAirParticle extends AbstractFancyAirParticle {
    protected ForgeFancyAirParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, ForgeFancyAirParticleOptions parameters, SpriteSet spriteSet) {
        super(clientLevel, x, y, z, velocityX, velocityY, velocityZ, parameters, spriteSet);
    }

    public static class Provider implements ParticleProvider<ForgeFancyAirParticleOptions> {
        private final SpriteSet spriteProvider;

        public Provider(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(@NotNull ForgeFancyAirParticleOptions fancyAirParticleOptions, @NotNull ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ForgeFancyAirParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, fancyAirParticleOptions, this.spriteProvider);
        }
    }
}
