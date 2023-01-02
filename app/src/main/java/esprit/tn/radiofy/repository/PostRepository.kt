package esprit.tn.radiofy.repository

import android.content.Context
import android.util.Log
import esprit.tn.radiofy.models.EventItem
import esprit.tn.radiofy.utils.ApiInterface
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PostRepository {
    val apiInterface = ApiInterface.create()
    companion object{
        var instance: PostRepository? = null
        lateinit var mContext: Context
        lateinit var title: String
    }

    fun getInstance(context: Context): PostRepository {
        mContext = context
        if (instance == null)
            instance = PostRepository()
        return instance!!
    }

    fun getEventsList(): MutableList<EventItem> {
        var listData = mutableListOf<EventItem>()

        try {
            //lehne bech ysir fetch data shemsfm
//            var radios_urls_array = arrayOf<String>("https://www.shemsfm.net/fr/actualites/actualites-tunisie-news",
//                "https://www.radiooxygene.tn/category/actualites/national/",
//                "https://www.mosaiquefm.net/fr/actualites/actualite-national-tunisie")
//            for (radio_url in radios_urls_array) {
//                Log.e("test",radio_url)
//            }
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

                val map: HashMap<String, String> = HashMap()
                map["title"] = title.toString()
                map["radio"] = "ShemsFm"



                apiInterface.postVerifier(map).enqueue(object : Callback<EventItem> {
                    override fun onResponse(call: Call<EventItem>, response: Response<EventItem>) {
                        val post = response.body()
                      if (post==null){
                          val map1: HashMap<String, String> = HashMap()
                          map1["title"] = title.toString()
                          map1["author"] = "Shems"
                          map1["desc"] = desc.toString()
                          map1["image"] = image.toString()
                          map1["url"] = eventUrl.toString()

                          apiInterface.postPost(map1).enqueue(object : Callback<EventItem>{
                              override fun onResponse(
                                  call: Call<EventItem>,
                                  response: Response<EventItem> ) {
                                  val post_created = response.body()
                                  if (post_created==null)
                                  {     Log.e("hamma","tahan")
//                                      println(response)
                                  }
                              }


                              override fun onFailure(call: Call<EventItem>, t: Throwable) {
                                 Log.e("test create=ion post","test metadech")
                              }

                          })

                      }
                    }

                    override fun onFailure(call: Call<EventItem>, t: Throwable) {
                        Log.e("problem_postVerifier","mehbtch tetada l cnx ma el backServer")
                    }
                })

                listData.add(EventItem(i,title,date,desc,image,eventUrl,))

            }
            //lehne youfa fetch data shemsfm

            //lehne bech ysir fetch data oxygenfm
            val url_jawharafm = "https://www.jawharafm.net/fr/articles/actualite/tunisie/90"

            val doc_jawharafm = Jsoup.connect(url_jawharafm).get()
            val events_jawharafm = doc_jawharafm.select("div.elem_ev")
            val eventsSize_jawharafm = events_jawharafm.size
//            Log.e("eventsSize_jawharafm",eventsSize_jawharafm.toString())
            for (i in 0 until eventsSize_jawharafm) {
                val title = events_jawharafm.select("h2.titr_ev")
                    .select("a")
                    .eq(i)
                    .text()
                val date = events_jawharafm.select("span.dat_ev")
                    .eq(i)
                    .text()
                val desc = events_jawharafm.select("p.disc_ev")
                    .eq(i)
                    .text()
                val eventUrl = "https://www.jawharafm.net" + events_jawharafm.select("h2.titr_ev")
                    .select("a")
                    .eq(i)
                    .attr("href")
                val image = "https://www.jawharafm.net" + events_jawharafm.select("div.img_ev")
                    .select("a")
                    .select("img")
                    .eq(i)
                    .attr("src")

                val map: HashMap<String, String> = HashMap()
                map["title"] = title.toString()
                map["radio"] = "ShemsFm"



                apiInterface.postVerifier(map).enqueue(object : Callback<EventItem> {
                    override fun onResponse(call: Call<EventItem>, response: Response<EventItem>) {
                        val post = response.body()
                        if (post==null){
                            val map1: HashMap<String, String> = HashMap()
                            map1["title"] = title.toString()
                            map1["author"] = "Jawhra"
                            map1["desc"] = desc.toString()
                            map1["image"] = image.toString()
                            map1["url"] = eventUrl.toString()

                            apiInterface.postPost(map1).enqueue(object : Callback<EventItem>{
                                override fun onResponse(
                                    call: Call<EventItem>,
                                    response: Response<EventItem> ) {
                                    val post_created = response.body()
                                    if (post_created==null)
                                    {     Log.e("hamma","tahan")
//                                        println(response)
                                    }
                                }


                                override fun onFailure(call: Call<EventItem>, t: Throwable) {
                                    Log.e("test create=ion post","test metadech")
                                }

                            })

                        }
                    }

                    override fun onFailure(call: Call<EventItem>, t: Throwable) {
                        Log.e("problem_postVerifier","mehbtch tetada l cnx ma el backServer")
                    }
                })
//                listData.add(EventItem(i,title,date,desc,image,eventUrl))

            }



            //lehne youfa fetch data oxygenfm

            //lehne bech ysir fetch data mosaiquefm
//            val url_mosaiquefm = "https://www.radiooxygene.tn/category/actualites/international/"
//
//            val doc_oxygenfm = Jsoup.connect(url_jawharafm).get()
//            val events_oxygenfm = doc_oxygenfm.select("post-item")
//            val eventsize_oxygenfm = events_oxygenfm.size
//            Log.e("eventsize_oxygenfm",eventsize_oxygenfm.toString())
//            for (i in 0 until eventsize_oxygenfm) {
//                val title = events_oxygenfm.select("h3.post-title")
//                    .select("a")
//                    .eq(i)
//                    .text()
//                val date = events_oxygenfm.select("span")
//                    .eq(i)
//                    .text()
//                val desc = events_oxygenfm.select("p.post-excerpt")
//                    .eq(i)
//                    .text()
//                val eventUrl = events_oxygenfm.select("h3.post-title")
//                    .select("a")
//                    .eq(i)
//                    .attr("href")
//                val image = events_oxygenfm.select("a")
//                    .select("img")
//                    .eq(i)
//                    .attr("src")
//                val map: HashMap<String, String> = HashMap()
//                map["title"] = title.toString()
//                map["radio"] = "MosaiqueFM"



//                apiInterface.postVerifier(map).enqueue(object : Callback<EventItem> {
//                    override fun onResponse(call: Call<EventItem>, response: Response<EventItem>) {
//                        val post = response.body()
//                        if (post==null){
//                            val map1: HashMap<String, String> = HashMap()
//                            map1["title"] = title.toString()
//                            map1["author"] = "Jawhra"
//                            map1["desc"] = desc.toString()
//                            map1["image"] = image.toString()
//                            map1["url"] = eventUrl.toString()
//
//                            apiInterface.postPost(map1).enqueue(object : Callback<EventItem>{
//                                override fun onResponse(
//                                    call: Call<EventItem>,
//                                    response: Response<EventItem> ) {
//                                    val post_created = response.body()
//                                    if (post_created==null)
//                                    {     Log.e("hamma","tahan")
//                                        println(response)
//                                    }
//                                }
//
//
//                                override fun onFailure(call: Call<EventItem>, t: Throwable) {
//                                    Log.e("test create=ion post","test metadech")
//                                }
//
//                            })
//
//                        }
//                    }
//
//                    override fun onFailure(call: Call<EventItem>, t: Throwable) {
//                        Log.e("problem_postVerifier","mehbtch tetada l cnx ma el backServer")
//                    }
//                })
//                listData.add(EventItem(i,title,date,desc,image,eventUrl))

//            }
            //lehne youfa fetch data mosaiquefm


        }catch (e: IOException) {
            e.printStackTrace()
        }
        apiInterface.getALlPosts().enqueue(object : Callback<List<EventItem>> {
            override fun onResponse(
                call: Call<List<EventItem>>,
                response: Response<List<EventItem>>
            ) { var k=1;
                for(i in response.body()!!){
                    println(i)
//                    listData.add(i)
                    listData.add(EventItem(k,i.title,i.date,i.desc,i.image,i.url))
                    k++
                }


            }

            override fun onFailure(call: Call<List<EventItem>>, t: Throwable) {
               // TODO("Not yet implemented")
                // kamalha mara okhra bech ta3em cnx m3a lbase :)
                println(" ")
            }
        })
//        print(listData)
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