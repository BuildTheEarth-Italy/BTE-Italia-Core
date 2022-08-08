package tk.bteitalia.core.feature.fixdh

import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import tk.bteitalia.core.BTEItalyCorePlugin
import tk.bteitalia.core.worldguard.WGRegionEnterEvent

internal class FixDHListener(
    private val corePlugin: BTEItalyCorePlugin, private val worldGuardPlugin: WorldGuardPlugin
) : Listener {
    private fun reloadDH() {
        corePlugin.server.scheduler.scheduleSyncDelayedTask(corePlugin, {
            val console = corePlugin.server.consoleSender
            corePlugin.server.dispatchCommand(console, "plugman reload DecentHolograms")
        }, 3)
    }

    @EventHandler(ignoreCancelled = true)
    fun onWGRegionEnter(event: WGRegionEnterEvent) {
        val name = event.region.id
        if (!name.contains("newspawn", ignoreCase = true)) return

        reloadDH()
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val regions = worldGuardPlugin.regionContainer.get(event.player.world) ?: return
        val spawnRegions = regions.regions.values.filter { it.id.contains("newspawn", ignoreCase = true) }

        val location = event.player.location

        for (region in spawnRegions) {
            if (!region.contains(location.blockX, location.blockY, location.blockZ)) continue

            reloadDH()
            return
        }
    }
}
