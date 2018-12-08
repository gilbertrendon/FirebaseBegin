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

case class FirebaseException(s:String) extends Exception(s)

object User{
  def create(user: User): Future[User] = {
    val ref = Firebase.ref(s"users/${user.id}")
    val userRecord = user.toBean
    val p = new Promise[User]
    ref.setValue(userRecord, new DataBaseReference.CompletionListener()){
       def onComplete(databaseError: DatabaseError, databaseReference: DataBaseReference){
        if(databaseError != null){
          p.setException(FirebaseException(databaseError.getMessage))
        }else{
          p.setValue(user)
        }
      }
    }
  }  
  def get(id: String): Future[User] = {
    val ref = Firebase.ref(s"users/$id")
    val p = new Promise[User]
    ref.addListennerForSingleValueEvent(new ValueEventListener() {
       def onDataChange(snapshot: DataSnapshot) =  {
        val userRecord: UserBean = snapshot.getValue(classOf[UserBean])
        if(userRecord != null){
          p.setValue(userRecord.toCase)
        }else{
          p.setException(UserNotFoundException(s"User $id not found."))
        }
      }
       def onCancelled(databaseError: DatabaseError) = {
        p.setException(FirebaseException(databaseError.getMessage))
      }
    })
  }  
  

}



object Firebase {

 
private val credentials:InputStream = getClass.getResourceAsStream("./curso-410b5-firebase-adminsdk-jtedy-210a3b3862.json")
 private val options = new FirebaseOptions.Builder()
 .setDatabaseUrl("https://curso-410b5.firebaseio.com/")
 .setServiceAccount(credentials)
 .build()
 FirebaseApp.initializeApp(options)
 private val database = FirebaseDatabase.getInstance()
 def ref(path: String): DatabaseReference = database.getReference(path)
 //App.initialize(serviceAccount, "https://curso-410b5.firebaseio.com/")
}
