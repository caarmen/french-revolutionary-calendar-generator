package ca.rmen.frcg

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.UUID

class CalendarGenerator(private val frc: ca.rmen.lfrc.FrenchRevolutionaryCalendar) {
    private val sdf = SimpleDateFormat("yyyyMMdd")

    fun createCalendar(
        calendarTemplateText: String,
        eventTemplateText: String,
        year: Int
    ): String {
        val events = createEvents(eventTemplateText, year)
        return createCalendar(calendarTemplateText, events)
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
    ) = templateText.replace("__EVENTS__", eventTexts.joinToString("\n"))

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
            .replace("__FRC_DATE_MONTH__", frcDate.monthName)
            .replace("__FRC_DATE_YEAR__", frcDate.year.toString())
            .replace("__FRC_DATE_OBJECT__", frcDate.objectOfTheDay)
            .replace("__GREGORIAN_DATE_START__", sdf.format(gregorianDate.time))
            .replace("__GREGORIAN_DATE_END__", sdf.format(Calendar.getInstance().apply {
                timeInMillis = gregorianDate.timeInMillis
                add(Calendar.DAY_OF_YEAR, 1)
            }.time))

}