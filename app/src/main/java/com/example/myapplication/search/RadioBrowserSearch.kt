<<<<<<< HEAD
/*
 * RadioBrowserSearch.kt
 * Implements the RadioBrowserSearch class
 * A RadioBrowserSearch performs searches on the radio-browser.info database
 *
 * This file is part of
 * TRANSISTOR - Radio App for Android
 *
 * Copyright (c) 2015-22 - Y20K.org
 * Licensed under the MIT-License
 * http://opensource.org/licenses/MIT
 */
=======
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889


package com.example.myapplication.search

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
<<<<<<< HEAD
import com.example.myapplication.Keys
import com.example.myapplication.helpers.LogHelper
import com.example.myapplication.helpers.NetworkHelper
import com.example.myapplication.helpers.PreferencesHelper
=======
import com.example.myapplication.BuildConfig.VERSION_NAME
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray
import com.example.myapplication.BuildConfig
<<<<<<< HEAD
import com.squareup.picasso.BuildConfig.VERSION_NAME
import de.hdodenhof.circleimageview.BuildConfig.VERSION_NAME
=======
import com.example.myapplication.Keys
import com.example.myapplication.helpers.LogHelper
import com.example.myapplication.helpers.NetworkHelper
import com.example.myapplication.helpers.PreferencesHelper
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889


/*
 * RadioBrowserSearch class
 */
class RadioBrowserSearch(private var radioBrowserSearchListener: RadioBrowserSearchListener) {

    /* Interface used to send back search results */
    interface RadioBrowserSearchListener {
        fun onRadioBrowserSearchResults(results: Array<RadioBrowserResult>) {
        }
    }


    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(RadioBrowserSearch::class.java)


    /* Main class variables */
    private var radioBrowserApi: String
    private lateinit var requestQueue: RequestQueue


    /* Init constructor */
    init {
        // get address of radio-browser.info api and update it in background
        radioBrowserApi = PreferencesHelper.loadRadioBrowserApiAddress()
        updateRadioBrowserApi()
    }


    /* Searches station(s) on radio-browser.info */
    fun searchStation(context: Context, query: String, searchType: Int) {
        LogHelper.v(TAG, "Search - Querying $radioBrowserApi for: $query")

        // create queue and request
        requestQueue = Volley.newRequestQueue(context)
        val requestUrl: String
        when (searchType) {
            // CASE: single station search - by radio browser UUID
            Keys.SEARCH_TYPE_BY_UUID -> requestUrl = "https://${radioBrowserApi}/json/stations/byuuid/${query}"
            // CASE: multiple results search by search term
            else -> requestUrl = "https://${radioBrowserApi}/json/stations/search?name=${query.replace(" ", "+")}"
        }

        // request data from request URL
        val stringRequest = object: JsonArrayRequest(Method.GET, requestUrl, null, responseListener, errorListener) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["User-Agent"] = "$Keys.APPLICATION_NAME ${BuildConfig.VERSION_NAME}"
                return params
            }
        }

        // override retry policy
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 30000
            }
            override fun getCurrentRetryCount(): Int {
                return 30000
            }
            @Throws(VolleyError::class)
            override fun retry(error: VolleyError) {
                LogHelper.w(TAG, "Error: $error")
            }
        }

        // add to RequestQueue.
        requestQueue.add(stringRequest)
    }


    fun stopSearchRequest() {
        if (this::requestQueue.isInitialized) {
            requestQueue.stop()
        }
    }


    /* Converts search result JSON string */
    private fun createRadioBrowserResult(result: String): Array<RadioBrowserResult> {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("M/d/yy hh:mm a")
        val gson = gsonBuilder.create()
        return gson.fromJson(result, Array<RadioBrowserResult>::class.java)
    }


    /* Updates the address of the radio-browser.info api */
    private fun updateRadioBrowserApi() {
        GlobalScope.launch {
            val deferred: Deferred<String> = async { NetworkHelper.getRadioBrowserServerSuspended() }
            radioBrowserApi = deferred.await()
        }
    }


    /* Listens for (positive) server responses to search requests */
    private val responseListener: Response.Listener<JSONArray> = Response.Listener<JSONArray> { response ->
        if (response != null) {
            radioBrowserSearchListener.onRadioBrowserSearchResults(createRadioBrowserResult(response.toString()))
        }
    }

    /* Listens for error response from server */
    private val errorListener: Response.ErrorListener = Response.ErrorListener { error ->
        LogHelper.w(TAG, "Error: $error")
    }

}