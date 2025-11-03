package net.valerio.mccourse.block;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;


import net.minecraftforge.registries.RegistryObject;
import net.valerio.mccourse.MCCourseMod;
import net.valerio.mccourse.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MCCourseMod.MOD_ID);

    // 🔥 TEMPORANEAMENTE: usa Block normale invece di classi custom
    public static final RegistryObject<Block> BLOCK_OF_COBALT = registerBlock(
            "block_of_cobalt",
            () -> new BlockOfCobalt(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2.5f)
                    .requiresCorrectToolForDrops())
    );


    public static final RegistryObject<Block> COBALT_ORE = registerBlock(
            "cobalt_ore",
            () -> new CobaltOreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2.5f)
                    .requiresCorrectToolForDrops())
    );

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(MCCourseMod.COURSE_TAB)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
