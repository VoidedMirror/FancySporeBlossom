package voidedmirror.FancySporeBlossom.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.NotNull;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import voidedmirror.FancySporeBlossom.particle.FabricFancyAirParticleOptions;

@Environment(EnvType.CLIENT)
public class FabricFancyAirParticle extends AbstractFancyAirParticle {
    protected FabricFancyAirParticle(ClientWorld clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, FabricFancyAirParticleOptions parameters, SpriteProvider spriteProvider) {
        super(clientLevel, x, y, z, velocityX, velocityY, velocityZ, parameters, spriteProvider);
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleFactory<FabricFancyAirParticleOptions> {
        private final SpriteProvider spriteProvider;

        public Provider(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(@NotNull FabricFancyAirParticleOptions fancyAirParticleOptions, @NotNull ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FabricFancyAirParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, fancyAirParticleOptions, this.spriteProvider);
        }
    }
}
