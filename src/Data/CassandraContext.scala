package Data

import Helpers.OptionHelper
import Domain.Logs.Models.{LastUpdatedPosition, LogTrips}
import com.datastax.driver.core._
import com.datastax.driver.core.PoolingOptions

import scala.collection.mutable.ListBuffer
import org.apache.commons.lang.StringEscapeUtils

object CassandraContext {

  val pool = new PoolingOptions()
  val cluster = Cluster.builder()
    .addContactPoints(
      "0.0.0.24",
      "0.0.0.25",
      "0.0.0.26")
    .withPoolingOptions(pool)
    .build();
  val session = cluster.connect("ctrack");

  def GetLastLogPosition(): LastUpdatedPosition = {
    try {
      val stmt = new SimpleStatement("SELECT * FROM ctrack.position;")
      val rs = session.execute(stmt)
      val it = rs.iterator

      while (it.hasNext) {
        val row = it.next

        return LastUpdatedPosition(row.getLong("lastlog"), row.getLong("lasttrip"))
      }

      LastUpdatedPosition()
    }
  }

  def UpdateLogsPosition(pos: LogTrips) : Boolean = {
    try {
      session.execute("TRUNCATE ctrack.position;");

      val batch = new BatchStatement()
        batch.add(new SimpleStatement("INSERT INTO ctrack.position (lastlog, lasttrip) VALUES (" + pos.LogId + ","+ pos.TripId + ");"))
        session.execute(batch)

      return true
    } catch {
      case e => e.printStackTrace()
    }finally {}
    false
  }

  def AddLogFacts(logs: ListBuffer[LogTrips]): Boolean = {
    try {
      val query = "DELETE FROM ctrack.logs where partitionid = 1 AND logid > " + (logs.head.LogId-1) + ";"
      session.execute(query)

      var batches = new ListBuffer[BatchStatement]
      var batch = new BatchStatement()

      logs.foreach { e =>
        if (batch.size() == 10) {
          batches += batch
          batch = new BatchStatement()
        }

        val statusTextString = OptionHelper.GetValue(e.StatusText)
        val zipString = OptionHelper.GetValue(e.ZipCode)
        val maxSpeed = OptionHelper.GetIntValue(e.MaxSpeed)
        val driverNodeId = OptionHelper.GetIntValue(e.DriverNodeId)

        batch.add(new SimpleStatement("INSERT INTO ctrack.logs (" +
          "tripid," +
          "tripstarttimeutc," +
          "tripendtimeutc," +
          "logid," +
          "vehiclenodeid," +
          "busgrpid," +
          "costcentreid," +
          "gpstime," +
          "gpstimedt," +
          "longitude," +
          "latitude," +
          "altitude," +
          "logsdistance," +
          "logsplaceid," +
          "statustext," +
          "streetid," +
          "suburbid," +
          "cityid," +
          "countryid," +
          "zipcode," +
          "speed," +
          "maxspeed," +
          "usedeltaodo," +
          "deltaodo," +
          "drivernodeid," +
          "partitionid" +
          ") VALUES (" +
          e.TripId + ",$$" +
          StringEscapeUtils.escapeJava(e.TripStartTimeUtc.toString) + "$$,$$" +
          StringEscapeUtils.escapeJava(e.TripEndTimeUtc.toString) + "$$," +
          e.LogId + "," +
          e.VehicleNodeId + "," +
          e.BusGrpId + "," +
          e.CostCentreId + "," +
          e.GPSTime + ",$$" +
          StringEscapeUtils.escapeJava(e.GPSTime_DT.toString) + "$$," +
          e.Longitude + "," +
          e.Latitude + "," +
          e.Altitude + "," +
          e.LogsDistance + "," +
          e.LogsPlaceId + ",$$" +
          StringEscapeUtils.escapeJava(statusTextString) + "$$," +
          e.StreetId + "," +
          e.SuburbId + "," +
          e.CityId + "," +
          e.CountryId + ",$$" +
          StringEscapeUtils.escapeJava(zipString) + "$$," +
          e.Speed + "," +
          maxSpeed + "," +
          e.UseDeltaOdo + "," +
          e.DeltaOdo + "," +
          driverNodeId + "," +
          1 +");"))
      }

      batches += batch
      batches.foreach(x => session.executeAsync(x))

      true
    } catch {
      case e => e.printStackTrace
      false
    }finally {}
  }


}
