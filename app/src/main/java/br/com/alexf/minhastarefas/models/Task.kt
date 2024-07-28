package br.com.alexf.minhastarefas.models

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false,
    val dueDate: Long? = null
) {
    @OptIn(FormatStringsInDatetimeFormats::class)
    val dateInBrazilianFormat: String? get() = dueDate?.let {
        Instant.fromEpochMilliseconds(it)
            .toLocalDateTime(TimeZone.UTC)
            .date
            .format(LocalDate.Format {
                byUnicodePattern("dd/MM/yyyy")
            })
    }
}