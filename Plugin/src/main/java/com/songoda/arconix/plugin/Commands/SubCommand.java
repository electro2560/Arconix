package com.songoda.arconix.plugin.Commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    private final String name;
    private final String usage;
    private final String permission;
    private final String description;
    private final int length;

    protected SubCommand(String name, String usage, String permission, String description, int length) {
        this.name = name;
        this.usage = usage;
        this.permission = permission;
        this.description = description;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public int length() {
        return length;
    }

    public abstract void execute(Player sender, String[] args);
}