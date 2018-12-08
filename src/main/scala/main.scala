import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}
import controllers._
import models.repository.{CarRepository, CarRepositoryMongo}
import mongodb.Mongo
import scala.beans.BeanProperty
import com.twitter.util.{Future,Promise}

import java.io.InputStream
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database._
import scala.collection.JavaConverters._
import scala.collection.breakOut
import models._


object Main extends App {
val gilbert = new User("z106aad","Tim","tim@omnicorp.com")
User.create(gilbert).map(user => println(s"Created ${user.firstname}"))
val userId = "-LT3x-YmSbAqUlR8O3__"
User.get(userId).map(user=>println(s"Retrieved ${user.firstname}"))

}
