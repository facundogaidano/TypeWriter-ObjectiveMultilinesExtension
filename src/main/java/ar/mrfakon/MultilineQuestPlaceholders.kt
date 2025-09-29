package ar.mrfakon

import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.engine.paper.extensions.placeholderapi.PlaceholderHandler
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.entry.*
import com.typewritermc.engine.paper.utils.replaceTagPlaceholders
import com.typewritermc.quest.ObjectiveEntry
import com.typewritermc.quest.inactiveObjectiveDisplay
import com.typewritermc.quest.showingObjectiveDisplay
import com.typewritermc.quest.trackedShowingObjectives
import org.bukkit.entity.Player

@Singleton
class MultilineQuestPlaceholders : PlaceholderHandler {
    override fun onPlaceholderRequest(player: Player?, params: String): String? {
        if (player == null) return null

        if (params == "tracked_objectives_multiline") {
            val parts = player.trackedShowingObjectives().flatMap { objective ->
                splitObjectiveIntoParts(player, objective)
            }.toList()

            return parts.joinToString(", ").ifBlank { "<gray>None tracked</gray>" }
        }

        return null
    }

    private fun splitObjectiveIntoParts(player: Player, objective: ObjectiveEntry): Sequence<String> {
        val raw = objective.display.get(player) ?: ""
        val normalized = normalizeObjectiveNewlines(raw)

        val wrapper = if (objective.criteria.matches(player)) showingObjectiveDisplay else inactiveObjectiveDisplay

        return normalized.split('\n').asSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { line ->
                wrapper.replaceTagPlaceholders("display", line).parsePlaceholders(player)
            }
    }
}


