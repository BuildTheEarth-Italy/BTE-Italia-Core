package tk.bteitalia.core

import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import tk.bteitalia.core.feature.fixhd.FixHDListener

@Suppress("unused")
class BTEItalyCorePlugin : JavaPlugin() {
    override fun onEnable() {
        val fixHDListener = FixHDListener()
        server.pluginManager.registerEvents(fixHDListener, this)
    }

    override fun onDisable() {
        HandlerList.unregisterAll(this)
    }
}
