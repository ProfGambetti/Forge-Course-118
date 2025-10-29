package net.valerio.mccourse;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valerio.mccourse.block.ModBlocks;
import net.valerio.mccourse.item.ModItems;
import net.valerio.mccourse.command.FindCobaltCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//mod di stefano
@Mod(MCCourseMod.MOD_ID)
public class MCCourseMod {
    public static final String MOD_ID = "mccourse";
    private static final Logger LOGGER = LogManager.getLogger();

    public static final CreativeModeTab COURSE_TAB = new CreativeModeTab(MOD_ID + "_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.COBALT_SWORD.get());
        }
    };

    public MCCourseMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // REGISTRA SOLO ITEMS E BLOCCHI
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        LOGGER.info("✅ ModItems e ModBlocks registrati!");

        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);

        // REGISTRA I COMANDI
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        new FindCobaltCommand(event.getDispatcher());
        LOGGER.info("✅ Comando /findcobalt registrato!");
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("🔧 Setup MCCourseMod avviato!");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}