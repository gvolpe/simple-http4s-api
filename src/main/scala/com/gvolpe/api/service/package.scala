package com.gvolpe.api

import org.http4s.headers.`Content-Type`
import org.http4s.{Charset, EntityEncoder, MediaType}
import org.json4s._
import org.json4s.Extraction.decompose
import org.json4s.jackson.JsonMethods.compact
import org.json4s.jackson.JsonMethods.render

package object service {

  case class User(id: Long, name: String, email: String)
  case class Product(id: Long, name: String)

  implicit val jsonFormats: Formats = DefaultFormats

  object Implicits {

    implicit val productAsJsonEncoder: EntityEncoder[Product] =
      EntityEncoder
        .stringEncoder(Charset.`UTF-8`)
        .contramap { r: Product => compact(render(json(r))) }
        .withContentType(`Content-Type`(MediaType.`application/hal+json`, Charset.`UTF-8`))

    implicit val userAsJsonEncoder: EntityEncoder[User] =
      EntityEncoder
        .stringEncoder(Charset.`UTF-8`)
        .contramap { r: User => compact(render(json(r))) }
        .withContentType(`Content-Type`(MediaType.`application/json`, Charset.`UTF-8`))

    def json[A <: Equals](a: A): JValue =
      decompose(a)

  }

}
