package Data

import java.sql._
import com.microsoft.sqlserver.jdbc._
import Domain.Logs.Models.LogTrips
import scala.collection.mutable.ListBuffer

object SqlServerContext {
  val bespokeConnectionUrl = "jdbc:sqlserver://test:1433;instanceName=s1;" +
    "databaseName=d;Persist Security Info=True;"

  val connectionUrl = "jdbc:sqlserver://test:1433;instanceName=s1;" +
    "databaseName=d;Persist Security Info=True;"


  def getLogs(logId: Long, TripId: Long) : ListBuffer[LogTrips] = {
    val logs = new ListBuffer[LogTrips]
    val con = DriverManager.getConnection(bespokeConnectionUrl, "user", "$pass$")

    try {
      val cstmt = con.prepareCall("{call dbo.name(?, ?)}")
      cstmt.setLong(1, logId);
      cstmt.setLong(2, TripId);
      val rs = cstmt.executeQuery();

      while (rs.next()) {
        var log = new LogTrips(
          rs.getLong("TripId"),
          rs.getTimestamp("TripStartTime_UTC"),
          rs.getTimestamp("TripEndTime_UTC"),
          rs.getLong("LogId"),
          rs.getInt("NodeId"),
          rs.getInt("BusGrpId"),
          rs.getInt("CostCentreId"),
          rs.getLong("GPSTime"),
          rs.getTimestamp("GPSTime_DT"),
          rs.getFloat("Longitude"),
          rs.getFloat("Latitude"),
          rs.getInt("Altitude"),
          rs.getFloat("LogsDistance"),
          rs.getInt("LogsPlaceId"),
          Some(rs.getString("StatusText")),
          rs.getLong("StreetId"),
          rs.getLong("SuburbId"),
          rs.getInt("CityId"),
          rs.getInt("CountryId"),
          Some(rs.getString("ZipCode")),
          rs.getInt("Speed"),
          Some(rs.getInt("MaxSpeed")),
          rs.getInt("UseDeltaODO"),
          rs.getLong("DeltaODO"),
          Some(rs.getInt("DriverNodeId1"))
        )
        logs += log;
      };
    } catch {
      case e => e.printStackTrace
    } finally {
      con.close();
    }
    logs
  }
}
