package ca.rmen.frcg

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.util.Calendar

object ArgumentParser  {
    fun parseArguments(args: Array<String>): Arguments {
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        val parser = ArgParser("frcg")
        val year by parser.option(
            type = ArgType.Int,
            fullName = "year",
            shortName = "y",
            description = "Year for which to generate the calendar"
        ).default(thisYear)
        val eventTemplatePath by parser.option(
            type = ArgType.String,
            fullName = "event-template",
            shortName = "et",
            description = "Path to event ics template file"
        ).default("src/main/resources/event.ics.template")
        val calendarTemplatePath by parser.option(
            type = ArgType.String,
            fullName = "calendar-template",
            shortName = "ct",
            description = "Path to calendar ics template file"
        ).default("src/main/resources/calendar.ics.template")
        val language by parser.option(
            type = ArgType.Choice(listOf("ca", "de", "en", "es", "eu", "fr", "it")),
            fullName = "language",
            shortName = "l"
        ).default("fr")
        parser.parse(args)
        return Arguments(
            year = year,
            calendarTemplatePath = calendarTemplatePath,
            eventTemplatePath = eventTemplatePath,
            language = language
        )
    }


}
