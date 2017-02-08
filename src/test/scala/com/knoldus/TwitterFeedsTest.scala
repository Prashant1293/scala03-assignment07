package com.knoldus

import org.apache.log4j.Logger
import org.scalatest.FunSuite
import scala.concurrent.duration._
import scala.concurrent.Await

class TwitterFeedsTest extends FunSuite {
  val logger: Logger = Logger.getLogger(this.getClass)
  val hashtagParam: String = "#Jigyasa"
  val twitterTest = new TwitterFeeds(hashtagParam)

  test("Test Creation Of Twitter Api Object") {
    assert(twitterTest.consumerKey == "Consumer_key")
  }

  test("Test Initializing accessToken") {
    assert(twitterTest.accessToken != "")
  }

  test("Test Initializing consumerKey") {
    assert(twitterTest.consumerKey != "")
  }

  test("Test Initializing consumerSecretKey") {
    assert(twitterTest.consumerSecretKey != "")
  }

  test("Test Initializing accessTokenSecret") {
    assert(twitterTest.accessTokenSecret != "")
  }

  test("Test Getting Tweets") {
    assert(Await.result(twitterTest.getTweets, 15.seconds) != Nil)
  }

  test("Test Tweets Count") {
    assert(Await.result(twitterTest.getNumberOfTweets, 15.seconds) >= 0)
  }
  test("Test ReTweets Count") {
    assert(Await.result(twitterTest.getNumberOfRetweets, 30.seconds) >= 0)
  }
  test("Test Number Of Likes On Tweets") {
    assert(Await.result(twitterTest.getNumberOfLikes, 15.seconds) >= 0)
  }
  test("Test Number Of Average Likes On Tweets") {
    assert(Await.result(twitterTest.getAverageNumberOfTweets, 50.seconds) >= 0)
  }

}
