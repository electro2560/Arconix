package com.songoda.arconix.packets;

import com.songoda.arconix.Arconix;
import com.songoda.arconix.packets.ActionBar.ActionBar;
import com.songoda.arconix.packets.Particle.Particle;
import com.songoda.arconix.packets.Hologram.Hologram;

/**
 * Created by songoda on 3/16/2017.
 */
public class PacketLibrary {

    Arconix plugin = Arconix.pl();


    private Particle particleManager;
    private ActionBar actionBarManager;
    private Hologram hologramManager;


    public void setupPackets() {
        switch (plugin.serverVersion) {
            case "v1_11_R1":
                this.particleManager = new com.songoda.arconix.packets.Particle.Particle1_11R1();
                this.actionBarManager = new com.songoda.arconix.packets.ActionBar.ActionBar1_11R1();
                this.hologramManager = new com.songoda.arconix.packets.Hologram.Hologram1_11R1();
                break;
            case "v1_10_R1":
                this.particleManager = new com.songoda.arconix.packets.Particle.Particle1_10R1();
                this.actionBarManager = new com.songoda.arconix.packets.ActionBar.ActionBar1_10R1();
                break;
            case "v1_9_R2":
                this.particleManager = new com.songoda.arconix.packets.Particle.Particle1_9R2();
                this.actionBarManager = new com.songoda.arconix.packets.ActionBar.ActionBar1_9R2();
                break;
            case "v1_9_R1":
                this.particleManager = new com.songoda.arconix.packets.Particle.Particle1_9R1();
                this.actionBarManager = new com.songoda.arconix.packets.ActionBar.ActionBar1_9R1();
                break;
            case "v1_8_R3":
                this.particleManager = new com.songoda.arconix.packets.Particle.Particle1_8R3();
                this.actionBarManager = new com.songoda.arconix.packets.ActionBar.ActionBar1_8R3();
                break;
            case "v1_8_R2":
                this.particleManager = new com.songoda.arconix.packets.Particle.Particle1_8R2();
                this.actionBarManager = new com.songoda.arconix.packets.ActionBar.ActionBar1_8R2();
                break;
            case "v1_8_R1":
                this.particleManager = new com.songoda.arconix.packets.Particle.Particle1_8R1();
                this.actionBarManager = new com.songoda.arconix.packets.ActionBar.ActionBar1_8R1();
                break;
            case "v1_7_R4":
                this.actionBarManager = new com.songoda.arconix.packets.ActionBar.ActionBar1_7R4();
                break;
            default:
                break;
        }
    }

    public Particle getParticleManager()
    {
        return particleManager;
    }

    public ActionBar getActionBarManager()
    {
        return actionBarManager;
    }
    public Hologram getHologramManager()
    {
        return hologramManager;
    }
}
