package tk.applism;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class AOMS2Util extends JavaPlugin implements Listener{
    @Override
    public void onEnable() {
        getLogger().info("Applism Origin Minecraft Server Two Utils Plugin has been enabled!");
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("a2utils").setExecutor(new A2Utils());
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable has been invoked!");
    }

    //JumpPad
    public static void JumpMethod(Material BelowBlock, Player player, int vx, int vy, int vz) {
        if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.SLIME_BLOCK) && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType().equals(BelowBlock)) {
            Vector v =  player.getVelocity();
            v.setX(vx);
            v.setY(vy);
            v.setZ(vz);
            player.setVelocity(v);
        }
    }
    //Set LowerMaterial and power of JumpPad
    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        JumpMethod(Material.REDSTONE_BLOCK, event.getPlayer(), 0, 3, 5);
        JumpMethod(Material.LAPIS_BLOCK, event.getPlayer(), 0, 3, -5);
    }

    //VoidProtect
    @EventHandler
    public void onMovement(PlayerMoveEvent event) {
        if (event.getTo().getBlockY() < 0) {
            Location TeleportArea = event.getTo();
            //Sets Protect Height
            TeleportArea.setY(128);
            event.getPlayer().teleport(TeleportArea);
        }
    }
}
