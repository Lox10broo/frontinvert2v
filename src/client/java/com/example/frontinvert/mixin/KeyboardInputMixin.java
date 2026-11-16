package com.example.frontinvert.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends ClientInput {
    @Inject(method = "tick", at = @At("TAIL"))
    private void frontInvert(CallbackInfo ci) {
        if (!Minecraft.getInstance().options.getCameraType().isMirrored()) return;
        Input k = this.keyPresses;
        this.keyPresses = new Input(k.forward(), k.backward(), k.right(), k.left(),
                k.jump(), k.shift(), k.sprint());
        Vec2 m = this.moveVector;
        this.moveVector = new Vec2(-m.x, m.y);
    }
}
