package net.valerio.mccourse.yoyo.entity;

import net.valerio.mccourse.yoyo.registry.ModEntityTypes;
import net.valerio.mccourse.yoyo.registry.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class YoYoEntity extends ThrowableItemProjectile {
    private static final int MAX_LIFETIME = 100; // 5 secondi
    private static final double MAX_DISTANCE = 15.0;
    private int ticksInAir = 0;
    private boolean isReturning = false;

    public YoYoEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public YoYoEntity(Level level, LivingEntity shooter) {
        super(ModEntityTypes.YOYO.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.YOYO.get();
    }

    @Override
    public void tick() {
        super.tick();
        ticksInAir++;

        if (ticksInAir >= MAX_LIFETIME && !isReturning) {
            startReturning();
        }

        if (isReturning) {
            handleReturn();
        } else {
            checkMaxDistance();
        }

        applyEffects();
    }

    private void checkMaxDistance() {
        Entity owner = getOwner();
        if (owner != null) {
            double distance = distanceTo(owner);
            if (distance > MAX_DISTANCE) {
                startReturning();
            }
        }
    }

    private void startReturning() {
        isReturning = true;
        setNoGravity(true);
    }

    private void handleReturn() {
        Entity owner = getOwner();
        if (owner != null) {
            Vec3 toOwner = owner.position().subtract(this.position());

            if (toOwner.length() < 1.5) {
                this.discard();
                return;
            }

            Vec3 motion = toOwner.normalize().scale(0.5);
            this.setDeltaMovement(motion);
        } else {
            this.discard();
        }
    }

    private void applyEffects() {
        if (!level.isClientSide) {
            level.getEntities(this, this.getBoundingBox().inflate(1.5))
                    .forEach(entity -> {
                        if (entity instanceof LivingEntity living && entity != getOwner()) {
                            living.hurt(DamageSource.thrown(this, getOwner()), 2.0F);

                            Vec3 push = entity.position().subtract(this.position()).normalize();
                            entity.setDeltaMovement(push.x * 0.3, 0.2, push.z * 0.3);
                        }
                    });
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!level.isClientSide) {
            Entity entity = result.getEntity();
            if (entity instanceof LivingEntity living && entity != getOwner()) {
                living.hurt(DamageSource.thrown(this, getOwner()), 5.0F);

                Vec3 push = entity.position().subtract(this.position()).normalize();
                entity.setDeltaMovement(push.x * 0.8, 0.4, push.z * 0.8);
            }

            startReturning();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!level.isClientSide && !isReturning) {
            startReturning();
        }
    }

    @Override
    protected float getGravity() {
        return isReturning ? 0.0F : 0.03F;
    }
}