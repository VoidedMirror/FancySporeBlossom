package voidedmirror.FancySporeBlossom;

import com.mojang.serialization.Codec;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;

import org.jetbrains.annotations.NotNull;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import voidedmirror.FancySporeBlossom.block.ForgeFancySporeBlossomBlock;
import voidedmirror.FancySporeBlossom.block.entity.ForgeFancySporeBlossomBlockEntity;
import voidedmirror.FancySporeBlossom.item.ForgeFancySporeBlossomItem;
import voidedmirror.FancySporeBlossom.particle.ForgeFancyAirParticleOptions;
import voidedmirror.FancySporeBlossom.particle.ForgeFancyFallingParticleOptions;
import voidedmirror.FancySporeBlossom.recipe.ForgeFancySporeBlossomRecipe;

@Mod(Constants.MOD_ID)
public class ForgeFancySporeBlossom {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Constants.MOD_ID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MOD_ID);

    public static final RegistryObject<Block> FANCY_SPORE_BLOSSOM_BLOCK = BLOCKS.register("fancy_spore_blossom", () -> new ForgeFancySporeBlossomBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().noCollission().sound(SoundType.SPORE_BLOSSOM).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 12 : 0)));
    public static final RegistryObject<BlockEntityType<ForgeFancySporeBlossomBlockEntity>> FANCY_SPORE_BLOSSOM_BLOCK_ENTITY = BLOCK_ENTITY_TYPE.register("fancy_spore_blossom_block_entity", () -> BlockEntityType.Builder.of(ForgeFancySporeBlossomBlockEntity::new, FANCY_SPORE_BLOSSOM_BLOCK.get()).build(null));
    public static final RegistryObject<Item> FANCY_SPORE_BLOSSOM_ITEM = ITEMS.register("fancy_spore_blossom", () -> new ForgeFancySporeBlossomItem(new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final RegistryObject<ParticleType<ForgeFancyAirParticleOptions>> FANCY_SPORE_BLOSSOM_AIR = PARTICLE_TYPE.register("fancy_spore_blossom_air", () -> new ParticleType<>(false, ForgeFancyAirParticleOptions.PARAMETERS_FACTORY) {
        @Override
        public @NotNull Codec<ForgeFancyAirParticleOptions> codec() {
            return ForgeFancyAirParticleOptions.CODEC;
        }
    });
    public static final RegistryObject<ParticleType<ForgeFancyFallingParticleOptions>> FANCY_FALLING_SPORE_BLOSSOM = PARTICLE_TYPE.register("fancy_falling_spore_blossom", () -> new ParticleType<>(false, ForgeFancyFallingParticleOptions.PARAMETERS_FACTORY) {
        @Override
        public @NotNull Codec<ForgeFancyFallingParticleOptions> codec() {
            return ForgeFancyFallingParticleOptions.CODEC;
        }
    });
    public static final RegistryObject<RecipeSerializer<ForgeFancySporeBlossomRecipe>> FANCY_SPORE_BLOSSOM_RECIPE = RECIPE_SERIALIZER.register("crafting_special_fancy_spore_blossom", () -> new SimpleRecipeSerializer<>(ForgeFancySporeBlossomRecipe::new));

    public ForgeFancySporeBlossom() {
        CommonClass.init();

        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ENTITY_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        PARTICLE_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_SERIALIZER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}