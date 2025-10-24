package net.valerio.mccourse.yoyo.registry;

import net.valerio.mccourse.yoyo.YoYoMod;
import net.valerio.mccourse.item.YoYoItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, YoYoMod.MOD_ID);

    public static final RegistryObject<Item> YOYO = ITEMS.register("yoyo",
            () -> new YoYoItem(new Item.Properties()
                    .tab(net.minecraft.world.item.CreativeModeTab.TAB_COMBAT)
                    .stacksTo(1)
                    .durability(256)));
}