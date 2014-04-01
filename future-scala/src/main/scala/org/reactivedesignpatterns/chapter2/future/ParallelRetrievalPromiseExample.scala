package org.reactivedesignpatterns.chapter2.future

import java.util.concurrent.{ CompletableFuture, ForkJoinPool }
import scala.concurrent.{ ExecutionContext, Future, Promise }
import scala.concurrent.duration._
import scala.util.Try

trait Customer {
  def getId(): Long
  def getName(): String
  def getAddress(): String
  def getPhone(): String
}

trait CacheRetriever {
  def getCustomer(customerId: Long): Customer
}

trait DBRetriever {
  def getCustomer(customerId: Long): Customer
}

class ParallelRetrievalPromiseExample(cacheRetriever: CacheRetriever, dbRetriever: DBRetriever) {
  def retrieveCustomer(id: Long): Future[Customer] = {
    val returnCustomerPromise = Promise[Customer]()
    implicit val ec = ExecutionContext.fromExecutor(new ForkJoinPool())
    implicit val timeout = 250 milliseconds

    Future {
      returnCustomerPromise.tryComplete(Try(cacheRetriever.getCustomer(id)))
    }
    Future {
      returnCustomerPromise.tryComplete(Try(dbRetriever.getCustomer(id)))
    }

    returnCustomerPromise.future
  }
}