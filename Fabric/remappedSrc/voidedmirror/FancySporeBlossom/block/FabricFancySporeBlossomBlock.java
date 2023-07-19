package voidedmirror.FancySporeBlossom.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import voidedmirror.FancySporeBlossom.FabricFancySporeBlossom;
import voidedmirror.FancySporeBlossom.block.entity.FabricFancySporeBlossomBlockEntity;
import voidedmirror.FancySporeBlossom.particle.FabricFancyAirParticleOptions;
import voidedmirror.FancySporeBlossom.particle.FabricFancyFallingParticleOptions;

public class FabricFancySporeBlossomBlock extends AbstractFancySporeBlossomBlock {
    public FabricFancySporeBlossomBlock(Settings properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FabricFancySporeBlossomBlockEntity(pos, state);
    }

    @Override
    public void randomDisplayTick(BlockState state, World level, BlockPos pos, net.minecraft.util.math.random.Random random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        level.addParticle(new FabricFancyFallingParticleOptions(getColor(level, pos), state.get(LIT)), (double)x + random.nextDouble(), (double)y + 0.7, (double)z + random.nextDouble(), 0.0, 0.0, 0.0);

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = 0; l < MAX_AIR_PARTICLES; ++l) {
            mutable.set(x + MathHelper.nextInt(random, -RANGE_XZ, RANGE_XZ), y - random.nextInt(RANGE_Y), z + MathHelper.nextInt(random, -RANGE_XZ, RANGE_XZ));
            BlockState blockState = level.getBlockState(mutable);
            if (blockState.isFullCube(level, mutable)) { continue; }
            level.addParticle(new FabricFancyAirParticleOptions(getColor(level, pos), 1.0f, state.get(LIT)), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onPlaced(World level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack itemStack) {
        if (level.isClient) {
            level.getBlockEntity(pos, FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readStackNbt(itemStack));
        }
    }
}
