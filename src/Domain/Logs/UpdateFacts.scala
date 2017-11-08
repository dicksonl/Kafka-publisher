package Domain.Logs

import Data.CassandraContext
import Domain.Logs.Models.{LastUpdatedPosition, LogTrips}

object UpdateFacts {

  def runUpdate() = {
    val pos = CassandraContext.GetLastLogPosition
    if(pos != 0){
      updateData(pos.LastLogId, pos.LastTripId)
    }else {
      updateData(26125049, 603060)
    }
  }

  def updateData(lastWrittenLogId: Long, LastWrittenTripId: Long) = {
    val logs = Data.SqlServerContext.getLogs(lastWrittenLogId, LastWrittenTripId);//10000000
    if(FactsStore.write(logs)){
      LogsProducer.produce(logs)
      this.writePosition(logs.last)
    }
  }

  def writePosition(pos : LogTrips) = {
    CassandraContext.UpdateLogsPosition(pos)
  }
}
