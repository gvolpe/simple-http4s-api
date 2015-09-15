package com.gvolpe.api.service.special

import org.http4s.dsl._
import org.http4s.server.HttpService
import org.http4s.server.websocket.WS
import org.http4s.websocket.WebsocketBits.{Text, WebSocketFrame}

import scala.concurrent.duration._
import scalaz.concurrent.Strategy.{DefaultStrategy, DefaultTimeoutScheduler}
import scalaz.concurrent.Task
import scalaz.stream.{Exchange, Process, Sink, time}

object WsService {

  def apply(): HttpService = service

  private val service = HttpService {
    case req @ GET -> Root  =>
      val src = time.awakeEvery(1.seconds)(DefaultStrategy, DefaultTimeoutScheduler).map{ d => Text(s"Ping! $d") }
      val sink: Sink[Task, WebSocketFrame] = Process.constant {
        case Text(t, _) => Task.delay(println(t))
        case f          => Task.delay(println(s"Unknown type: $f"))
      }
      WS(Exchange(src, sink))
  }

}
