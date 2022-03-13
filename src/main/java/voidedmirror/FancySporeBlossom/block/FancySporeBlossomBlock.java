package voidedmirror.FancySporeBlossom.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.SporeBlossomBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import voidedmirror.FancySporeBlossom.FancySporeBlossom;
import voidedmirror.FancySporeBlossom.block.entity.FancySporeBlossomBlockEntity;
import voidedmirror.FancySporeBlossom.particle.FancyAirParticleEffect;
import voidedmirror.FancySporeBlossom.particle.FancyFallingParticleEffect;

import java.util.Random;

public class FancySporeBlossomBlock extends SporeBlossomBlock implements BlockEntityProvider {
    public static final BooleanProperty LIT = Properties.LIT;
    public static final int RANGE_XZ = 10;
    public static final int RANGE_Y = 10;
    public static final int MAX_AIR_PARTICLES = 14;

    public FancySporeBlossomBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(LIT, false));
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FancySporeBlossomBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext context) {
        NbtCompound compound = context.getStack().getSubNbt("display");
        if (compound != null) {
            return this.getDefaultState().with(LIT, compound.getBoolean("glowing"));
        }
        return this.getDefaultState().with(LIT, false);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) {
            world.getBlockEntity(pos, FancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readStackNbt(itemStack));
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && world.getBlockEntity(pos) instanceof FancySporeBlossomBlockEntity fancySporeBlossomBlockEntity) {
            ItemStack itemStack = new ItemStack(this);
            fancySporeBlossomBlockEntity.writeStackNbt(itemStack);
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.addParticle(new FancyFallingParticleEffect(getColor(world, pos), state.get(LIT)), (double)x + random.nextDouble(), (double)y + 0.7, (double)z + random.nextDouble(), 0.0, 0.0, 0.0);

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = 0; l < MAX_AIR_PARTICLES; ++l) {
            mutable.set(x + MathHelper.nextInt(random, -RANGE_XZ, RANGE_XZ), y - random.nextInt(RANGE_Y), z + MathHelper.nextInt(random, -RANGE_XZ, RANGE_XZ));
            BlockState blockState = world.getBlockState(mutable);
            if (blockState.isFullCube(world, mutable))
                continue;
            world.addParticle(new FancyAirParticleEffect(getColor(world, pos), 1.0f, state.get(LIT)), (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack itemStack = new ItemStack(this);
        if (world.getBlockEntity(pos) instanceof FancySporeBlossomBlockEntity fancySporeBlossomBlockEntity) {
            fancySporeBlossomBlockEntity.writeStackNbt(itemStack);
        }
        return itemStack;
    }

    public Vec3f getColor(World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof FancySporeBlossomBlockEntity fancySporeBlossomBlockEntity) {
            int color = fancySporeBlossomBlockEntity.getColor();
            float red = (float)(color >> 16 & 0xFF) / 255.0f;
            float green = (float)(color >> 8 & 0xFF) / 255.0f;
            float blue = (float)(color & 0xFF) / 255.0f;

            return new Vec3f(red, green, blue);
        }
        return new Vec3f(0.0f, 0.0f,0.0f);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
