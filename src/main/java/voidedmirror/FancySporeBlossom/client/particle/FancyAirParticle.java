package voidedmirror.FancySporeBlossom.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import voidedmirror.FancySporeBlossom.particle.FancyAirParticleEffect;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class FancyAirParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    public boolean glowing;

    protected FancyAirParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, FancyAirParticleEffect parameters, SpriteProvider spriteProvider) {
        super(clientWorld, x, y - 0.125f, z, 0.0f, -0.8f, 0.0f);
        this.red = parameters.getColor().getX();
        this.green = parameters.getColor().getY();
        this.blue = parameters.getColor().getZ();
        this.scale *= parameters.getScale() * (this.random.nextFloat() * 0.6f + 0.6f);
        this.glowing = parameters.isGlowing();
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.spriteProvider = spriteProvider;
        this.maxAge = MathHelper.nextBetween(clientWorld.random, 500, 1000);
        this.setSpriteForAge(spriteProvider);
        this.gravityStrength = 0.01f;
        this.collidesWithWorld = false;
    }

    @Override
    public ParticleTextureSheet getType() {
        if (glowing) {
            return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
        }
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    public int getBrightness(float tint) {
        if (glowing) {
            return 220 - (this.age / (this.maxAge / 20));
        }
        return super.getBrightness(tint);
    }

    @Override
    public Optional<ParticleGroup> getGroup() {
        return Optional.of(ParticleGroup.SPORE_BLOSSOM_AIR);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<FancyAirParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(FancyAirParticleEffect fancyAirParticleEffect, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FancyAirParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, fancyAirParticleEffect, this.spriteProvider);
        }
    }
}
