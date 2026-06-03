package dev.vercim.handycam.fabric.client;

import dev.vercim.handycam.config.HandycamConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class HandycamConfigScreen {

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Component.literal("Handycam Settings"));

        HandycamConfig config = HandycamConfig.get();
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // Общие настройки
        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
        general.addEntry(entryBuilder.startFloatSlider(Component.literal("Master Intensity"), config.masterIntensity, 0f, 5f)
            .setDefaultValue(1.0f)
            .setSaveConsumer(v -> config.masterIntensity = v)
            .build());
        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Disable Vanilla Bob"), config.disableVanillaBob)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.disableVanillaBob = v)
            .build());
        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Pause on Menus"), config.pauseOnMenus)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.pauseOnMenus = v)
            .build());
        general.addEntry(entryBuilder.startFloatSlider(Component.literal("Creative Fly Multiplier"), config.creativeFlyMultiplier, 0f, 1f)
            .setDefaultValue(0.0f)
            .setSaveConsumer(v -> config.creativeFlyMultiplier = v)
            .build());
        general.addEntry(entryBuilder.startFloatSlider(Component.literal("Elytra Fly Multiplier"), config.elytraFlyMultiplier, 0f, 1f)
            .setDefaultValue(0.25f)
            .setSaveConsumer(v -> config.elytraFlyMultiplier = v)
            .build());

        // Настройки тряски
        ConfigCategory shake = builder.getOrCreateCategory(Component.literal("Shake"));

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Idle Enabled"), config.idleEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.idleEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatSlider(Component.literal("Idle Intensity"), config.idleIntensity, 0f, 3f)
            .setDefaultValue(1.5f)
            .setSaveConsumer(v -> config.idleIntensity = v)
            .build());
        shake.addEntry(entryBuilder.startFloatSlider(Component.literal("Idle Frequency"), config.idleFrequency, 0.1f, 2f)
            .setDefaultValue(0.5f)
            .setSaveConsumer(v -> config.idleFrequency = v)
            .build());

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Walk Bob Enabled"), config.walkBobEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.walkBobEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatSlider(Component.literal("Walk Bob Intensity"), config.walkBobIntensity, 0f, 4f)
            .setDefaultValue(2.2f)
            .setSaveConsumer(v -> config.walkBobIntensity = v)
            .build());
        shake.addEntry(entryBuilder.startFloatSlider(Component.literal("Walk Bob Frequency"), config.walkBobFrequency, 0.5f, 2f)
            .setDefaultValue(0.9f)
            .setSaveConsumer(v -> config.walkBobFrequency = v)
            .build());

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Landing Enabled"), config.landingEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.landingEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatSlider(Component.literal("Landing Intensity"), config.landingIntensity, 0f, 3f)
            .setDefaultValue(1.4f)
            .setSaveConsumer(v -> config.landingIntensity = v)
            .build());

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Damage Enabled"), config.damageEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.damageEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatSlider(Component.literal("Damage Intensity"), config.damageIntensity, 0f, 3f)
            .setDefaultValue(1.5f)
            .setSaveConsumer(v -> config.damageIntensity = v)
            .build());

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Sprint Enabled"), config.sprintEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.sprintEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatSlider(Component.literal("Turn Sway"), config.turnSway, 0f, 0.5f)
            .setDefaultValue(0.08f)
            .setSaveConsumer(v -> config.turnSway = v)
            .build());

        builder.setDefaultBackgroundTexture(null);
        builder.setSavingRunnable(() -> HandycamConfig.save());

        return builder.build();
    }
}
