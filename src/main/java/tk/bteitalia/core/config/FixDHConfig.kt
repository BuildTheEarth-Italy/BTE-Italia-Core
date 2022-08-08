package tk.bteitalia.core.config

import dev.dejvokep.boostedyaml.YamlDocument

internal class FixDHConfig(private val config: YamlDocument) {
    var enabled: Boolean
        get() = config.getBoolean("features.fix-dh.enabled", false)
        set(value) {
            config.set("features.fix-dh.enabled", value)
            config.save()
        }

    var delay: Double
        get() = config.getDouble("features.fix-dh.delay", 1.0)
        set(value) {
            config.set("features.fix-dh.delay", value)
            config.save()
        }

    var executeCommands: List<String>
        get() = config.getStringList("features.fix-dh.execute-commands", emptyList())
        set(value) {
            config.set("features.fix-dh.execute-commands", value)
            config.save()
        }

    var regions: List<String>
        get() = config.getStringList("features.fix-dh.regions", emptyList())
        set(value) {
            config.set("features.fix-dh.regions", value)
            config.save()
        }
}
