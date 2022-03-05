package voidedmirror.FancySporeBlossom.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.SporeBlossomBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import voidedmirror.FancySporeBlossom.FancySporeBlossom;
import voidedmirror.FancySporeBlossom.block.entity.FancySporeBlossomBlockEntity;

public class FancySporeBlossomBlock extends SporeBlossomBlock implements BlockEntityProvider {
    public FancySporeBlossomBlock(Settings settings) {
        super(settings);
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FancySporeBlossomBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) {
            world.getBlockEntity(pos, FancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readStackNbt(itemStack));
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity;
        if (!world.isClient && player.isCreative() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && (blockEntity = world.getBlockEntity(pos)) instanceof FancySporeBlossomBlockEntity) {
            FancySporeBlossomBlockEntity sporeBlossomBlockEntity = (FancySporeBlossomBlockEntity) blockEntity;
            ItemStack itemStack = new ItemStack(this);
            sporeBlossomBlockEntity.writeStackNbt(itemStack);
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
        super.onBreak(world, pos, state, player);
    }
}
