package net.valerio.mccourse.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valerio.mccourse.MCCourseMod;
import net.valerio.mccourse.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MCCourseMod.MOD_ID);

    // Blocco cobalto esistente
    public static final RegistryObject<Block> COBALT_BLOCK = registerBlock("cobalt_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5f).requiresCorrectToolForDrops()),
            CreativeModeTab.TAB_MISC);

    // NUOVO: Blocco di cristallo arcano
    public static final RegistryObject<Block> ARCANE_CRYSTAL_BLOCK = registerBlock("arcane_crystal_block",
            () -> new ArcaneCrystalBlock(),
            CreativeModeTab.TAB_BUILDING_BLOCKS);

    // Registra il blocco
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    // Crea un oggetto corrispondente al blocco registrato
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    // Collega i blocchi al ciclo di eventi FORGE
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}