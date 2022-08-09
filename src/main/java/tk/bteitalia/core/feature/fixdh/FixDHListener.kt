package tk.bteitalia.core.feature.fixdh

import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import eu.decentsoftware.holograms.api.DecentHologramsAPI
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerTeleportEvent
import tk.bteitalia.core.BTEItalyCorePlugin
import tk.bteitalia.core.config.FixDHConfig
import tk.bteitalia.core.worldguard.WGRegionEnterEvent
import java.util.logging.Logger
import kotlin.math.max

internal class FixDHListener(
    private val corePlugin: BTEItalyCorePlugin,
    private val worldGuardPlugin: WorldGuardPlugin,
    private val config: FixDHConfig,
    private val logger: Logger? = null
) : Listener {
    private fun reloadDH(player: Player) {
        val tps = 20
        val ticksDelay = max(0, (config.delay * tps).toLong())

        logger?.info("Scheduling reloading holograms for player ${player.name} in the next $ticksDelay ticks...")

        corePlugin.server.scheduler.scheduleSyncDelayedTask(corePlugin, {
            logger?.info("Start reloading holograms for player ${player.name}")

            val dh = DecentHologramsAPI.get()
            dh.hologramManager.updateVisibility(player)

            logger?.info("Done reloading holograms for player ${player.name}")
        }, ticksDelay)
    }

    @EventHandler(ignoreCancelled = true)
    fun onWGRegionEnter(event: WGRegionEnterEvent) {
        if (!config.enabled) return

        val name = event.region.id
        logger?.info("Player ${event.player.name} stepped into the region ${name}.")

        for (regionName in config.regions) {
            if (name.equals(regionName, ignoreCase = true)) {
                logger?.info("The region $name is found in the list.")

                reloadDH(event.player)
                return
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (!config.enabled) return

        val regions = worldGuardPlugin.regionContainer.get(event.player.world) ?: return
        val worldRegions = regions.regions.values.filter { worldReg ->
            config.regions.map { it.lowercase() }.contains(worldReg.id.lowercase())
        }


        val location = event.player.location

        logger?.info("Player ${event.player.name} joined at location $location")

        for (region in worldRegions) {
            if (!region.contains(location.blockX, location.blockY, location.blockZ)) continue

            logger?.info("Location $location is found in the region ${region.id}")

            reloadDH(event.player)
            return
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerTeleport(event: PlayerTeleportEvent) {
        if (!config.enabled) return

        val regions = worldGuardPlugin.regionContainer.get(event.player.world) ?: return
        val worldRegions = regions.regions.values.filter { worldReg ->
            config.regions.map { it.lowercase() }.contains(worldReg.id.lowercase())
        }


        val location = event.to

        logger?.info("Player ${event.player.name} teleported to the location $location")

        for (region in worldRegions) {
            if (!region.contains(location.blockX, location.blockY, location.blockZ)) continue

            logger?.info("Location $location is found in the region ${region.id}")

            reloadDH(event.player)
            return
        }
    }
}
