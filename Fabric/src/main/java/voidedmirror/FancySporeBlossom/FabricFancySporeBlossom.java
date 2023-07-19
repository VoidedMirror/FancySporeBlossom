package voidedmirror.FancySporeBlossom;

import com.mojang.serialization.Codec;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.impl.itemgroup.MinecraftItemGroups;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import voidedmirror.FancySporeBlossom.block.FabricFancySporeBlossomBlock;
import voidedmirror.FancySporeBlossom.block.entity.FabricFancySporeBlossomBlockEntity;
import voidedmirror.FancySporeBlossom.item.FabricFancySporeBlossomItem;
import voidedmirror.FancySporeBlossom.particle.FabricFancyAirParticleOptions;
import voidedmirror.FancySporeBlossom.particle.FabricFancyFallingParticleOptions;
import voidedmirror.FancySporeBlossom.recipe.FabricFancySporeBlossomRecipe;

import static voidedmirror.FancySporeBlossom.CommonClass.getID;

public class FabricFancySporeBlossom implements ModInitializer {
    public static FabricFancySporeBlossomBlock FANCY_SPORE_BLOSSOM_BLOCK;
    public static BlockEntityType<FabricFancySporeBlossomBlockEntity> FANCY_SPORE_BLOSSOM_BLOCK_ENTITY;
    public static FabricFancySporeBlossomItem FANCY_SPORE_BLOSSOM_ITEM;
    public static ParticleType<FabricFancyAirParticleOptions> FANCY_SPORE_BLOSSOM_AIR;
    public static ParticleType<FabricFancyFallingParticleOptions> FANCY_FALLING_SPORE_BLOSSOM;
    public static RecipeSerializer<FabricFancySporeBlossomRecipe> FANCY_SPORE_BLOSSOM_RECIPE;

    @Override
    public void onInitialize() {
        CommonClass.init();

        registerBlocks();
        registerEntities();
        registerItems();
        registerParticles();
        registerRecipes();

        ItemGroupEvents.modifyEntriesEvent(MinecraftItemGroups.NATURAL_ID).register(entries -> entries.addAfter(Items.SPORE_BLOSSOM, FANCY_SPORE_BLOSSOM_ITEM));
    }

    private void registerBlocks() {
        FANCY_SPORE_BLOSSOM_BLOCK = Registry.register(BuiltInRegistries.BLOCK, getID("fancy_spore_blossom"), new FabricFancySporeBlossomBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().noCollision().sounds(SoundType.SPORE_BLOSSOM).luminance(state -> state.getValue(BlockStateProperties.LIT) ? 12 : 0)));
    }

    private void registerEntities() {
        FANCY_SPORE_BLOSSOM_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, getID("fancy_spore_blossom_block_entity"), FabricBlockEntityTypeBuilder.create(FabricFancySporeBlossomBlockEntity::new, FANCY_SPORE_BLOSSOM_BLOCK).build(null));
    }

    private void registerItems() {
        FANCY_SPORE_BLOSSOM_ITEM = Registry.register(BuiltInRegistries.ITEM, getID("fancy_spore_blossom"), new FabricFancySporeBlossomItem(new FabricItemSettings()));
    }

    private void registerParticles() {
        FANCY_SPORE_BLOSSOM_AIR = Registry.register(BuiltInRegistries.PARTICLE_TYPE, getID("fancy_spore_blossom_air"), new ParticleType<FabricFancyAirParticleOptions>(false, FabricFancyAirParticleOptions.PARAMETERS_FACTORY) {
            @Override
            public Codec<FabricFancyAirParticleOptions> codec() {
                return FabricFancyAirParticleOptions.CODEC;
            }
        });
        FANCY_FALLING_SPORE_BLOSSOM = Registry.register(BuiltInRegistries.PARTICLE_TYPE, getID("fancy_falling_spore_blossom"), new ParticleType<FabricFancyFallingParticleOptions>(false, FabricFancyFallingParticleOptions.PARAMETERS_FACTORY) {
            @Override
            public Codec<FabricFancyFallingParticleOptions> codec() {
                return FabricFancyFallingParticleOptions.CODEC;
            }
        });
    }

    private void registerRecipes() {
        FANCY_SPORE_BLOSSOM_RECIPE = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, getID("crafting_special_fancy_spore_blossom"), new SimpleCraftingRecipeSerializer(FabricFancySporeBlossomRecipe::new));
    }
}
