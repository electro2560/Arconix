package com.songoda.arconix.api.events.Custom;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Kiran Hart on 4/12/2017.
 */
public class RegionCreateEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;

    private Player p;
    private String regionName;
    private Location locationOne;
    private Location locationTwo;

    public RegionCreateEvent(Player p, String regionName, Location locationOne, Location locationTwo) {
        this.p = p;
        this.regionName = regionName;
        this.locationOne = locationOne;
        this.locationTwo = locationTwo;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public Player getPlayer() {
        return p;
    }

    public String getRegionName() {
        return regionName;
    }

    public Location getLocationOne() {
        return locationOne;
    }

    public Location getLocationTwo() {
        return locationTwo;
    }
}
