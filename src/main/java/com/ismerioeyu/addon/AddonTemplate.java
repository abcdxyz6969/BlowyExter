package com.ismerioeyu.blowyexter;

import com.ismerioeyu.blowyexter.commands.CommandExample;
import com.ismerioeyu.blowyexter.hud.HudExample;
import com.ismerioeyu.blowyexter.modules.ModuleTest;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class AddonTemplate extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Example");
    public static final HudGroup HUD_GROUP = new HudGroup("Example");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Meteor Addon BlowyExter");

        // Modules
        Modules.get().add(new ModuleTest());

        // Commands
        Commands.add(new CommandExample());

        // HUD
        Hud.get().register(HudExample.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.ismerioeyu.blowyexter";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("adcdxyz6969", "BlowyExter");
    }
}
