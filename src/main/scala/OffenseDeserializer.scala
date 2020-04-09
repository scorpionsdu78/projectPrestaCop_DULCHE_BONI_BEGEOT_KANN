import java.io.{ObjectInputStream, ByteArrayInputStream}
import java.util

import org.apache.kafka.common.serialization.{Deserializer, Serializer}

class OffenseDeserializer extends Deserializer[Offense]{

  override def configure(configs: util.Map[String,_],isKey: Boolean):Unit = {

  }
  override def deserialize(topic:String,bytes: Array[Byte]) = {
    val byteIn = new ByteArrayInputStream(bytes)
    val objIn = new ObjectInputStream(byteIn)
    val obj = objIn.readObject().asInstanceOf[Offense]
    byteIn.close()
    objIn.close()
    obj
  }
  override def close():Unit = {

  }

}