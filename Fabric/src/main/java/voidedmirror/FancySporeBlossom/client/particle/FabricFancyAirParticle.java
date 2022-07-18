package voidedmirror.FancySporeBlossom.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;

import org.jetbrains.annotations.NotNull;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import voidedmirror.FancySporeBlossom.particle.FabricFancyAirParticleOptions;

@Environment(EnvType.CLIENT)
public class FabricFancyAirParticle extends AbstractFancyAirParticle {
    protected FabricFancyAirParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, FabricFancyAirParticleOptions parameters, SpriteSet spriteProvider) {
        super(clientLevel, x, y, z, velocityX, velocityY, velocityZ, parameters, spriteProvider);
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<FabricFancyAirParticleOptions> {
        private final SpriteSet spriteProvider;

        public Provider(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(@NotNull FabricFancyAirParticleOptions fancyAirParticleOptions, @NotNull ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FabricFancyAirParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, fancyAirParticleOptions, this.spriteProvider);
        }
    }
}
