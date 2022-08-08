package tk.bteitalia.core.worldguard

import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

@Suppress("unused")
internal class WGRegionEnterEvent(
    val player: Player,
    val region: ProtectedRegion
) : Event(), Cancellable {
    private var cancelled = false

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    override fun getHandlers(): HandlerList {
        return pHandlerList
    }

    companion object {
        private val pHandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return pHandlerList
        }
    }
}
