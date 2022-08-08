package tk.bteitalia.core

import com.sk89q.worldguard.bukkit.WGBukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import tk.bteitalia.core.feature.fixhd.FixHDListener

@Suppress("unused")
class BTEItalyCorePlugin : JavaPlugin() {
    override fun onEnable() {
        val wg = WGBukkit.getPlugin()
        if (wg == null) {
            logger.severe("WorldGuard is not found!!!")
            server.pluginManager.disablePlugin(this)
            return
        }

        val fixHDListener = FixHDListener()
        server.pluginManager.registerEvents(fixHDListener, this)

        logger.info("Plugin is enabled!")
    }

    override fun onDisable() {
        HandlerList.unregisterAll(this)

        logger.info("Plugin is disabled!")
    }
}
