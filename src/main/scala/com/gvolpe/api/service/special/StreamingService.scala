package com.gvolpe.api.service.special

import com.gvolpe.api.service._
import org.http4s.dsl._
import org.http4s.server.HttpService

import scala.concurrent.duration._
import scala.concurrent.forkjoin.ThreadLocalRandom
import scalaz.concurrent.Strategy.DefaultTimeoutScheduler
import scalaz.concurrent.Task
import scalaz.stream.{Process, time}

object StreamingService {

  def apply(): HttpService = service

  private val service = HttpService {
    case GET -> Root =>
      val streamingData = Process.emit(s"Starting stream intervals\n\n") ++ dataStream(10)
      Ok(streamingData).chunked
    case GET -> Root / "delay" =>
      Ok(Process.eval(Task.delay("Hola!")))
    case GET -> Root / "range" =>
      Ok(Process.ranges(0, 100, 10).map(_.toString()))
    case GET -> Root / "random" =>
      Ok(randomNumbers)
  }

  private def intTask: Task[Int] = Task.now(ThreadLocalRandom.current().nextInt(100))

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
