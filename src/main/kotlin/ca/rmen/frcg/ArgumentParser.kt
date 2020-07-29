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

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.util.Calendar
import java.util.Locale

object ArgumentParser  {
    fun parseArguments(args: Array<String>): Arguments {
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        val parser = ArgParser("frcg")
        val year by parser.option(
            type = ArgType.Int,
            fullName = "year",
            shortName = "y",
            description = "Gregorian year for which to generate the calendar"
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
        val calculationMethod by parser.option(
            type = ArgType.Choice(
                ca.rmen.lfrc.FrenchRevolutionaryCalendar.CalculationMethod.values().map {
                    it.name.toLowerCase(Locale.US)
                }),
            fullName = "method",
            shortName = "m"
        ).default("romme")
        parser.parse(args)
        return Arguments(
            year = year,
            calendarTemplatePath = calendarTemplatePath,
            eventTemplatePath = eventTemplatePath,
            language = language,
            calculationMethod = calculationMethod
        )
    }


}
