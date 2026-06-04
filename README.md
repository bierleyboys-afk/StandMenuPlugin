StandMenu Plugin

Opens a DeluxeMenus-defined GUI when a player stands on a configured block and teleports the player to a configured location if they close the menu without interacting (ESC).

Build

Use Maven to build (Java 17):

```bash
mvn clean package
```

Install

- Drop the resulting jar from `target/` into your Paper server `plugins/` folder.
- Configure `config.yml` inside the plugin folder and ensure the `open-command` matches your DeluxeMenus command (default: `dm open %menu% %player%`).
- Restart the server.

GitHub Actions

- A workflow is included at `.github/workflows/maven-build.yml`.
- Push this repository to GitHub and open the Actions tab to build the plugin automatically.
- The workflow uploads the built jar as an artifact named `StandMenuPlugin-jar`.

Notes

- The plugin opens DeluxeMenus by dispatching the configured command from the console; ensure DeluxeMenus is installed and the command is correct.
- ESC detection is heuristic: if the player did not click any slot in the opened menu before closing, the plugin treats the close as an ESC close and teleports the player.
- If you want API-based integration instead of command-based, I can update the plugin to call DeluxeMenus' API directly (requires DeluxeMenus as a compile-time dependency).