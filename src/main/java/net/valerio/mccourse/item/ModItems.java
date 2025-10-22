package net.valerio.mccourse.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valerio.mccourse.MCCourseMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MCCourseMod.MOD_ID);

    // Item esistenti
    public static final RegistryObject<Item> COBALT_INGOT = ITEMS.register("cobalt_ingot",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> COBALT_NUGGET = ITEMS.register("cobalt_nugget",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> MAGIC_STICK = ITEMS.register("magic_stick",
            () -> new MagicStick(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // Nuova Spada Magica - nel tab COMBAT
    public static final RegistryObject<Item> MAGIC_SWORD = ITEMS.register("magic_sword",
            () -> new MagicSword(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(500)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}