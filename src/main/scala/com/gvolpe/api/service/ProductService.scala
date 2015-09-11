package com.gvolpe.api.service

import org.http4s.dsl._
import org.http4s.server.HttpService

object ProductService {

  def apply(): HttpService = service

  private val service = HttpService {
    case GET -> Root / "products" =>
      Ok(""" List("p1", "p2", "p3") """)
    case GET -> Root / "products" / id =>
      Ok(s"p$id by ID")
  }

}
