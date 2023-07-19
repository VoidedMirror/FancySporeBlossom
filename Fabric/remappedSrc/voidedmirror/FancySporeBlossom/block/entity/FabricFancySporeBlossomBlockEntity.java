package voidedmirror.FancySporeBlossom.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import voidedmirror.FancySporeBlossom.FabricFancySporeBlossom;

public class FabricFancySporeBlossomBlockEntity extends AbstractFancySporeBlossomBlockEntity {
    public FabricFancySporeBlossomBlockEntity(BlockPos pos, BlockState state) {
        super(FabricFancySporeBlossom.FANCY_SPORE_BLOSSOM_BLOCK_ENTITY, pos, state);
    }
}
