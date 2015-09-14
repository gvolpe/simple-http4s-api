package com.gvolpe.api.service

import org.http4s.{Request, Status, Uri}

class HomeServiceSpec extends HttpServiceSpec {

  "Home Service" should {

    val service = HomeService()

    "Not find the unknown url request" in {
      val request = new Request(uri = Uri(path = "/unknown"))
      val response = service.run(request).run

      response.status should be (Status.NotFound)
    }

    "Get the welcome message" in {
      val request = new Request()
      val response = service.run(request).run

      response.status should be (Status.Ok)
      response.body should be ("Http4s API".asByteVector)
    }

  }

}
