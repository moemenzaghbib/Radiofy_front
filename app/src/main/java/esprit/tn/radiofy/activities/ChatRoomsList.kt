package esprit.tn.radiofy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R
import esprit.tn.radiofy.adapters.ChatRooms_list_Adapter
import esprit.tn.radiofy.adapters.CommentAdapter
import esprit.tn.radiofy.models.ChatRoom
import esprit.tn.radiofy.models.Comment
import esprit.tn.radiofy.models.data
import esprit.tn.radiofy.utils.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatRoomsList : AppCompatActivity() {
    lateinit var chatroom_list_ReycleView: RecyclerView
    lateinit var recyclerChatRoom_list_Adapter: ChatRooms_list_Adapter
    val apiInterface = ApiInterface.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_rooms_list)
        chatroom_list_ReycleView = findViewById(R.id.chat_list_recycleview)
        val email = intent.getStringExtra("email").toString()

        val map: HashMap<String, String> = HashMap()

        apiInterface.GetAllChatRooms().enqueue(object: Callback<ArrayList<data>> {
            override fun onResponse(
                call: Call<ArrayList<data>>,
                response: Response<ArrayList<data>>
            ) {
                val chatrooms = response.body()
                if(chatrooms!=null){
                    println("first test\n"+chatrooms)
                    val list = mutableListOf<ChatRoom>()

                    for(i in chatrooms){
                        list.add(ChatRoom(i.value.toString()))

                    }

                    recyclerChatRoom_list_Adapter = ChatRooms_list_Adapter(email,list)
                    chatroom_list_ReycleView.adapter = recyclerChatRoom_list_Adapter
                    chatroom_list_ReycleView.layoutManager = LinearLayoutManager(this@ChatRoomsList, LinearLayoutManager.VERTICAL ,false)

                }
            }

            override fun onFailure(call: Call<ArrayList<data>>, t: Throwable) {
                println("khlet commnttr")
            }


        })

    }
}