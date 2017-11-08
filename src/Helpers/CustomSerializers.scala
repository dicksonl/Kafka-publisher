package Helpers

import java.lang.reflect.Type
import com.google.gson._
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}

object CustomSerializers {
  val DATE_TIME_FORMATTER = ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC)

  class ListSerializer extends JsonSerializer[Seq[Any]] {
    override def serialize(src: Seq[Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      import scala.collection.JavaConverters._
      context.serialize(src.toList.asJava)
    }
  }

  class MapSerializer extends JsonSerializer[Map[Any,Any]] {
    override def serialize(src: Map[Any,Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      import scala.collection.JavaConverters._
      context.serialize(src.asJava)
    }
  }

  class OptionSerializer extends JsonSerializer[Option[Any]] {
    override def serialize(src: Option[Any], typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      src match {
        case None => JsonNull.INSTANCE
        case Some(v) => context.serialize(v)
      }
    }
  }

  class DateTimeSerializer extends JsonSerializer[DateTime] {
    override def serialize(src: DateTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
      new JsonPrimitive(DATE_TIME_FORMATTER.print(src))
    }
  }
}
