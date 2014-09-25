package com.taintech.immobili.example.apache

import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/25/14
 * Time: 11:30
 */
object HttpClientMain {
  def main(args: Array[String]): Unit ={
    val client: HttpClient  =  HttpClientBuilder.create().build()
    val request = new HttpGet("http://127.0.0.1:5984/")
    val response = client.execute(request)
    Console println scala.io.Source.fromInputStream(response.getEntity.getContent).getLines().mkString("\n")
  }
}
