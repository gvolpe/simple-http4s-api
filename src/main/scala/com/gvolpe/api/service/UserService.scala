package com.gvolpe.api.service

import com.gvolpe.api.service.CirceImplicits._
import org.http4s.dsl._
import org.http4s.server.HttpService
import io.circe.generic.auto._
import io.circe.syntax._

object UserService {

  def apply(): HttpService = service

  private val service = HttpService {
    case GET -> Root =>
      val users = List(User(1, "user1", "user1@mail.com"), User(2, "user2", "user2@mail.com"))
      Ok(users.asJson)
    case GET -> Root / id =>
      Ok(User(id.toLong, s"User$id", s"user$id@mail.com").asJson)
  }

}
