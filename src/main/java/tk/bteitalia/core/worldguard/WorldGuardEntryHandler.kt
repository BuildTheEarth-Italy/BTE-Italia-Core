package tk.bteitalia.core.worldguard

import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import com.sk89q.worldguard.session.MoveType
import com.sk89q.worldguard.session.Session
import com.sk89q.worldguard.session.handler.Handler
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginManager

internal class WorldGuardEntryHandler(session: Session, private val pluginManager: PluginManager) : Handler(session) {
    companion object {
        fun register(worldGuard: WorldGuardPlugin, pluginManager: PluginManager): Boolean {
            return worldGuard.sessionManager.registerHandler(Factory(pluginManager), null)
        }
    }

    class Factory(private val pluginManager: PluginManager) : Handler.Factory<WorldGuardEntryHandler>() {
        override fun create(session: Session): WorldGuardEntryHandler {
            return WorldGuardEntryHandler(session, pluginManager)
        }
    }

    override fun onCrossBoundary(
        player: Player?,
        from: Location?,
        to: Location?,
        toSet: ApplicableRegionSet?,
        entered: MutableSet<ProtectedRegion>?,
        exited: MutableSet<ProtectedRegion>?,
        moveType: MoveType?
    ): Boolean {
        if (player != null && entered != null) {
            for (region in entered) {
                val enterEvent = WorldGuardRegionEnterEvent(player, region)
                pluginManager.callEvent(enterEvent)
                if (enterEvent.isCancelled) return false
            }
        }

        return super.onCrossBoundary(player, from, to, toSet, entered, exited, moveType)
    }
}
