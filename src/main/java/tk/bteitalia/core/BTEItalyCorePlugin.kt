package tk.bteitalia.core

import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class BTEItalyCorePlugin : JavaPlugin() {
    override fun onEnable() {
        logger.info("Plugin is enabled!")
    }

    override fun onDisable() {
        logger.info("Plugin is disabled!")
    }
}
