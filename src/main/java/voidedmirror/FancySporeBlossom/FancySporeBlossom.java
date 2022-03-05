package voidedmirror.FancySporeBlossom;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import voidedmirror.FancySporeBlossom.block.FancySporeBlossomBlock;
import voidedmirror.FancySporeBlossom.block.entity.FancySporeBlossomBlockEntity;
import voidedmirror.FancySporeBlossom.item.FancySporeBlossomItem;

public class FancySporeBlossom implements ModInitializer {
    public static final String MOD_ID = "fancysporeblossom";

    public static FancySporeBlossomBlock FANCY_SPORE_BLOSSOM_BLOCK;
    public static BlockEntityType<FancySporeBlossomBlockEntity> FANCY_SPORE_BLOSSOM_BLOCK_ENTITY;
    public static FancySporeBlossomItem FANCY_SPORE_BLOSSOM_ITEM;

    @Override
    public void onInitialize() {
        registerBlocks();
        registerEntities();
        registerItems();
    }

    public void registerBlocks() {
        FANCY_SPORE_BLOSSOM_BLOCK = Registry.register(Registry.BLOCK, getID("fancy_spore_blossom"), new FancySporeBlossomBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().noCollision().sounds(BlockSoundGroup.SPORE_BLOSSOM)));
    }

    private void registerEntities() {
        FANCY_SPORE_BLOSSOM_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, getID("fancy_spore_blossom_block_entity"), FabricBlockEntityTypeBuilder.create(FancySporeBlossomBlockEntity::new, FANCY_SPORE_BLOSSOM_BLOCK).build(null));
    }

    private void registerItems() {
        FANCY_SPORE_BLOSSOM_ITEM = Registry.register(Registry.ITEM, getID("fancy_spore_blossom"), new FancySporeBlossomItem(new FabricItemSettings().group(ItemGroup.DECORATIONS)));
    }

    public Identifier getID(String string) {
        return new Identifier(MOD_ID, string);
    }

}
