package Domain.Logs.Models

import java.sql.Timestamp

class LogTrips (
  var TripId : Long,
  var TripStartTimeUtc: Timestamp,
  var TripEndTimeUtc : Timestamp,
  var LogId: Long,
  var VehicleNodeId: Int,
  var BusGrpId: Int,
  var CostCentreId: Int,
  var GPSTime: Long,
  var GPSTime_DT: Timestamp,
  var Longitude : Float,
  var Latitude : Float,
  var Altitude : Int,
  var LogsDistance : Float,
  var LogsPlaceId : Int,
  var StatusText : Option[String],
  var StreetId : Long,
  var SuburbId : Long,
  var CityId : Int,
  var CountryId : Int,
  var ZipCode : Option[String],
  var Speed : Int,
  var MaxSpeed : Option[Int],
  var UseDeltaOdo : Int,
  var DeltaOdo : Long,
  var DriverNodeId : Option[Int]
)
