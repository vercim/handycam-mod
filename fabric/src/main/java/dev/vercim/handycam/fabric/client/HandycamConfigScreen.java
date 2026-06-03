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
        general.addEntry(entryBuilder.startFloatField(Component.literal("Master Intensity"), config.masterIntensity)
            .setDefaultValue(1.0f)
            .setSaveConsumer(v -> config.masterIntensity = v)
            .setMin(0f).setMax(5f)
            .build());
        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Disable Vanilla Bob"), config.disableVanillaBob)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.disableVanillaBob = v)
            .build());
        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Pause on Menus"), config.pauseOnMenus)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.pauseOnMenus = v)
            .build());
        general.addEntry(entryBuilder.startFloatField(Component.literal("Creative Fly Multiplier"), config.creativeFlyMultiplier)
            .setDefaultValue(0.0f)
            .setSaveConsumer(v -> config.creativeFlyMultiplier = v)
            .setMin(0f).setMax(1f)
            .build());
        general.addEntry(entryBuilder.startFloatField(Component.literal("Elytra Fly Multiplier"), config.elytraFlyMultiplier)
            .setDefaultValue(0.25f)
            .setSaveConsumer(v -> config.elytraFlyMultiplier = v)
            .setMin(0f).setMax(1f)
            .build());

        // Настройки тряски
        ConfigCategory shake = builder.getOrCreateCategory(Component.literal("Shake"));

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Idle Enabled"), config.idleEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.idleEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatField(Component.literal("Idle Intensity"), config.idleIntensity)
            .setDefaultValue(1.5f)
            .setSaveConsumer(v -> config.idleIntensity = v)
            .setMin(0f).setMax(3f)
            .build());
        shake.addEntry(entryBuilder.startFloatField(Component.literal("Idle Frequency"), config.idleFrequency)
            .setDefaultValue(0.5f)
            .setSaveConsumer(v -> config.idleFrequency = v)
            .setMin(0.1f).setMax(2f)
            .build());

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Walk Bob Enabled"), config.walkBobEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.walkBobEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatField(Component.literal("Walk Bob Intensity"), config.walkBobIntensity)
            .setDefaultValue(2.2f)
            .setSaveConsumer(v -> config.walkBobIntensity = v)
            .setMin(0f).setMax(4f)
            .build());
        shake.addEntry(entryBuilder.startFloatField(Component.literal("Walk Bob Frequency"), config.walkBobFrequency)
            .setDefaultValue(0.9f)
            .setSaveConsumer(v -> config.walkBobFrequency = v)
            .setMin(0.5f).setMax(2f)
            .build());

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Landing Enabled"), config.landingEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.landingEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatField(Component.literal("Landing Intensity"), config.landingIntensity)
            .setDefaultValue(1.4f)
            .setSaveConsumer(v -> config.landingIntensity = v)
            .setMin(0f).setMax(3f)
            .build());

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Damage Enabled"), config.damageEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.damageEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatField(Component.literal("Damage Intensity"), config.damageIntensity)
            .setDefaultValue(1.5f)
            .setSaveConsumer(v -> config.damageIntensity = v)
            .setMin(0f).setMax(3f)
            .build());

        shake.addEntry(entryBuilder.startBooleanToggle(Component.literal("Sprint Enabled"), config.sprintEnabled)
            .setDefaultValue(true)
            .setSaveConsumer(v -> config.sprintEnabled = v)
            .build());
        shake.addEntry(entryBuilder.startFloatField(Component.literal("Turn Sway"), config.turnSway)
            .setDefaultValue(0.08f)
            .setSaveConsumer(v -> config.turnSway = v)
            .setMin(0f).setMax(0.5f)
            .build());

        builder.setDefaultBackgroundTexture(null);
        builder.setSavingRunnable(() -> HandycamConfig.save());

        return builder.build();
    }
}
