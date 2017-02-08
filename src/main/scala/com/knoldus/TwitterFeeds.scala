package com.knoldus

import org.apache.log4j.Logger
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Query, Status, Twitter, TwitterFactory}
import scala.concurrent.duration._
import scala.collection.JavaConverters._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Qus:create an application in which:
  * 1.Retrieve tweet  on the basis of HASHTAG(#hashtag)
  * 2.Find number of tweets
  * 3.Find re-tweets per tweet
  * 4.Find Average number of likes
  *
  */

class TwitterFeeds(hashtag: String) {

  val logger: Logger = Logger.getLogger(this.getClass)
  val consumerKey: String = "Consumer_Key"
  val consumerSecretKey: String = "Consumer_Secret_key"
  val accessToken: String = "Access_token"
  val accessTokenSecret: String = "Access_token_Private"
  val configurationBuilder = new ConfigurationBuilder()
  configurationBuilder.setDebugEnabled(true)
    .setOAuthConsumerKey(consumerKey)
    .setOAuthConsumerSecret(consumerSecretKey)
    .setOAuthAccessToken(accessToken)
    .setOAuthAccessTokenSecret(accessTokenSecret)
  val twitter: Twitter = new TwitterFactory(configurationBuilder.build()).getInstance()
  val query = new Query(hashtag)
  val date: String = "2017-02-5"

  /**
    * if tweets found retrieves those tweets.
    *
    * @return
    */
  def getTweets: Future[List[Status]] = Future {
    query.setSince(date)
    val tweets = twitter.search(query).getTweets.asScala.toList
    tweets
  }

  /**
    * on the basis of tweets found calculate the total count.
    *
    * @return
    */
  def getNumberOfTweets: Future[Int] = Future {
    query.setSince(date)
    val tweets = twitter.search(query).getTweets.asScala.toList
    logger.info(s"\n Number of Tweets are:: ${tweets.size}")
    tweets.size
  }

  /**
    * on the basis of tweets captrured calculate the retweets.
    *
    * @return
    */
  def getNumberOfRetweets: Future[Int] = Future {
    query.setSince(date)
    val tweets = twitter.search(query).getTweets.asScala.toList
    val retweets = tweets.map(tweet => tweet.getRetweetCount)
    logger.info(s"\n Number of ReTweets are:: $retweets")
    tweets.map(tweet => tweet.getFavoriteCount)
    retweets.sum
  }

  /**
    * Retrieves number of Likes on tweet
    *
    * @return
    */
  def getNumberOfLikes: Future[Int] = Future {
    query.setSince(date)
    val tweets = twitter.search(query).getTweets.asScala.toList
    val likes = tweets.map(tweet => tweet.getFavoriteCount)
    logger.info(s"\n Number of ReTweets are:: $likes")
    likes.size
  }

  /**
    * Retrives the Average Number of Tweets
    *
    * @return
    */
  def getAverageNumberOfTweets: Future[Float] = Future {
    val tweets = Await.result(getNumberOfTweets, 20.seconds)
    val retweets = Await.result(getNumberOfRetweets, 20.seconds)
    val average = retweets / tweets
    average
  }

}
