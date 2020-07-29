package ca.rmen.frcg

import java.io.File
import java.util.Locale

fun main(args: Array<String>) {
    val parsedArguments = ArgumentParser.parseArguments(args)
    val calendarTemplate = File(parsedArguments.calendarTemplatePath).readText()
    val eventTemplate = File(parsedArguments.eventTemplatePath).readText()
    val frc = ca.rmen.lfrc.FrenchRevolutionaryCalendar(
        Locale(parsedArguments.language),
        ca.rmen.lfrc.FrenchRevolutionaryCalendar.CalculationMethod.ROMME
    )
    val calendar = CalendarGenerator(frc).createCalendar(
        calendarTemplateText = calendarTemplate,
        eventTemplateText = eventTemplate,
        year = parsedArguments.year
    )
    println(calendar)
}

