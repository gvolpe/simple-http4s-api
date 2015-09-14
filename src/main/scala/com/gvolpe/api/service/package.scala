package com.gvolpe.api

import org.http4s.headers.`Content-Type`
import org.http4s.{Charset, EntityEncoder, MediaType}
import play.api.libs.json.{JsValue, Json}
import io.circe.{ Json => CirceJson }

package object service {

  case class User(id: Long, name: String, email: String)
  case class Product(id: Long, name: String)

  object CirceImplicits {

    implicit val circeJsonEncoder: EntityEncoder[CirceJson] =
      EntityEncoder
        .stringEncoder(Charset.`UTF-8`)
        .contramap { json: CirceJson => json.noSpaces }
        .withContentType(`Content-Type`(MediaType.`application/json`, Charset.`UTF-8`))

  }

  object PlayJsonImplicits {

    implicit val playJsonEncoder: EntityEncoder[JsValue] =
      EntityEncoder
        .stringEncoder(Charset.`UTF-8`)
        .contramap { json: JsValue => json.toString() }
        .withContentType(`Content-Type`(MediaType.`application/json`, Charset.`UTF-8`))

    implicit val userJsonFormat = Json.format[User]
    implicit val productJsonFormat = Json.format[Product]

  }

}
