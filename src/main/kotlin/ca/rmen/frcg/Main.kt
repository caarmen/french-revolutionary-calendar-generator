/*
 * Copyright (c) 2020 Carmen Alvarez
 *
 * This file is part of French Revolutionary Calendar Generator
 * French Revolutionary Calendar Generator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * French Revolutionary Calendar Generator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with French Revolutionary Calendar Generator.  If not, see <https://www.gnu.org/licenses/>.
 */

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
    val calendar = CalendarGenerator(parsedArguments.language, frc).createCalendar(
        calendarTemplateText = calendarTemplate,
        eventTemplateText = eventTemplate,
        year = parsedArguments.year
    )
    println(calendar)
}

