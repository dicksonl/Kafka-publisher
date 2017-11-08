package main

import Domain.Logs.UpdateFacts
import java.util.concurrent._

object MainApp extends App{

  val task1 = new Runnable {
    def run() = UpdateFacts.runUpdate
  }

  try {
    val executor = new ScheduledThreadPoolExecutor(1)
    executor.scheduleWithFixedDelay(task1, 0, 60, TimeUnit.SECONDS)
  }catch {
    case e => e.printStackTrace()
    }

}