package voidedmirror.FancySporeBlossom.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import voidedmirror.FancySporeBlossom.particle.FancyFallingParticleEffect;

@Environment(EnvType.CLIENT)
public class FancyFallingParticle extends SpriteBillboardParticle {
    public boolean glowing;

    protected FancyFallingParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, FancyFallingParticleEffect parameters, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z);
        this.red = parameters.getColor().getX();
        this.green = parameters.getColor().getY();
        this.blue = parameters.getColor().getZ();
        this.glowing = parameters.isGlowing();
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.maxAge = (int)(64.0f / MathHelper.nextBetween(clientWorld.random, 0.1f, 0.9f));
        this.setSpriteForAge(spriteProvider);
        this.gravityStrength = 0.005f;
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
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.updateAge();
        if (this.dead) {
            return;
        }
        this.velocityY -= this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        if (this.onGround) {
            this.markDead();
        }
        if (this.dead) {
            return;
        }
        this.velocityX *= 0.98;
        this.velocityY *= 0.98;
        this.velocityZ *= 0.98;
        BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
        FluidState fluidState = this.world.getFluidState(blockPos);
        if (fluidState.getFluid() == Fluids.EMPTY && this.y < (double)((float)blockPos.getY() + fluidState.getHeight(this.world, blockPos))) {
            this.markDead();
        }
    }

    private void updateAge() {
        if (this.maxAge-- <= 0) {
            this.markDead();
        }
    }

    @Override
    public int getBrightness(float tint) {
        if (glowing) {
            return 220;
        }
        return super.getBrightness(tint);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<FancyFallingParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(FancyFallingParticleEffect fancyFallingParticleEffect, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new FancyFallingParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, fancyFallingParticleEffect, this.spriteProvider);
        }
    }
}
