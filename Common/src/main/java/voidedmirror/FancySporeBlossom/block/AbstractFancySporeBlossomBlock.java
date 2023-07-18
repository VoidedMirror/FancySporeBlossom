package voidedmirror.FancySporeBlossom.block;

import com.mojang.math.Vector3f;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SporeBlossomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import voidedmirror.FancySporeBlossom.block.entity.AbstractFancySporeBlossomBlockEntity;

public abstract class AbstractFancySporeBlossomBlock extends SporeBlossomBlock implements EntityBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final int RANGE_XZ = 10;
    public static final int RANGE_Y = 10;
    public static final int MAX_AIR_PARTICLES = 14;

    public AbstractFancySporeBlossomBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(LIT, false));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        CompoundTag tag = context.getItemInHand().getTagElement(AbstractFancySporeBlossomBlockEntity.DISPLAY_KEY);
        if (tag != null) {
            return defaultBlockState().setValue(LIT, tag.getBoolean(AbstractFancySporeBlossomBlockEntity.GLOWING_KEY));
        }
        return defaultBlockState().setValue(LIT, false);
    }

    @Override
    public void playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!level.isClientSide && player.isCreative() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && level.getBlockEntity(pos) instanceof AbstractFancySporeBlossomBlockEntity blockEntity) {
            ItemStack stack = new ItemStack(this);
            blockEntity.writeStackNbt(stack);
            ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
        ItemStack stack = new ItemStack(this);
        if (level.getBlockEntity(pos) instanceof AbstractFancySporeBlossomBlockEntity blockEntity) {
            blockEntity.writeStackNbt(stack);
        }
        return stack;
    }

    public Vector3f getColor(Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof AbstractFancySporeBlossomBlockEntity blockEntity) {
            int color = blockEntity.getColor();
            float red = (float)(color >> 16 & 0xFF) / 255.0f;
            float green = (float)(color >> 8 & 0xFF) / 255.0f;
            float blue = (float)(color & 0xFF) / 255.0f;

            return new Vector3f(red, green, blue);
        }
        return new Vector3f(0.0f, 0.0f,0.0f);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (level.getBlockEntity(pos) instanceof AbstractFancySporeBlossomBlockEntity blockEntity) {
            if (!level.isClientSide()) {
                if (heldItem.getItem() instanceof DyeItem dyeItem) {
                    blockEntity.mixColor(dyeItem.getDyeColor().getTextureDiffuseColors());
                    return InteractionResult.CONSUME;
                } else if (!blockEntity.isGlowing() && heldItem.getItem() == Items.GLOW_BERRIES) {
                    blockEntity.setGlowing(true);
                    level.setBlock(pos, state.setValue(LIT, true), 2);
                    return InteractionResult.CONSUME;
                } else if (blockEntity.isGlowing() && heldItem.getItem() == Items.SWEET_BERRIES) {
                    blockEntity.setGlowing(false);
                    level.setBlock(pos, state.setValue(LIT, false), 2);
                    return InteractionResult.CONSUME;
                }
            } else if (heldItem.getItem() instanceof DyeItem
                    || (!blockEntity.isGlowing() && heldItem.getItem() == Items.GLOW_BERRIES)
                    || (blockEntity.isGlowing() && heldItem.getItem() == Items.SWEET_BERRIES)) {
                if (!player.isCreative()) {
                    heldItem.setCount(heldItem.getCount() - 1);
                }
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }


}
