package xyz.drewcification.hardcorerespawn

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class HardcoreRespawn : JavaPlugin() {

    override fun onEnable() {
        // psa: ethan sucks eggs
        server.scheduler.cancelTasks(this)

        // easy way to do this, probably a bad way, but it's an easy way!!!
        server.scheduler.runTaskTimer(this, ::checkTime, 100, 100)

        logger.info("${description.name} version ${description.version} enabled!")
    }

    private fun checkTime() {
        val overworld: World = server.worlds[0]

        if (overworld.time in 0..100) {
            logger.info("Doing respawn check => ${overworld.time}")
            // Get everyone who is in spectator mode currently
            val deadPlayers: List<Player> = server.onlinePlayers.filter { player: Player? -> player?.gameMode == GameMode.SPECTATOR }

            logger.info("Respawn check => No one is dead! Skipping the rest.")
            if (deadPlayers.isEmpty()) return

            val deadPlayersStr: String = deadPlayers.joinToString(", ") { player: Player -> player.name }

            logger.info("Respawning $deadPlayersStr")
            server.onlinePlayers.forEach { player: Player? -> player?.sendMessage("awakening those whom'st'dve have deceased. Welcome black $deadPlayersStr") }

            for (player: Player in deadPlayers) {
                player.sendMessage("welcome black! don't die idiot")
                player.gameMode = GameMode.SURVIVAL
                player.teleport(Location(overworld, -533.5, 70.0, 248.5, 0f, 0f))
            }
        }
    }

    override fun onDisable() {
        logger.info("${description.name} disabled.")
        server.scheduler.cancelTasks(this)
    }
}
