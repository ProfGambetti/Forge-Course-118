package net.valerio.mccourse.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valerio.mccourse.MCCourseMod;
import net.valerio.mccourse.util.ModTiers;
import net.valerio.mccourse.item.CobaltPickaxe;
import net.valerio.mccourse.item.CobaltSword;
//stefano
// ModItems gestisce tutti gli item della mod e li registra al gioco
public class ModItems {

    // Registro globale degli item della mod
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MCCourseMod.MOD_ID);

    // Lingotto di cobalto
    public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot",
            () -> new Item(new Item.Properties().tab(MCCourseMod.COURSE_TAB)));

    // Nugget di cobalto
    public static final RegistryObject<Item> COBALT_NUGGET = ITEMS.register("cobalt_nugget",
            () -> new Item(new Item.Properties().tab(MCCourseMod.COURSE_TAB)));

    // Cobalto grezzo (raw)
    public static final RegistryObject<Item> RAW_COBALT = ITEMS.register("raw_cobalt",
            () -> new Item(new Item.Properties().tab(MCCourseMod.COURSE_TAB)));

    // Spada di cobalto
    public static final RegistryObject<Item> COBALT_SWORD = ITEMS.register("cobalt_sword",
            () -> new CobaltSword(ModTiers.COBALT, 3, -2.4F,
                    new Item.Properties().tab(MCCourseMod.COURSE_TAB)));

    // Piccone di cobalto
    public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe",
            () -> new CobaltPickaxe(ModTiers.COBALT, 1, -2.8F,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    // Magic Stick (oggetto personalizzato creato da te)
    public static final RegistryObject<Item> MAGIC_STICK = ITEMS.register("magic_stick",
            () -> new MagicStick(new Item.Properties().tab(MCCourseMod.COURSE_TAB)));

    // Metodo per collegare il registro al ciclo di eventi di Forge
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}