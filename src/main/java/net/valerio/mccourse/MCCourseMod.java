package net.valerio.mccourse;

import net.valerio.mccourse.block.ModBlocks;
import net.valerio.mccourse.item.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MCCourseMod.MOD_ID)
public class MCCourseMod {
    public static final String MOD_ID = "mccourse";

    public MCCourseMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Registra SOLO la mod principale per ora
        ModBlocks.BLOCKS.register(eventBus);
        ModItems.ITEMS.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}