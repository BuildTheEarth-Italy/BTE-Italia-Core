package tk.bteitalia.core

import co.aikar.commands.PaperCommandManager
import com.sk89q.worldguard.bukkit.WGBukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import tk.bteitalia.core.commands.FixDHCommand
import tk.bteitalia.core.commands.MainCommand
import tk.bteitalia.core.commands.RemovePlayerdataCommand
import tk.bteitalia.core.config.Config
import tk.bteitalia.core.feature.fixdh.FixDHListener
import tk.bteitalia.core.worldguard.WGEntryHandler
import java.io.File

@Suppress("unused")
class BTEItalyCorePlugin : JavaPlugin() {
    private var commandManager: PaperCommandManager? = null

    override fun onEnable() {
        val configResource = getResource("config.yml")
        if (configResource == null) {
            logger.severe("Unable to get config.yml resource!!! (Did you edit plugin files?)")
            server.pluginManager.disablePlugin(this)
        }

        val config = Config(File(dataFolder, "config.yml"), configResource)

        val worldGuard = WGBukkit.getPlugin()
        if (worldGuard == null) {
            logger.severe("WorldGuard is not found!!!")
            server.pluginManager.disablePlugin(this)
            return
        }

        val fixDHListener = FixDHListener(this, worldGuard, config.features.fixDH)
        server.pluginManager.registerEvents(fixDHListener, this)

        if (commandManager == null) commandManager = PaperCommandManager(this)
        commandManager?.registerCommand(MainCommand(config))
        commandManager?.registerCommand(FixDHCommand(config.features.fixDH))
        commandManager?.registerCommand(RemovePlayerdataCommand())

        logger.info("Plugin is enabled!")

        server.scheduler.scheduleSyncDelayedTask(this, {
            if (!WGEntryHandler.register(worldGuard, server.pluginManager)) {
                logger.warning("Unable to register WorldGuard session handler!")
            }
        }, 1)
    }

    override fun onDisable() {
        HandlerList.unregisterAll(this)

        commandManager?.unregisterCommands()
        commandManager = null

        logger.info("Plugin is disabled!")
    }
}
