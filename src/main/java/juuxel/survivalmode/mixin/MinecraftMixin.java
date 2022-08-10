/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package juuxel.survivalmode.mixin;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.MinecraftApplet;
import com.mojang.minecraft.gamemode.GameMode;
import juuxel.survivalmode.GameModes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Canvas;

@Mixin(Minecraft.class)
abstract class MinecraftMixin {
    @Shadow public GameMode gameMode;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstruct(Canvas applet, MinecraftApplet width, int height, int fullscreen, boolean par5, CallbackInfo info) {
        gameMode = GameModes.survival((Minecraft) (Object) this);
    }
}
