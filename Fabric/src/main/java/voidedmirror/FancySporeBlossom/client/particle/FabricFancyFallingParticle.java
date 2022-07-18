package voidedmirror.FancySporeBlossom.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;

import org.jetbrains.annotations.NotNull;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import voidedmirror.FancySporeBlossom.particle.FabricFancyFallingParticleOptions;

@Environment(EnvType.CLIENT)
public class FabricFancyFallingParticle extends AbstractFancyFallingParticle {
    protected FabricFancyFallingParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, FabricFancyFallingParticleOptions parameters, SpriteSet spriteSet) {
        super(clientLevel, x, y, z, velocityX, velocityY, velocityZ, parameters, spriteSet);
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<FabricFancyFallingParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull FabricFancyFallingParticleOptions fancyFallingParticleOptions, @NotNull ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FabricFancyFallingParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, fancyFallingParticleOptions, this.spriteSet);
        }
    }
}
