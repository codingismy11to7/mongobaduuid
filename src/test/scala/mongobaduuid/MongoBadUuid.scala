package mongobaduuid

import org.scalatest._
import reactivemongo.api.bson.exceptions.TypeDoesNotMatchException
import reactivemongo.api.bson.{BSONBinary, BSONReader, BSONString, Subtype}

import java.nio.ByteBuffer
import java.util.UUID
import scala.util.{Failure, Try}

class MongoBadUuid extends FlatSpec with Matchers {
  "MongoUUIDs" should "decode from binary" in {
    implicit val uuidReader: BSONReader[UUID] = BSONReader.from {
      case BSONString(repr) => Try(UUID fromString repr)

      case bin @ BSONBinary(Subtype.UuidSubtype) =>
        Try {
          val bb = ByteBuffer.wrap(bin.byteArray)
          new UUID(bb.getLong, bb.getLong)
        }

      case bson => Failure(TypeDoesNotMatchException("BSONString|BSONBinary", bson.getClass.getSimpleName))
    }

    val id = UUID.randomUUID()
    BSONBinary(id).asOpt[UUID] should contain(id)
  }

  it should "not fail with default decoder" in {
    val id = UUID.randomUUID()
    BSONBinary(id).asOpt[UUID] should contain(id)
  }
}
