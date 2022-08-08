package tk.bteitalia.core.feature.fixdh

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import tk.bteitalia.core.BTEItalyCorePlugin
import tk.bteitalia.core.worldguard.WGRegionEnterEvent

internal class FixDHListener(private val plugin: BTEItalyCorePlugin) : Listener {
    private fun reloadDH() {
        plugin.server.scheduler.scheduleSyncDelayedTask(plugin, {
            val console = plugin.server.consoleSender
            plugin.server.dispatchCommand(console, "plugman reload DecentHolograms")
        }, 3)
    }

    @EventHandler(ignoreCancelled = true)
    fun onWGRegionEnter(event: WGRegionEnterEvent) {
        val name = event.region.id
        if (!name.contains("newspawn", ignoreCase = true)) return

        reloadDH()
    }
}
