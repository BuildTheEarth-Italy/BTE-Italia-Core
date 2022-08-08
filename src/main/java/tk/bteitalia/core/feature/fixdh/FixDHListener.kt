package tk.bteitalia.core.feature.fixdh

import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import tk.bteitalia.core.BTEItalyCorePlugin
import tk.bteitalia.core.config.FixDHConfig
import tk.bteitalia.core.worldguard.WGRegionEnterEvent

internal class FixDHListener(
    private val corePlugin: BTEItalyCorePlugin,
    private val worldGuardPlugin: WorldGuardPlugin,
    private val config: FixDHConfig
) : Listener {
    private fun reloadDH() {
        val ticksDelay = (config.delay * 20).toLong()

        corePlugin.server.scheduler.scheduleSyncDelayedTask(corePlugin, {
            val console = corePlugin.server.consoleSender

            for (command in config.executeCommands) {
                corePlugin.server.dispatchCommand(console, command)
            }
        }, ticksDelay)
    }

    @EventHandler(ignoreCancelled = true)
    fun onWGRegionEnter(event: WGRegionEnterEvent) {
        if (!config.enabled) return

        val name = event.region.id
        for (regionName in config.regions) {
            if (name.equals(regionName, ignoreCase = true)) {
                reloadDH()
                return
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!config.enabled) return

        val regions = worldGuardPlugin.regionContainer.get(event.player.world) ?: return
        val spawnRegions = regions.regions.values.filter { it.id.equals("newspawn", ignoreCase = true) }

        val location = event.player.location

        for (region in spawnRegions) {
            if (!region.contains(location.blockX, location.blockY, location.blockZ)) continue

            for (regionName in config.regions) {
                reloadDH()
                return
            }
        }
    }
}
