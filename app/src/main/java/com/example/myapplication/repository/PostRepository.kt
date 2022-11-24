package com.example.myapplication.repository

import android.content.Context
import com.example.myapplication.models.EventItem
import org.jsoup.Jsoup
import java.io.IOException

class PostRepository {

    companion object{
        var instance: PostRepository? = null
        lateinit var mContext: Context
    }

    fun getInstance(context: Context): PostRepository {
        mContext = context
        if (instance == null)
            instance = PostRepository()
        return instance!!
    }

    fun getEventsList(): MutableList<EventItem> {
        val listData = mutableListOf<EventItem>()
        try {
            val url = "https://www.shemsfm.net/fr/actualites/actualites-tunisie-news"
            val doc = Jsoup.connect(url).get()
            val events = doc.select("div.item.liste_news_other_")
            val eventsSize = events.size
            for (i in 0 until eventsSize) {
                val title = events.select("h2.title")
                    .select("a")
                    .eq(i)
                    .text()
                val date = events.select("div.date")
                    .eq(i)
                    .text()
                val desc = events.select("p.intro")
                    .eq(i)
                    .text()
                val eventUrl = "https://www.shemsfm.net" + events.select("div.col-xs-5.col-sm-5.col-md-5.thumb")
                    .select("a")
                    .eq(i)
                    .attr("href")
                val image = "https://media.shemsfm.net/" + events.select("div.col-xs-5.col-sm-5.col-md-5.thumb")
                    .select("a")
                    .select("img")
                    .eq(i)
                    .attr("src")
                listData.add(EventItem(i,title,date,desc,image,eventUrl))
            }
        }catch (e: IOException) {
            e.printStackTrace()
        }
        return listData
    }

    fun getEvent(url: String): EventItem {
        val item = EventItem()
        try {
            val document = Jsoup.connect(url).get()
            val title = document.select("h1.title.lg-size.color_til")
                .text()
            val date = document.select("span")
                .select("i")
                .text()

            val detail = document.select("div.contentBody")
                .select("p")
                .eq(1)//select one or all in cycle
                .text()

            val image = document.select("div.thumb")
                .select("img")
                .attr("src")

            item.title = title
            item.date = date
            item.desc = detail
            item.image = image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return item
    }
}