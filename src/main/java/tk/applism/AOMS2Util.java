package tk.applism;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class AOMS2Util extends   JavaPlugin implements Listener{
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        //Config info
        config.options().header("Welcome to AOMS2Util Config!\nVoidProtect has two Types: `save`, `kill`. `save` teleports the player into the air. `kill` kills the player");
        //Config for enabling/disabling VoidProtect and JumpPads
        config.addDefault("VoidProtect.VoidEnable", true);
        config.addDefault("VoidProtect.VoidType", "teleport");
        config.addDefault("JumpPad", true);
        config.options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("Applism Origin Minecraft Server Two Utils Plugin has been enabled!");
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("a2utils").setExecutor(new A2Utils());
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable has been invoked!");
    }

    //JumpPad

    public void JumpMethod(Material BelowBlock, Player player, int vx, int vy, int vz) {
        if (config.getBoolean("JumpPad")) {
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SLIME_BLOCK) && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(BelowBlock)) {
                Vector v = player.getVelocity();
                v.setX(vx);
                v.setY(vy);
                v.setZ(vz);
                player.setVelocity(v);
            }
        }
    }
    //Set LowerMaterial and power of JumpPad
    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        JumpMethod(Material.REDSTONE_BLOCK, event.getPlayer(), 0, 3, 5);
        JumpMethod(Material.LAPIS_BLOCK, event.getPlayer(), 0, 3, -5);
        JumpMethod(Material.GOLD_BLOCK, event.getPlayer(), -5, 3, 0);
        JumpMethod(Material.IRON_BLOCK, event.getPlayer(), 5, 3, 0);
    }

    //VoidProtect
    @EventHandler
    public void onMovement(PlayerMoveEvent event) {
        if (config.getBoolean("VoidProtect.VoidEnable")) {
            switch (config.getString("VoidProtect.VoidType")) {
                case "teleport":
                    if (event.getTo().getBlockY() < 0) {
                        Location TeleportArea = event.getTo();
                        //Sets Protect Height
                        TeleportArea.setY(128);
                        event.getPlayer().teleport(TeleportArea);
                    }
                    break;
                case "kill":
                    if (event.getTo().getBlockY() < 0) {
                        event.getPlayer().setHealth(0);
                    }
            }
        }
    }
}
