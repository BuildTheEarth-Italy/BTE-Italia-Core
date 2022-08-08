@file:Suppress("unused")

package tk.bteitalia.core.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.*
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.CommandSender
import tk.bteitalia.core.config.FixDHConfig

@CommandAlias("bteitalia|bteitaly|btei")
@Subcommand("fix-dh")
@CommandPermission("bte-italy.core.admin")
internal class FixDHCommand(private val config: FixDHConfig) : BaseCommand() {
    @Subcommand("info")
    @Default
    @Description("Displays feature info")
    fun onStatus(sender: CommandSender) {
        val status = """
            The feature is ${if (config.enabled) "${ChatColor.DARK_GREEN}enabled" else "${ChatColor.DARK_RED}not enabled"}.${ChatColor.RESET}
            There are ${config.regions.size} regions: ${config.regions.joinToString(", ")}
            The following commands are executed: ${"\n" + config.executeCommands.joinToString("\n") { "                > $it" }}
        """.trimIndent()

        sender.sendMessage(status)
    }

    @Subcommand("enable")
    @Description("Enables the feature")
    fun onEnable(sender: CommandSender) {
        if (config.enabled) {
            sender.sendMessage("${ChatColor.YELLOW}The feature is already enabled!")
        } else {
            config.enabled = true
            sender.sendMessage("${ChatColor.DARK_GREEN}The feature is successfully enabled!")
        }
    }

    @Subcommand("disable")
    @Description("Disables the feature")
    fun onDisable(sender: CommandSender) {
        if (!config.enabled) {
            sender.sendMessage("${ChatColor.YELLOW}The feature is already disabled!")
        } else {
            config.enabled = false
            sender.sendMessage("${ChatColor.DARK_GREEN}The feature is successfully disabled!")
        }
    }

    @Subcommand("rglist|region list")
    @Description("Displays the list of all regions")
    fun onListRegion(sender: CommandSender) {
        val regions = config.regions.joinToString(", ")
        sender.sendMessage("Regions: $regions")
    }

    @Subcommand("rgadd|region add")
    @Description("Adds a region")
    fun onAddRegion(sender: CommandSender, region: String) {
        config.regions += region
        sender.sendMessage("${ChatColor.DARK_GREEN}The region ${ChatColor.WHITE}$region${ChatColor.DARK_GREEN} is successfully added!")
    }

    @Subcommand("rgrm|region remove")
    @Description("Removes a region")
    fun onRemoveRegion(sender: CommandSender, region: String) {
        config.regions = config.regions.filter { !it.equals(region, ignoreCase = true) }
        sender.sendMessage("${ChatColor.DARK_GREEN}All regions with the name ${ChatColor.WHITE}$region${ChatColor.DARK_GREEN} are removed!")
    }
}
