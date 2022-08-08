package tk.bteitalia.core

import com.sk89q.worldguard.bukkit.WGBukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import tk.bteitalia.core.feature.fixhd.FixHDListener
import tk.bteitalia.core.worldguard.WGEntryHandler

@Suppress("unused")
class BTEItalyCorePlugin : JavaPlugin() {
    override fun onEnable() {
        val worldGuard = WGBukkit.getPlugin()
        if (worldGuard == null) {
            logger.severe("WorldGuard is not found!!!")
            server.pluginManager.disablePlugin(this)
            return
        }

        if (!WGEntryHandler.register(worldGuard, server.pluginManager)) {
            logger.warning("Unable to register WorldGuard handler!")
        }

        val fixHDListener = FixHDListener(this)
        server.pluginManager.registerEvents(fixHDListener, this)

        logger.info("Plugin is enabled!")
    }

    override fun onDisable() {
        HandlerList.unregisterAll(this)

        logger.info("Plugin is disabled!")
    }
}
