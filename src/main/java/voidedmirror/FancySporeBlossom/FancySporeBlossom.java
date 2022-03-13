package voidedmirror.FancySporeBlossom;

import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.ParticleType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import voidedmirror.FancySporeBlossom.block.FancySporeBlossomBlock;
import voidedmirror.FancySporeBlossom.block.entity.FancySporeBlossomBlockEntity;
import voidedmirror.FancySporeBlossom.item.FancySporeBlossomItem;
import voidedmirror.FancySporeBlossom.particle.FancyAirParticleEffect;
import voidedmirror.FancySporeBlossom.particle.FancyFallingParticleEffect;
import voidedmirror.FancySporeBlossom.recipe.FancySporeBlossomRecipe;

public class FancySporeBlossom implements ModInitializer {
    public static final String MOD_ID = "fancysporeblossom";

    public static FancySporeBlossomBlock FANCY_SPORE_BLOSSOM_BLOCK;
    public static BlockEntityType<FancySporeBlossomBlockEntity> FANCY_SPORE_BLOSSOM_BLOCK_ENTITY;
    public static FancySporeBlossomItem FANCY_SPORE_BLOSSOM_ITEM;
    public static ParticleType<FancyAirParticleEffect> FANCY_SPORE_BLOSSOM_AIR;
    public static ParticleType<FancyFallingParticleEffect> FANCY_FALLING_SPORE_BLOSSOM;
    public static SpecialRecipeSerializer<FancySporeBlossomRecipe> FANCY_SPORE_BLOSSOM_RECIPE;

    @Override
    public void onInitialize() {
        registerBlocks();
        registerEntities();
        registerItems();
        registerParticles();
        registerRecipes();
    }

    private void registerBlocks() {
        FANCY_SPORE_BLOSSOM_BLOCK = Registry.register(Registry.BLOCK, getID("fancy_spore_blossom"), new FancySporeBlossomBlock(FabricBlockSettings.of(Material.PLANT).breakInstantly().noCollision().sounds(BlockSoundGroup.SPORE_BLOSSOM).luminance(state -> state.get(Properties.LIT) ? 12 : 0)));
    }

    private void registerEntities() {
        FANCY_SPORE_BLOSSOM_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, getID("fancy_spore_blossom_block_entity"), FabricBlockEntityTypeBuilder.create(FancySporeBlossomBlockEntity::new, FANCY_SPORE_BLOSSOM_BLOCK).build(null));
    }

    private void registerItems() {
        FANCY_SPORE_BLOSSOM_ITEM = Registry.register(Registry.ITEM, getID("fancy_spore_blossom"), new FancySporeBlossomItem(new FabricItemSettings().group(ItemGroup.DECORATIONS)));
    }

    private void registerParticles() {
        FANCY_SPORE_BLOSSOM_AIR = Registry.register(Registry.PARTICLE_TYPE, getID("fancy_spore_blossom_air"), new ParticleType<FancyAirParticleEffect>(false, FancyAirParticleEffect.PARAMETERS_FACTORY) {
            @Override
            public Codec<FancyAirParticleEffect> getCodec() {
                return FancyAirParticleEffect.CODEC;
            }
        });
        FANCY_FALLING_SPORE_BLOSSOM = Registry.register(Registry.PARTICLE_TYPE, getID("fancy_falling_spore_blossom"), new ParticleType<FancyFallingParticleEffect>(false, FancyFallingParticleEffect.PARAMETERS_FACTORY) {
            @Override
            public Codec<FancyFallingParticleEffect> getCodec() {
                return FancyFallingParticleEffect.CODEC;
            }
        });
    }

    private void registerRecipes() {
        FANCY_SPORE_BLOSSOM_RECIPE = Registry.register(Registry.RECIPE_SERIALIZER, getID("crafting_special_fancy_spore_blossom"), new SpecialRecipeSerializer<>(FancySporeBlossomRecipe::new));
    }

    public static Identifier getID(String string) {
        return new Identifier(MOD_ID, string);
    }
}
