package net.valerio.mccourse.item;

import net.minecraftforge.registries.*;
import net.minecraft.world.entity.*;
import net.mccourse.spearmod.entity.SpearEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SpearMod.MODID);

    public static final RegistryObject<EntityType<SpearEntity>> SPEAR =
            ENTITIES.register("spear",
                    () -> EntityType.Builder.<SpearEntity>of(SpearEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("spear"));
}
