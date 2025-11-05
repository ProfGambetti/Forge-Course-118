package net.valerio.mccourse.item;

import net.filippo.mccourse.items.TreeChopperAxe;
import net.filippo.mccourse.items.TunnelPickaxeItem;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valerio.mccourse.MCCourseMod;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    // =========================================================================
    // REGISTRAZIONE ITEMS
    // =========================================================================

    // Due oggetti di Filippo
    public static final RegistryObject<Item> TREE_CHOPPER_AXE = ITEMS.register("tree_chopper_axe",
            TreeChopperAxe::new);

    public static final RegistryObject<Item> TUNNEL_PICKAXE = ITEMS.register("tunnel_pickaxe",
            () -> new TunnelPickaxeItem(Tiers.DIAMOND, 1, -2.8F,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    // Le prossime istruzioni creano elementi del registro, ITEM
    public static final ArmaturaCobalto COBALT_ARMOR_MATERIAL = new ArmaturaCobalto();
    // Le prossime due istruzioni creano due elementi del registro, due ITEM
    // chiamati COBALT_INGOT e COBALT_NUGGET
    // Item.Properties ne definisce le caratteristiche
    // Sono aggiunte al tab "Vari" (TAB_MISC) del menu creativo
    // Il menù creativo è il pannello degli oggetti della modalità creativa di Minecraft
    public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> COBALT_NUGGET = ITEMS.register("cobalt_nugget",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // DA NOTARE CHE DEVO CREARE UN OGGETTO DELLA CLASSE MagicStick che ho creato io
  //  public static final RegistryObject<Item> MAGIC_STICK = ITEMS.register("magic_stick", () -> new MagicStick(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BRUSH = ITEMS.register("brush", () -> new Brush(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> MAGIC_STICK = ITEMS.register("magic_stick",
            () -> new MagicStick(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // =========================================================================
    // DEFINIZIONE DEL MATERIALE COBALTO PER STRUMENTI
    // =========================================================================

    /**
     * Tier (materiale) personalizzato per il cobalto
     * Implementazione custom dell'interfaccia Tier per definire le proprietà del cobalto
     */
    public static final Tier COBALT_TIER = new Tier() {
        @Override
        public int getUses() {
            // Durabilità totale: 1800 usi (come il diamante)
            return 1800;
        }
        @Override
        public float getSpeed() {
            // Velocità di mining: 15.0 (estremamente veloce)
            return 15.0f;
        }
        @Override
        public float getAttackDamageBonus() {
            // Danno d'attacco base: 4.0 (come il diamante)
            return 4.0f;
        }
        @Override
        public int getLevel() {
            // Livello del materiale: 3 (0=legno, 1=pietra, 2=ferro, 3=diamante, 4=netherite)
            return 3;
        }
        @Override
        public int getEnchantmentValue() {
            // Enchantability: 15 (come il diamante - facile da incantare)
            return 15;
        }
        @Override
        public Ingredient getRepairIngredient() {
            // Materiale per riparazione: lingotti di cobalto
            return Ingredient.of(COBALT_INGOT.get());
        }
    };



    /**
     * Registrazione del piccone di cobalto
     * - "cobalt_pickaxe": nome unico per la registrazione
     * - PickaxeItem: classe base di Minecraft per i picconi
     * - COBALT_TIER: il materiale che abbiamo definito sopra
     * - 1: danno d'attacco aggiuntivo
     * - -2.8f: velocità d'attacco (negativo = più lento, ma bilanciato dal danno)
     * - CreativeModeTab.TAB_TOOLS: appare nella tab strumenti del creative menu
     */
    public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe",
            () -> new PickaxeItem(
                    COBALT_TIER,           // materiale del piccone (tier personalizzato)
                    1,                     // danno d'attacco aggiuntivo (si somma a quello del tier)
                    -2.8f,                 // velocità d'attacco (valori negativi = attacco più lento)
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS) // proprietà: appare in tab strumenti
            ));

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



    //aggiungendo l'oggetto "Summon whistle"
    public static final RegistryObject<Item> SUMMON_WHISTLE = ITEMS.register("summon_whistle", () -> new SummonWhistle(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    //aggiungendo l'oggetto "Summon whistle"
    public static final RegistryObject<Item> SUMMON_WHISTLE_LVL2 = ITEMS.register("summon_whistle_lvl2", () -> new Summon_whistle_lvl2(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));


    public static final RegistryObject<Item> MAGICLANTERNITEM = ITEMS.register("magic_lantern", () -> new net.valerio.mccourse.item.MagicLanternItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // Registriamo la nostra spada personalizzata
    // "knockback_sword" sarà il nome interno dell'item
    // RegistryObject è un wrapper che ci dà accesso sicuro al nostro item registrato
    public static final RegistryObject<Item> KNOCKBACK_SWORD = ITEMS.register("knockback_sword",
            () -> new KnockbackSword()); // Lambda che crea una nuova istanza quando serve

    // Collega gli elementi creati con il ciclo di eventi di FORGE
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }


    /*
    FONDAMENTALI:
    Creare un registro per gli oggetti → DeferredRegister<Item> serve per dire a Minecraft: "Qui registrerò i miei oggetti".
    Definire nuovi oggetti → ogni RegistryObject<Item> (es. COBALT_INGOT, COBALT_NUGGET) rappresenta un nuovo oggetto del gioco.
    Organizzare gli oggetti nel menu creativo → con new Item.Properties().tab(...) si sceglie in quale sezione del menu creativo compaiono.
    Registrare gli oggetti al gioco → il metodo register(IEventBus eventBus) collega il registro degli oggetti al ciclo di avvio del gioco.
     */

    public class MagicLanternItem extends Item
    {
        private final Map<UUID, Integer> immuneMap = new HashMap<>();
        public MagicLanternItem(Properties props)
        {
            super(props);
        }

        public static final RegistryObject<Item> MAGIC_LANTERN = ITEMS.register("magic_lantern",
                () -> new net.valerio.mccourse.item.MagicLanternItem(new Item.Properties().stacksTo(1).durability(36000)));

    }


}


