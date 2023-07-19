package voidedmirror.FancySporeBlossom.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;

import org.jetbrains.annotations.NotNull;

import voidedmirror.FancySporeBlossom.particle.AbstractFancyFallingParticleOptions;

public abstract class AbstractFancyFallingParticle extends TextureSheetParticle {
    public boolean glowing;

    protected AbstractFancyFallingParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, AbstractFancyFallingParticleOptions parameters, SpriteSet spriteSet) {
        super(clientLevel, x, y, z);
        this.rCol = parameters.getColor().x();
        this.gCol = parameters.getColor().y();
        this.bCol = parameters.getColor().z();
        this.glowing = parameters.isGlowing();
        this.setSize(0.01f, 0.01f);
        this.lifetime = (int)(64.0f / Mth.randomBetween(clientLevel.random, 0.1f, 0.9f));
        this.setSpriteFromAge(spriteSet);
        this.gravity = 0.005f;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return glowing ? ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT : ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;
        updateAge();
        if (removed) { return; }
        yd -= gravity;
        move(xd, yd, zd);
        if (onGround) { remove(); }
        if (removed) { return; }
        xd *= 0.98;
        yd *= 0.98;
        zd *= 0.98;
        BlockPos blockPos = BlockPos.containing(x, y, z);
        FluidState fluidState = level.getFluidState(blockPos);
        if (fluidState.getType() == Fluids.EMPTY && y < (double)((float)blockPos.getY() + fluidState.getHeight(level, blockPos))) {
            remove();
        }
    }

    private void updateAge() { if (lifetime-- <= 0) { remove(); } }

    @Override
    public int getLightColor(float tint) { return glowing ? 220 : super.getLightColor(tint); }
}
