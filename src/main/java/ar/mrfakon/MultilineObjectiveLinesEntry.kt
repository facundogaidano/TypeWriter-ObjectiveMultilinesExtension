package ar.mrfakon

import com.typewritermc.core.books.pages.Colors
import com.typewritermc.core.extension.annotations.*
import com.typewritermc.core.entries.priority
import com.typewritermc.engine.paper.entry.*
import com.typewritermc.engine.paper.entry.entries.LinesEntry
import com.typewritermc.engine.paper.extensions.placeholderapi.parsePlaceholders
import com.typewritermc.engine.paper.utils.replaceTagPlaceholders
import com.typewritermc.quest.inactiveObjectiveDisplay
import com.typewritermc.quest.showingObjectiveDisplay
import com.typewritermc.quest.trackedShowingObjectives
import org.bukkit.entity.Player
import java.util.*

@Entry(
    name = "objective_multilines",
    description = "Display tracked objectives with multi-line splitting",
    color = Colors.ORANGE_RED,
    icon = "fluent:clipboard-task-list-ltr-24-filled",
)
class MultilineObjectiveLinesEntry(
    override val id: String = "",
    override val name: String = "",
    @Help("Format for each line. Use <objective> to inject text.")
    @Colored
    @Placeholder
    @MultiLine
    val format: String = "<objective>",
    override val priorityOverride: Optional<Int> = Optional.empty(),
) : LinesEntry {
    override fun lines(player: Player): String {
        val objectives = player.trackedShowingObjectives().toList().sortedByDescending { it.priority }

        val lines = objectives.flatMap { obj ->
            val raw = obj.display.get(player) ?: ""
            val normalized = normalizeObjectiveNewlines(raw)

            val wrapper = if (obj.criteria.matches(player)) showingObjectiveDisplay else inactiveObjectiveDisplay

            normalized.split('\n')
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { part ->
                    val renderedObjective = wrapper.replaceTagPlaceholders("display", part).parsePlaceholders(player)
                    format.parsePlaceholders(player).replaceTagPlaceholders("objective", renderedObjective)
                }
        }

        return lines.joinToString("\n")
    }
}


