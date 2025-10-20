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
// ModBlocks ha i seguenti scopi:
//       Registra tutti i blocchi della MOD
//       Crea i corrispondenti oggetti inserendoli nell'inventario creativo
//       Collega i blocchi al sistema di eventi Forge
public class ModBlocks {

    // Collega il registro dei blocchi della MOD con quello globale di Minecraft
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MCCourseMod.MOD_ID);

    // Crea un blocco chiamato COBALT_BLOCK, usa BlockBehaviour.Properties per impostare
    // caratteristiche come il tipo di materiale, la resistenza del materiale, la
    // necessità di usare un utensile per ottenere il drop
    // Lo si inserisce nella scheda VARI (TAB_MISC) del menu creativo
    public static final RegistryObject<Block> COBALT_BLOCK = registerBlock("cobalt_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5f).requiresCorrectToolForDrops()), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> KRYPTONITE_BLOCK = registerBlock("kryptonite_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5f).requiresCorrectToolForDrops()), CreativeModeTab.TAB_MISC);

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

    /*
    FONDAMENTALI:
    Creare un registro per i blocchi → DeferredRegister<Block> prepara l’elenco dei blocchi personalizzati.
    Definire nuovi blocchi → ogni RegistryObject<Block> (es. COBALT_BLOCK) rappresenta un nuovo blocco del gioco.
    Associare un “BlockItem” → tramite registerBlockItem(...) si crea l’oggetto corrispondente al blocco, così da poterlo usare/inserire nell’inventario.
    Organizzare i blocchi nel menu creativo → con new Item.Properties().tab(...) si decide dove appare il blocco nel menu creativo.
    Registrare i blocchi al gioco → il metodo register(IEventBus eventBus) collega il registro dei blocchi al sistema di avvio del gioco.
     */
}
