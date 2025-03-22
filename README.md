# gAbility
An HCF ability items plugin for new Minecraft versions!
## How to install
- Download the latest version on the [releases page](https://github.com/UnaPepsi/gAbility/releases)
- Drag and drop the JAR in your server's plugin folder
- Start the server
## Docs
_This plugin was developed under PaperMC's API. So it is recommended that you use PaperMC when using this plugin_

- `/ability info`
  - Displays information about the plugin
  - Permissions required: `None`
- `/ability reload`
  - Reloads the plugin's **config.yml**
  - Permissions required: `gability.admin`
- `/ability cooldown <player> <ability|global|all> <seconds>`
  - Sets a player's ability/global cooldown to the specified amount in seconds
  - If set to `all` the cooldown is applied to every ability
  - Permissions required: `gability.cooldown`
- `/ability give <player> <ability|all> [amount]`
  - Gives the specified player `amount` of a specific `ability` (or every if `all` is used)
  - `amount` can't go past 100 or below 1. Defaults to 1
  - Permissions required: `gability.give`

_`/ability test` commands and subcommands shouldn't be run unless you know what you're doing. Permissions required: `gability.admin`_