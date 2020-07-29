package ca.rmen.frcg

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun main(args: Array<String>) {
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
    val eventTemplate = File(eventTemplatePath).readText()
    val calendarTemplate = File(calendarTemplatePath).readText()
    val frc = ca.rmen.lfrc.FrenchRevolutionaryCalendar(
        Locale(language), ca.rmen.lfrc.FrenchRevolutionaryCalendar.CalculationMethod.ROMME
    )
    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    } as GregorianCalendar

    val events = createEvents(eventTemplate, year, frc)
    val calendar = createCalendar(calendarTemplate, events)
    System.out.println(calendar)
}

val sdf = SimpleDateFormat("yyyyMMdd")
fun createEvents(
    templateText: String,
    year: Int,
    frc: ca.rmen.lfrc.FrenchRevolutionaryCalendar
) : List<String>{
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, Calendar.JANUARY)
        set(Calendar.DAY_OF_YEAR, 1)
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    } as GregorianCalendar
    val events = mutableListOf<String>()
    while(calendar.get(Calendar.YEAR) == year) {
        events.add(createEvent(templateText, calendar, frc))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    return events
}

fun createCalendar(
    templateText: String,
    eventTexts: List<String>
) = templateText.replace("__EVENTS__", eventTexts.joinToString("\n"))

fun createEvent(
    templateText: String,
    gregorianDate: GregorianCalendar,
    frc: ca.rmen.lfrc.FrenchRevolutionaryCalendar
) =
    createEvent(templateText, gregorianDate, frc.getDate(gregorianDate))

fun createEvent(
    templateText: String,
    gregorianDate: GregorianCalendar,
    frcDate: ca.rmen.lfrc.FrenchRevolutionaryCalendarDate
) =
    templateText
        .replace("__UUID__", UUID.randomUUID().toString())
        .replace("__GREGORIAN_DATE_GENERATION__", sdf.format(Date()))
        .replace("__FRC_DATE_DAY__", frcDate.dayOfMonth.toString())
        .replace("__FRC_DATE_MONTH__", frcDate.monthName)
        .replace("__FRC_DATE_YEAR__", frcDate.year.toString())
        .replace("__FRC_DATE_OBJECT__", frcDate.objectOfTheDay)
        .replace("__GREGORIAN_DATE_START__", sdf.format(gregorianDate.time))
        .replace("__GREGORIAN_DATE_END__", sdf.format(Calendar.getInstance().apply {
            timeInMillis = gregorianDate.timeInMillis
            add(Calendar.DAY_OF_YEAR, 1)
        }.time))
