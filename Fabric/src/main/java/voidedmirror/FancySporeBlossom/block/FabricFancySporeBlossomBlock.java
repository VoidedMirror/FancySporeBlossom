package voidedmirror.FancySporeBlossom.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import voidedmirror.FancySporeBlossom.FabricFancySporeBlossom;
import voidedmirror.FancySporeBlossom.block.entity.FabricFancySporeBlossomBlockEntity;
import voidedmirror.FancySporeBlossom.particle.FabricFancyAirParticleOptions;
import voidedmirror.FancySporeBlossom.particle.FabricFancyFallingParticleOptions;

public class FabricFancySporeBlossomBlock extends AbstractFancySporeBlossomBlock {
    public FabricFancySporeBlossomBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FabricFancySporeBlossomBlockEntity(pos, state);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        level.addParticle(new FabricFancyFallingParticleOptions(getColor(level, pos), state.getValue(LIT)), (double)x + random.nextDouble(), (double)y + 0.7, (double)z + random.nextDouble(), 0.0, 0.0, 0.0);

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < MAX_AIR_PARTICLES; ++l) {
            mutable.set(x + Mth.nextInt(random, -RANGE_XZ, RANGE_XZ), y - random.nextInt(RANGE_Y), z + Mth.nextInt(random, -RANGE_XZ, RANGE_XZ));
            BlockState blockState = level.getBlockState(mutable);
            if (blockState.isCollisionShapeFullBlock(level, mutable)) { continue; }
            level.addParticle(new FabricFancyAirParticleOptions(getColor(level, pos), 1.0f, state.getValue(LIT)), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack itemStack) {
        if (level.isClientSide) {
            level.getBlockEntity(pos, FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readStackNbt(itemStack));
        }
    }
}
