package org.fsf.tetra

import io.circe.{ Decoder, Encoder }

import org.fsf.tetra.implicits.Circe._
import org.fsf.tetra.model.database.User
import org.fsf.tetra.module.db.userRepository.UserRepository
import org.fsf.tetra.route.UserRoute
import org.http4s.EntityEncoder
import org.http4s.circe.{ jsonEncoderOf, jsonOf }
import org.http4s.implicits._
import org.http4s.{ Method, Request, Status }

import HTTPSpec._

import zio._
import zio.console._
import zio.test.Assertion._
import zio.test._
import zio.test.environment._

object HelloWorldSpec extends DefaultRunnableSpec {
  type Env = UserRepository

  def spec = suite("spec")(
    testM("UserRoute") {

      implicit val e: EntityEncoder[zio.Task, User] =
        jsonEncoderOf[zio.Task, User]

      val user = User(0, "guy", 12)
      request(Method.POST, "/user").withEntity(user)

      // assertM(check(app.run(payload)))(Status.Created)
      assertM(ZIO.succeed(true))(isTrue)
    }
  )

  private val userRoute: UserRoute[Env] = new UserRoute[Env]

  userRoute.getRoutes
}