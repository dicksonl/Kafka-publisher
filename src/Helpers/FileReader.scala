package Helpers

import java.io.{BufferedReader, FileReader}

object FileReader {

  def readFile(filename : String) : String =
  {
    try
    {
      val reader = new BufferedReader(new FileReader(filename))
      val line = reader.readLine()
      reader.close()

      line
    }
    catch
    {
      case e => e.printStackTrace
      null
    }
  }
}
