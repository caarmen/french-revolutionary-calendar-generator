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

import ca.rmen.frcg.i18n.getStrings
import java.text.SimpleDateFormat
import java.util.*

class CalendarGenerator(
    private val language: String,
    private val frc: ca.rmen.lfrc.FrenchRevolutionaryCalendar) {

    private val sdf = SimpleDateFormat("yyyyMMdd")
    private val strings = getStrings(language)

    fun createCalendar(
        calendarTemplateText: String,
        eventTemplateText: String,
        year: Int
    ): String {
        val events = createEvents(eventTemplateText, year)
        return createCalendar(
            templateText = calendarTemplateText,
            eventTexts = events
        )
    }

    private fun createEvents(
        templateText: String,
        year: Int
    ): List<String> {
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
        while (calendar.get(Calendar.YEAR) == year) {
            events.add(createEvent(templateText, calendar))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return events
    }

    private fun createCalendar(
        templateText: String,
        eventTexts: List<String>
    ) = templateText
        .replace("__CALENDAR_NAME__", strings.calendarName)
        .replace("__EVENTS__", eventTexts.joinToString("\n"))

    private fun createEvent(
        templateText: String,
        gregorianDate: GregorianCalendar
    ) =
        createEvent(templateText, gregorianDate, frc.getDate(gregorianDate))

    private fun createEvent(
        templateText: String,
        gregorianDate: GregorianCalendar,
        frcDate: ca.rmen.lfrc.FrenchRevolutionaryCalendarDate
    ) =
        templateText
            .replace("__UUID__", UUID.randomUUID().toString())
            .replace("__GREGORIAN_DATE_GENERATION__", sdf.format(Date()))
            .replace("__FRC_DATE_DAY__", frcDate.dayOfMonth.toString())
            .replace("__FRC_DATE_DAY_OF_WEEK__", frcDate.weekdayName)
            .replace("__FRC_DATE_MONTH__", frcDate.monthName)
            .replace("__FRC_DATE_YEAR__", frcDate.year.toString())
            .replace("__FRC_DATE_OBJECT__", frcDate.objectOfTheDay)
            .replace("__FRC_DATE_OBJECT_LABEL__",
                String.format(Locale.US, strings.objectOfTheDayLabelFormat, frcDate.objectTypeName))
            .replace("__GREGORIAN_DATE_START__", sdf.format(gregorianDate.time))
            .replace("__GREGORIAN_DATE_END__", sdf.format(Calendar.getInstance().apply {
                timeInMillis = gregorianDate.timeInMillis
                add(Calendar.DAY_OF_YEAR, 1)
            }.time))

}