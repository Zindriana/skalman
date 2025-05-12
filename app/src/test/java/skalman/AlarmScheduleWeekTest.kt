import org.junit.Assert
import org.junit.Test
import skalman.data.models.CalendarAlarm
import java.time.LocalDate
import java.time.LocalDateTime

// This test verifies that weekly alarm grouping correctly includes all 7 days
class AlarmScheduleWeekTest {

    @Test
    fun `returns 7 days even if only 2 days have alarms`() {
        val monday = LocalDate.of(2025, 5, 12) // This is a Monday

        val tuesdayAlarm = CalendarAlarm(
            id = 1,
            title = "Tisdagalarm",
            startTime = LocalDateTime.of(2025, 5, 13, 9, 0)
        )

        val sundayAlarm = CalendarAlarm(
            id = 2,
            title = "Söndagsalarm",
            startTime = LocalDateTime.of(2025, 5, 18, 10, 0)
        )

        val result = getAlarmsForWeek(monday, listOf(tuesdayAlarm, sundayAlarm))

        Assert.assertEquals(7, result.size)
        Assert.assertEquals(LocalDate.of(2025, 5, 12), result.first().date)
        Assert.assertTrue(result[1].alarms.contains(tuesdayAlarm))
        Assert.assertTrue(result[6].alarms.contains(sundayAlarm))
        Assert.assertTrue(result.all { it.date.dayOfWeek.value in 1..7 }) // Validates days are in the week
    }

    @Test
    fun `returns 7 days with empty alarm lists when no alarms exist`() {
        val monday = LocalDate.of(2025, 5, 12)
        val result = getAlarmsForWeek(monday, emptyList())

        Assert.assertEquals(7, result.size)
        Assert.assertTrue(result.all { it.alarms.isEmpty() })
    }

    @Test
    fun `multiple alarms on same day are grouped correctly`() {
        val monday = LocalDate.of(2025, 5, 12)
        val sameDay = LocalDateTime.of(2025, 5, 14, 8, 0) // Wednesday

        val alarms = listOf(
            CalendarAlarm(1, "A", startTime = sameDay),
            CalendarAlarm(2, "B", startTime = sameDay.plusHours(1)),
            CalendarAlarm(3, "C", startTime = sameDay.plusHours(2))
        )

        val result = getAlarmsForWeek(monday, alarms)
        val wednesday = result.find { it.date.dayOfWeek.name == "WEDNESDAY" }

        Assert.assertEquals(3, wednesday?.alarms?.size)
    }

    @Test
    fun `alarms outside of the selected week are ignored`() {
        val monday = LocalDate.of(2025, 5, 12)
        val outOfWeekAlarm = CalendarAlarm(
            id = 1,
            title = "Outside",
            startTime = LocalDateTime.of(2025, 5, 20, 10, 0) // Tuesday next week
        )

        val result = getAlarmsForWeek(monday, listOf(outOfWeekAlarm))

        Assert.assertTrue(result.all { it.alarms.isEmpty() })
    }

    @Test
    fun `alarms on week boundaries are included`() {
        val monday = LocalDate.of(2025, 5, 12)

        val mondayAlarm = CalendarAlarm(
            id = 1,
            title = "Start",
            startTime = LocalDateTime.of(2025, 5, 12, 8, 0)
        )
        val sundayAlarm = CalendarAlarm(
            id = 2,
            title = "End",
            startTime = LocalDateTime.of(2025, 5, 18, 22, 0)
        )

        val result = getAlarmsForWeek(monday, listOf(mondayAlarm, sundayAlarm))

        Assert.assertTrue(result.first().alarms.contains(mondayAlarm))
        Assert.assertTrue(result.last().alarms.contains(sundayAlarm))
    }


}