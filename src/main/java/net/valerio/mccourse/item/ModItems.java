package net.valerio.mccourse.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valerio.mccourse.MCCourseMod;

// ModItems ha i seguenti scopi:
//     Definisce tutti i nuovi elementi della mod (lingotti, polveri, cibi, strumenti, etc.)
//     Registra tutti i nuovi elementi della mod
//     Collega gli oggetti al sistema degli eventi FORGE chiamato EVENT BUS
public class ModItems {

    // Crea un registro di oggetti per la MOD all'interno del registro globale di Minecraft
    // MOD_ID è una stringa univoca della MOD scelta dal modder
    // che deve essere presenta nel file mods.toml
    // tutta minuscola e senza spazi
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MCCourseMod.MOD_ID);

    // Le prossime due istruzioni creano due elementi del registro, due ITEM
    // chiamati COBALT_INGOT e COBALT_NUGGET
    // Item.Properties ne definisce le caratteristiche
    // Sono aggiunte al tab "Vari" (TAB_MISC) del menu creativo
    // Il menù creativo è il pannello degli oggetti della modalità creativa di Minecraft
    public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> COBALT_NUGGET = ITEMS.register("cobalt_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> YOYO = ITEMS.register("yoyo", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> PALLINA = ITEMS.register("pallina", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    // DA NOTARE CHE DEVO CREARE UN OGGETTO DELLA CLASSE MagicStick che ho creato io
    public static final RegistryObject<Item> MAGIC_STICK = ITEMS.register("magic_stick", () -> new MagicStick(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // Collega gli elementi creati con il ciclo di eventi di FORGE
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

    /*
    FONDAMENTALI:
    Creare un registro per gli oggetti → DeferredRegister<Item> serve per dire a Minecraft: “Qui registrerò i miei oggetti”.
    Definire nuovi oggetti → ogni RegistryObject<Item> (es. COBALT_INGOT, COBALT_NUGGET) rappresenta un nuovo oggetto del gioco.
    Organizzare gli oggetti nel menu creativo → con new Item.Properties().tab(...) si sceglie in quale sezione del menu creativo compaiono.
    Registrare gli oggetti al gioco → il metodo register(IEventBus eventBus) collega il registro degli oggetti al ciclo di avvio del gioco.
     */

}
