package Domain.Logs

import Data.CassandraContext
import Models.LogTrips

import scala.collection.mutable.ListBuffer

object FactsStore {
  def write(logs: ListBuffer[LogTrips]): Boolean = {
    CassandraContext.AddLogFacts(logs)
  }
}
