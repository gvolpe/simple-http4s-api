package com.gvolpe.api.service

import org.http4s.{Request, Status, Uri}

class UserServiceSpec extends HttpServiceSpec {

  "User Service" should {

    val service = UserService()

    "Throw a NumberFormatException when trying to parse the User ID from the url" in {
      val request = new Request(uri = Uri(path = "/unknown"))

      intercept[NumberFormatException] {
        service.run(request).run
      }
    }

    "Not find the unknown url request" in {
      val request = new Request(uri = Uri(path = "/123/unknown"))
      val response = service.run(request).run

      response.status should be (Status.NotFound)
    }

    "Get the list of users" in {
      val request = new Request()
      val response = service.run(request).run

      response.status should be (Status.Ok)
      val expected = """ [{"email":"user1@mail.com","name":"user1","id":1},{"email":"user2@mail.com","name":"user2","id":2}] """.trim
      response.body.asString should be (expected)
    }

    "Get the user by Id" in {
      val userId = 123
      val request = new Request(uri = Uri(path = s"/$userId"))
      val response = service.run(request).run

      response.status should be (Status.Ok)
      val expected = s""" {"email":"user$userId@mail.com","name":"User$userId","id":$userId} """.trim
      response.body.asString should be (expected)
    }

  }

}
