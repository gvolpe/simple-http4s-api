package com.gvolpe.api.service

import org.http4s.dsl._
import org.http4s.server.HttpService

object HomeService {

  def apply(): HttpService = service

  private val service = HttpService {
    case GET -> Root =>
      Ok("Http4s API")
  }

}
