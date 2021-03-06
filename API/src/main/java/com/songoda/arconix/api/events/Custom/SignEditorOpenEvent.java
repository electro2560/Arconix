package com.songoda.arconix.api.events.Custom;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Kiran Hart on 4/8/2017.
 */
public class SignEditorOpenEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;

    private Player p;
    private Sign sign;
    private String[] lines;
    private String line;


    public SignEditorOpenEvent(Player p, Sign sign) {
        this.p = p;
        this.sign = sign;
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

    public Sign getSign() {
        return sign;
    }

    public String[] getLines() {
        return sign.getLines();
    }

    public String getLine(int line) {
        return sign.getLine(line - 1);
    }
}
