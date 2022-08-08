package tk.bteitalia.core.config

import dev.dejvokep.boostedyaml.YamlDocument

internal class FeaturesConfig(config: YamlDocument) {
    val fixDH = FixDHConfig(config)
}
