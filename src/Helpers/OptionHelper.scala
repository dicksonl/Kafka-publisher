package Helpers

import java.sql.Timestamp

object OptionHelper {

  def GetIntValue(nullableValue: Option[Int]): Int = {
    if(nullableValue == Some(null)){
      return 0
    }
    return nullableValue.getOrElse(0)
  }

  def GetValue(nullableValue : Option[String]) : String = {
    if(nullableValue == Some(null)){
      return ""
    }
    return nullableValue.getOrElse("")
  }

  def GetTimeValue(nullableValue: Option[Timestamp]) : String = {
    if(nullableValue == Some(null)){
      return ""
    }
    return nullableValue.getOrElse("").toString
  }

}
