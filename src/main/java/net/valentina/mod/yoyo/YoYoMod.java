package net.valentina.mod.yoyo;

import net.valentina.mod.yoyo.registry.ModEntityTypes;
import net.valentina.mod.yoyo.registry.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//e
@Mod(YoYoMod.MOD_ID)
public class YoYoMod {
    public static final String MOD_ID = "yoyo";

    public YoYoMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Registra items ed entities
        ModItems.ITEMS.register(eventBus);
        ModEntityTypes.ENTITY_TYPES.register(eventBus); // ← Questo è importante!

        MinecraftForge.EVENT_BUS.register(this);
    }
}