package ar.mrfakon

fun normalizeObjectiveNewlines(text: String): String = text
    .replace("<newline>", "\n", ignoreCase = true)
    .replace("<nl>", "\n", ignoreCase = true)
    .replace("|NL|", "\n", ignoreCase = true)
    .replace("\\n", "\n")
