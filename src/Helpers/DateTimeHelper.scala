package Helpers

import com.github.nscala_time.time.Imports._
import java.sql.Date

object DateTimeHelper {
  def GetCurrentSQLDateTimeMinusDays(daysOffset: Int): Date = {
    val utilDate =
      DateTime.now()
        .hour(0)
        .minute(0)
        .second(0)
        .minusDays(daysOffset)
        .toDate()
        .getTime

    new java.sql.Date(utilDate)
  }
}