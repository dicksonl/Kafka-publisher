package Domain.Logs

import java.util.Properties

import Domain.Logs.Models.LogTrips
import Helpers.CustomSerializers.{OptionSerializer}
import org.apache.kafka.clients.producer._
import scala.collection.mutable.ListBuffer
import com.google.gson.{GsonBuilder}

object LogsProducer {
  val  props = new Properties()
  props.put("bootstrap.servers", "0.0.0.0:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val TOPIC="logs"

  def produce(logs: ListBuffer[LogTrips]): Unit ={
    val producer = new KafkaProducer[String, String](props)

    val gson = new GsonBuilder()
      .registerTypeHierarchyAdapter(classOf[Option[Any]], new OptionSerializer)
      .create()

    try {
      logs.foreach(x => {
          val record = new ProducerRecord[String, String](x.BusGrpId.toString, gson.toJson(x, classOf[LogTrips]))
          producer.send(record)
        }
      )
    }catch{
      case e => e.printStackTrace()
    }
    finally {
      producer.close()}
  }
}
