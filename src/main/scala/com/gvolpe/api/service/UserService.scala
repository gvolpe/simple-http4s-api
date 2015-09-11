package com.gvolpe.api.service

import org.http4s.dsl._
import org.http4s.server.HttpService

object UserService {

  def apply(): HttpService = service

  private val service = HttpService {
    case GET -> Root =>
      Ok(""" List("user1", "user2", "user3") """)
    case GET -> Root / id =>
      Ok(s"user$id by ID")
  }

}
