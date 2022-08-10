/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package juuxel.survivalmode.mixin;

import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.NewLevelScreen;
import com.mojang.minecraft.gui.Screen;
import juuxel.survivalmode.GameModes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NewLevelScreen.class)
abstract class NewLevelScreenMixin extends Screen {
    @Unique
    private int gameModeButtonIndex;

    @Inject(method = "init", at = @At("RETURN"))
    private void onInit(CallbackInfo info) {
        gameModeButtonIndex = buttons.size();
        buttons.add(new Button(gameModeButtonIndex, width / 2 - 100, height / 4 + 24 * 3, "Game mode: " + GameModes.currentType.getDisplayName()));
    }

    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    private void onButtonClicked(Button button, CallbackInfo info) {
        if (button.id == gameModeButtonIndex) {
            GameModes.Type[] allGameModes = GameModes.Type.values();
            int nextIndex = (GameModes.currentType.ordinal() + 1) % allGameModes.length;
            GameModes.Type next = allGameModes[nextIndex];
            GameModes.currentType = next;
            button.msg = "Game mode: " + next.getDisplayName();
            info.cancel();
        } else if (button.id < 3) {
            minecraft.gameMode = GameModes.forType(minecraft, GameModes.currentType);
        }
    }
}
