package com.typesafe.play.redis

import redis.clients.jedis.{Jedis, JedisPool}

import scala.util.control.NonFatal

object JedisHelpers {

  implicit class RichJedisPool(private val pool: JedisPool) {
    def withJedisClient[T](block: Jedis => T): T = {
      val client: Jedis = pool.getResource
      var exception: Throwable = null

      try {
        block(client)
      } catch {
        case NonFatal(e) =>
          exception = e
          throw e
      } finally {
        closeAndAddSuppressed(exception, client)
      }
    }

    private def closeAndAddSuppressed(e: Throwable, client: AutoCloseable): Unit = {
      if (e != null) {
        try {
          client.close()
        } catch {
          case NonFatal(suppressed) => e.addSuppressed(suppressed)
        }
      } else {
        client.close()
      }
    }
  }

}
