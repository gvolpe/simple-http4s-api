package com.gvolpe.api.service

import org.http4s.dsl._
import org.http4s.server.HttpService

import scala.concurrent.duration._
import scalaz._
import scalaz.\/._
import scalaz.concurrent.Strategy.DefaultTimeoutScheduler
import scalaz.concurrent.Task
import scalaz.stream.{Process, time}

object StreamingService {

  def apply(): HttpService = service

  private val service = HttpService {
    case GET -> Root =>
      val streamingData = Process.emit(s"Starting stream intervals\n\n") ++ dataStream(10)
      Ok(streamingData)
    case GET -> Root / "delay" =>
      Ok(Process.eval(Task.delay("Hola!")))
    case GET -> Root / "range" =>
      Ok(Process.ranges(0, 100, 10).map(_.toString()))
    case GET -> Root / "task" =>
      Ok(randomNumbers)
  }

  private def asyncReadInt(callback: Throwable \/ Int => Unit): Unit = {
    // imagine an asynchronous task which eventually produces an `Int`
    try {
      Thread.sleep(50)
      val result = (math.random * 100).toInt
      callback(right(result))
    } catch { case t: Throwable => callback(left(t)) }
  }

  private val intTask: Task[Int] = Task.async(asyncReadInt)

  private def randomNumbers: Process[Task, String] = {
    Process.eval(intTask)
      .repeat
      .take(25)
      .filter(_ > 10)
      .filter(_ % 2 != 0)
      .map(_.toString)
  }

  private def dataStream(n: Int): Process[Task, String] = {
    implicit def defaultScheduler = DefaultTimeoutScheduler
    val interval = 100.millis
    time.awakeEvery(interval)
      .map(_ => s"Current system time: ${System.currentTimeMillis()} ms\n")
      .take(n)
  }

}
