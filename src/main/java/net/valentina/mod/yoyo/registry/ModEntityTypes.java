package net.valentina.mod.yoyo.registry;

import net.valentina.mod.yoyo.YoYoMod;
import net.valentina.mod.yoyo.entity.YoYoEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    // PER 1.18.1 usa ENTITIES (non ENTITY_TYPES)
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, YoYoMod.MOD_ID);

    public static final RegistryObject<EntityType<YoYoEntity>> YOYO =
            ENTITY_TYPES.register("yoyo",
                    () -> EntityType.Builder.<YoYoEntity>of(YoYoEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("yoyo"));
}