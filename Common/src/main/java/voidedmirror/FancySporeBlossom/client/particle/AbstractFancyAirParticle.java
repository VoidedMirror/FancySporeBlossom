package voidedmirror.FancySporeBlossom.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.util.Mth;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import voidedmirror.FancySporeBlossom.particle.AbstractFancyAirParticleOptions;

public abstract class AbstractFancyAirParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public boolean glowing;

    protected AbstractFancyAirParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, AbstractFancyAirParticleOptions parameters, SpriteSet spriteSet) {
        super(clientLevel, x, y - 0.125f, z, 0.0f, -0.8f, 0.0f);
        this.rCol = parameters.getColor().x();
        this.gCol = parameters.getColor().y();
        this.bCol = parameters.getColor().z();
        this.quadSize *= parameters.getScale() * (random.nextFloat() * 0.6f + 0.6f);
        this.glowing = parameters.isGlowing();
        this.setSize(0.01f, 0.01f);
        this.spriteSet = spriteSet;
        this.lifetime = Mth.randomBetweenInclusive(clientLevel.random, 500, 1000);
        this.setSpriteFromAge(spriteSet);
        this.gravity = 0.01f;
        this.hasPhysics = false;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return glowing ? ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT : ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        setSpriteFromAge(spriteSet);
    }

    @Override
    public int getLightColor(float tint) {
        return glowing ? 220 - (age / (lifetime / 20)) : super.getLightColor(tint);
    }

    @Override
    public @NotNull Optional<ParticleGroup> getParticleGroup() {
        return Optional.of(ParticleGroup.SPORE_BLOSSOM);
    }
}
