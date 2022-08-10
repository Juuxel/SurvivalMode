/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package juuxel.survivalmode;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gamemode.CreativeMode;
import com.mojang.minecraft.gamemode.GameMode;
import com.mojang.minecraft.gamemode.SurvivalMode;

public final class GameModes {
    private static Minecraft currentMinecraft;
    private static GameMode creative;
    private static GameMode survival;
    public static Type currentType = Type.SURVIVAL;

    public static GameMode forType(Minecraft minecraft, Type type) {
        switch (type) {
            case CREATIVE:
                return creative(minecraft);
            case SURVIVAL:
                return survival(minecraft);
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }

    public static GameMode creative(Minecraft minecraft) {
        GameMode result = creative;

        if (result == null || minecraft != currentMinecraft) {
            result = creative = new CreativeMode(minecraft);
            currentMinecraft = minecraft;
        }

        return result;
    }

    public static GameMode survival(Minecraft minecraft) {
        GameMode result = survival;

        if (result == null || minecraft != currentMinecraft) {
            try {
                result = survival = SurvivalMode.class.getConstructor(Minecraft.class).newInstance(minecraft);
                currentMinecraft = minecraft;
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Could not create survival mode!", e);
            }
        }

        return result;
    }

    public enum Type {
        CREATIVE("Creative"),
        SURVIVAL("Survival");

        private final String displayName;

        Type(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
