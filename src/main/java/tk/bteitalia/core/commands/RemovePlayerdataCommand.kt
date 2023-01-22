@file:Suppress("unused")

package tk.bteitalia.core.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import khttp.get
import org.bukkit.command.CommandSender
import java.nio.file.Files
import java.nio.file.Paths

@CommandAlias("bteitalia|bteitaly|btei")
@CommandPermission("bte-italy.core.admin")
internal class RemovePlayerdataCommand() : BaseCommand() {

    @Subcommand("rmpd")
    @Description("Remove playerdata from both world and essentials")
    fun onRmPlayerdata(sender: CommandSender, username: String) {
        val uuid = getPlayerUUID(username)
        val essentialsPath = Paths.get("plugins/essentials/userdata/$uuid.yml")
        val worldPath = Paths.get("world/playerdata/$uuid.dat")

        val result1 = Files.deleteIfExists(essentialsPath)
        val result2 = Files.deleteIfExists(worldPath)

        if (result1) {
            sender.sendMessage("Successfully deleted essentials data for $username ($uuid)")
            return
        } else if (result2){
            sender.sendMessage("Successfully deleted world data for $username ($uuid)")
        } else {
            sender.sendMessage("Error! Problem deleting player data for $username ($uuid)")
        }
    }

        // get uuid function

    private fun getPlayerUUID(username: String): String? {
        val apiResponse = get("https://playerdb.co/api/player/minecraft/$username")
        return apiResponse.jsonObject.getJSONObject("data").getJSONObject("player").getString("id")
    }
}