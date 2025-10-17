
//ziad chihab 4CI
package net.valerio.mccourse;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valerio.mccourse.block.ModBlocks;
import net.valerio.mccourse.item.ModItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// La MOD_ID usato qui deve essere uguale a quello scritto nel file META-INF/mods.toml
// La MOD_ID è una stringa scritta in minuscolo e senza spazi
// L'annotazione @Mod comunica a Forge che questa è la classe principale della MOD
@Mod(MCCourseMod.MOD_ID)

//commento di Francesco Lacava
//ccommmento di micolRRIGHIii
//Commento Di Stefano Liviero
//
public class MCCourseMod //la mod di marco parini
{
    // Definisce il MOD ID
    //ciao guys1
    public static final String MOD_ID = "mccourse";

    // Stampa i messaggi di LOG
    private static final Logger LOGGER = LogManager.getLogger();
    // Test change for GIT version 2
    // Third test change for GIT
    // Third committ
    public MCCourseMod() {

        // Carico il sistema di eventi Forge, chiamato eventBus
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Registro items e blocchi
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        System.out.println("Ho registrato blocchi ed eventi in MCCourseMod.java");
        // Aggiungo il metodo setup, definito più in basso
        eventBus.addListener(this::setup);


        // La Mod si iscrive al bus degli eventi di Minecraft stesso, non solo FORGE
        MinecraftForge.EVENT_BUS.register(this);
    }

    // FORGE chiama questo metodo in fase di pre-inizializzazione della mod
    private void setup(final FMLCommonSetupEvent event)
    {
        // Stampo semplicemente due messaggi, nel secondo stampa il nome del blocco terra
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    // FONDAMENTALI:
    /*
        MCCourseMod = punto d’ingresso del mod → qui parte tutto.
        @Mod lega la classe a Forge.
        MOD_ID è l’identità del mod (univoca).
        Il costruttore registra blocchi, oggetti ed eventi.
        Il setup prepara eventuali configurazioni iniziali.
        Il LOGGER serve a capire cosa succede nel caricamento (debug).
     */

}
