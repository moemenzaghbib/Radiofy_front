package com.example.myapplication.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.EventItem
import com.example.myapplication.repository.PostRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class EventViewModel: ViewModel() {
    var items: MutableLiveData<MutableList<EventItem>> = MutableLiveData()
    fun init (context: Context) {
        if (items.value != null )
            return
    }
    private val repo = PostRepository()
    fun fetchData(): MutableLiveData<MutableList<EventItem>> {
        // nekhthou data de facon async lehne
        viewModelScope.launch(IO) {
            // lezel el use mte3 el postValue khatar nekhdmou de faocn async
            items.postValue(repo.getEventsList())

        }
        return items
    }

    fun fetchEvent(url: String): MutableLiveData<EventItem>{
        val item = MutableLiveData<EventItem>()
        viewModelScope.launch(IO) {
            item.postValue(repo.getEvent(url))
                    }
        return item
    }

}