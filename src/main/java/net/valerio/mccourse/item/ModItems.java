package net.valerio.mccourse.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valerio.mccourse.MCCourseMod;
import net.valerio.mccourse.item.Hamburger;
import net.valerio.mccourse.item.MagicStick;
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
    public static final ArmaturaCobalto COBALT_ARMOR_MATERIAL = new ArmaturaCobalto();
    // Le prossime due istruzioni creano due elementi del registro, due ITEM
    // chiamati COBALT_INGOT e COBALT_NUGGET
    // Item.Properties ne definisce le caratteristiche
    // Sono aggiunte al tab "Vari" (TAB_MISC) del menu creativo
    // Il menù creativo è il pannello degli oggetti della modalità creativa di Minecraft
    public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> COBALT_NUGGET = ITEMS.register("cobalt_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // DA NOTARE CHE DEVO CREARE UN OGGETTO DELLA CLASSE MagicStick che ho creato io
    public static final RegistryObject<Item> MAGIC_STICK = ITEMS.register("magic_stick", () -> new MagicStick(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BRUSH = ITEMS.register("brush", () -> new Brush(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // REGISTRAZIONE DEL NUOVO HAMBURGER
    // Crea un elemento del registro chiamato HAMBURGER
    // Utilizza la classe Hamburger personalizzata che abbiamo creato
    // Viene aggiunto al tab "Cibo" (TAB_FOOD) del menu creativo

    public static final RegistryObject<Item> HAMBURGER = ITEMS.register("hamburger",
            () -> new Hamburger());  // Solo questo, niente Properties qui
    public static final RegistryObject<Item> COBALT_HELMET = ITEMS.register("cobalt_helmet",
            () -> new ArmorItem(COBALT_ARMOR_MATERIAL, EquipmentSlot.HEAD,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> COBALT_CHESTPLATE = ITEMS.register("cobalt_chestplate",
            () -> new ArmorItem(COBALT_ARMOR_MATERIAL, EquipmentSlot.CHEST,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> COBALT_LEGGINGS = ITEMS.register("cobalt_leggings",
            () -> new ArmorItem(COBALT_ARMOR_MATERIAL, EquipmentSlot.LEGS,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> COBALT_BOOTS = ITEMS.register("cobalt_boots",
            () -> new ArmorItem(COBALT_ARMOR_MATERIAL, EquipmentSlot.FEET,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    // Collega gli elementi creati con il ciclo di eventi di FORGE
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }


}