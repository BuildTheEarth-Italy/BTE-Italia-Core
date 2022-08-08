@file:Suppress("unused")

package tk.bteitalia.core.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.CommandSender
import tk.bteitalia.core.config.Config

@CommandAlias("bteitalia|bteitaly|btei")
@CommandPermission("bte-italy.core.admin")
internal class MainCommand(private val config: Config) : BaseCommand() {
    @Subcommand("reload")
    @Description("Reloads config")
    fun onReload(sender: CommandSender) {
        val res = config.reload()

        if (res) {
            sender.sendMessage("${ChatColor.DARK_GREEN}Successfully reloaded!")
        } else {
            sender.sendMessage("${ChatColor.DARK_RED}Unable to reload!")
        }
    }

}
