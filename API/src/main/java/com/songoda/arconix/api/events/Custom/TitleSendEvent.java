package com.songoda.arconix.api.events.Custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Kiran Hart on 4/8/2017.
 */
public class TitleSendEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled;

    private Player p;
    private String msg;
    private int fadeInTime;
    private int fadeOutTime;
    private int stayTime;
    private String titleType;

    public TitleSendEvent(Player p, String msg, int fadeInTime, int stayTime, int fadeOutTime, String titleType) {
        this.p = p;
        this.msg = msg;
        this.fadeInTime = fadeInTime;
        this.fadeOutTime = fadeOutTime;
        this.stayTime = stayTime;
        this.titleType = titleType;
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

    public String getMessage() {
        return msg;
    }

    public String getTitleType() {
        return titleType;
    }

    public int getFadeInTime() {
        return fadeInTime;
    }

    public int getFadeOutTime() {
        return fadeOutTime;
    }

    public int getStayTime() {
        return stayTime;
    }
}
