package com.nide.pocketpass.util

import android.text.format.DateUtils
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.math.abs

object TimeFormat {

    const val ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'"


    fun String.toLocalDateTime(pattern: String = ISO_8601): LocalDateTime? = try {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))
    } catch (exception: Exception) {
        null
    }


    fun LocalDateTime.format(pattern: String = ISO_8601): String? = try {
        DateTimeFormatter.ofPattern(pattern).format(this)
    } catch (exception: Exception) {
        null
    }


    fun LocalDateTime.getRelativeTimeSpanString(now: LocalDateTime?): CharSequence? =
        DateUtils.getRelativeTimeSpanString(
            getEpochMilli(),
            (now ?: LocalDateTime.now()).getEpochMilli(),
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_TIME
        )


    fun Long.getRelativeTimeSpanString(now: LocalDateTime?): CharSequence? =
        DateUtils.getRelativeTimeSpanString(
            this,
            (now ?: LocalDateTime.now()).getEpochMilli(),
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_TIME
        )


    fun LocalDateTime.getEpochMilli(): Long =
        atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

    fun getLocalTimeTimestamp() = LocalDateTime.now().getEpochMilli()


    fun LocalDateTime.getDaysTo(to: LocalDateTime?): Long =
        ChronoUnit.DAYS.between(this, to)


    fun LocalDateTime.getDaysBetween(to: LocalDateTime?): Long =
        abs(ChronoUnit.DAYS.between(this, to))


    fun LocalDateTime.nextDayOfTheWeek(dayOfWeek: DayOfWeek): LocalDateTime =
        this.plusHours(1)
            .with(TemporalAdjusters.next(dayOfWeek))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()


    fun LocalDateTime.toDate(): Date = Date.from(
        this.atZone(ZoneId.systemDefault())
            .toInstant()
    )


    fun Date.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(
        this.toInstant(),
        ZoneOffset.UTC
    )

}