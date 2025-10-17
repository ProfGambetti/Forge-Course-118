package net.valerio.mccourse.item;

import net.minecraft.world.entity.projectile.ThrownItemProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.damagesource.DamageSource;
import com.example.spearmod.ModItems;

public class SpearEntity extends ThrownItemProjectile {
    public SpearEntity(EntityType<? extends SpearEntity> type, Level level) {
        super(type, level);
    }

    public SpearEntity(Level level, LivingEntity thrower) {
        super(ModEntities.SPEAR.get(), thrower, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SPEAR.get(); // What item this projectile visually represents
    }

    // Called when hitting an entity
    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 8.0F); // 8 damage
        this.discard(); // Remove spear after hit
    }
}