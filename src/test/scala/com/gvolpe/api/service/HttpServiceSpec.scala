package com.gvolpe.api.service

import org.http4s.EntityBody
import org.scalatest.{Matchers, WordSpecLike}
import scodec.bits.ByteVector

import scalaz.stream.Process
import scalaz.stream.Process._

trait HttpServiceSpec extends WordSpecLike with Matchers {

  implicit class String2ByteVector(value: String) {
    def asByteVector: Process[Nothing, ByteVector] = emit(value).map(s => ByteVector(s.getBytes))
  }

  implicit class ByteVector2String(body: EntityBody) {
    def asString: String = {
      val array = body.runLog.run.reduce(_ ++ _).toArray
      new String(array.map(_.toChar))
    }
  }

}
