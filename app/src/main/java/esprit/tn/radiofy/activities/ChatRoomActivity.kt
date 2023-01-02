package esprit.tn.radiofy.activities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.junga.socketio_android.ChatRoomAdapter
import esprit.tn.radiofy.R
import esprit.tn.radiofy.adapters.CurrentUsersAdapter
import esprit.tn.radiofy.models.*
import esprit.tn.radiofy.utils.ApiInterface
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import kotlinx.android.synthetic.main.activity_chat_room.*


class ChatRoomActivity : AppCompatActivity(), View.OnClickListener {


    val TAG = ChatRoomActivity::class.java.simpleName


    lateinit var mSocket: Socket;
    lateinit var userName: String;
    lateinit var roomName: String;
//    lateinit var leave: ImageView
    lateinit var send: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText
    lateinit var email:String
    lateinit var radioName:String
    lateinit var radioEmail:String
    val gson: Gson = Gson()

    //For setting the recyclerView.
    val chatList: ArrayList<Message> = arrayListOf();
    lateinit var chatRoomAdapter: ChatRoomAdapter
    lateinit var recyclerCurrentUser: RecyclerView
    lateinit var recyclerCurrentUsersAdapter: CurrentUsersAdapter
    val apiInterface = ApiInterface.create()
    val map_current_users: HashMap<String, String> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room_admin)
        email = intent.getStringExtra("email").toString()
        println("email test ======"+email)
        radioName = intent.getStringExtra("radioName").toString()
        radioEmail = intent.getStringExtra("radioEmail").toString()
        println("email test ======"+radioEmail)
        recyclerCurrentUser = findViewById(R.id.recyclerCurrentUser)
        recyclerCurrentUsersAdapter = CurrentUsersAdapter(radioEmail.toString(),mutableListOf())
        recyclerCurrentUser.adapter = recyclerCurrentUsersAdapter

        editText = findViewById(R.id.editText)
        recyclerView = findViewById(R.id.recyclerView)
        send = findViewById(R.id.send)
//        leave = findViewById(R.id.leave)
        send.setOnClickListener(this)
//        leave.setOnClickListener(this)



        apiInterface.GetAllConnectedUsers(map_current_users).enqueue(object:
            Callback<ArrayList<data>> {
            override fun onResponse(
                call: Call<ArrayList<data>>,
                response: Response<ArrayList<data>>
            ) {

                val users = response.body()
                val list1 = mutableListOf<current_user>()
                if(users!=null){



                    for(i in users){
                        list1.add(current_user(i.key.toString(),i.value.toString()))

                    }

                         }
                recyclerCurrentUsersAdapter = CurrentUsersAdapter(radioEmail,list1)
                recyclerCurrentUser.adapter = recyclerCurrentUsersAdapter
                recyclerCurrentUser.layoutManager = LinearLayoutManager(this@ChatRoomActivity, LinearLayoutManager.VERTICAL ,false)


            }

            override fun onFailure(call: Call<ArrayList<data>>, t: Throwable) {
                println("khlet commnttr")
            }


        })
//        Get the nickname and roomname from entrance activity.
//        try {
//            userName = intent.getStringExtra("userName")!!
//            roomName = intent.getStringExtra("roomName")!!
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        val email = intent.getStringExtra("email").toString()
        val radioName = intent.getStringExtra("radioName").toString()
        println("moemen testing the email in the chat "+email);
        userName = email.toString()
        roomName = radioName.toString()
        map_current_users["email"] = radioEmail
        println("map_current_users")



        //Set Chatroom adapter

        chatRoomAdapter = ChatRoomAdapter(this, chatList);
        recyclerView.adapter = chatRoomAdapter;

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        //Let's connect to our Chat room! :D
        try {
            mSocket = IO.socket("http://10.0.2.2:3000")
            Log.d("success", mSocket.id())

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }
//        val t = Thread()
//        Thread {
        Thread.sleep(1000)
        mSocket.connect()
        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("newUserToChatRoom", onNewUser)
        mSocket.on("updateChat", onUpdateChat)
        mSocket.on("userLeftChatRoom", onUserLeft)



    }

//    }

    var onUserLeft = Emitter.Listener {
        val leftUserName = it[0] as String
        val chat: Message = Message(leftUserName, "", "", MessageType.USER_LEAVE.index)
        addItemToRecyclerView(chat)
        runOnUiThread {

            recyclerCurrentUsersAdapter.updateData()
            apiInterface.GetAllConnectedUsers(map_current_users).enqueue(object:
                Callback<ArrayList<data>> {
                override fun onResponse(
                    call: Call<ArrayList<data>>,
                    response: Response<ArrayList<data>>
                ) {
                    val users = response.body()
                    val list1 = mutableListOf<current_user>()
                    if(users!=null){



                        for(i in users){
                            list1.add(current_user(i.key.toString(),i.value.toString()))
                            println("test te3 el users suppose yodhhrou lenna wala la hethi meghir update awel mara khatar")
                        }

                        recyclerCurrentUsersAdapter = CurrentUsersAdapter(radioEmail,list1)
                        recyclerCurrentUser.adapter = recyclerCurrentUsersAdapter
                        recyclerCurrentUser.layoutManager = LinearLayoutManager(this@ChatRoomActivity, LinearLayoutManager.VERTICAL ,false)
                    }


                }

                override fun onFailure(call: Call<ArrayList<data>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })



//            if(::recyclerCurrentUsersAdapter.isInitialized) {
//
//                apiInterface.GetAllConnectedUsers(map_current_users).enqueue(object:
//                    Callback<ArrayList<data>> {
//                    override fun onResponse(
//                        call: Call<ArrayList<data>>,
//                        response: Response<ArrayList<data>>
//                    ) {
//                        val users = response.body()
//                        val list1 = mutableListOf<current_user>()
//                        if(users!=null){
//
//
//
//                            for(i in users){
//                                list1.add(current_user(i.key.toString(),i.value.toString()))
//                                println("test te3 el users suppose yodhhrou lenna wala la hethi meghir update awel mara khatar")
//                            }
//
//
//                        }
//                        recyclerCurrentUsersAdapter = CurrentUsersAdapter(list1)
//                        recyclerCurrentUser.adapter = recyclerCurrentUsersAdapter
//                        recyclerCurrentUser.layoutManager = LinearLayoutManager(this@ChatRoomActivity, LinearLayoutManager.VERTICAL ,false)
//
//                    }
//
//                    override fun onFailure(call: Call<ArrayList<data>>, t: Throwable) {
//                        TODO("Not yet implemented")
//                    }
//                })
//            }else {
//
//            } // Stuff that updates the UI
        }

    }

    var onUpdateChat = Emitter.Listener {
        val chat: Message = gson.fromJson(it[0].toString(), Message::class.java)
        chat.viewType = MessageType.CHAT_PARTNER.index
        addItemToRecyclerView(chat)
    }

    var onConnect = Emitter.Listener {
//        val apiInterface = ApiInterface.create()
        val data = initialData(userName, roomName)
        val jsonData = gson.toJson(data)
        mSocket.emit("subscribe", jsonData)
//        Toast.makeText(applicationContext,"this is where we connect",Toast.LENGTH_SHORT).show()
//        val map_current_user: HashMap<String, String> = HashMap()
//        map_current_user["email_user"] = email
//        map_current_user["radioName"] = radioName
//        map_current_user["email_radio"] = radioEmail
//        apiInterface.addConnectedUser(map_current_user).enqueue(object:
//            Callback<User> {
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//                println("khedmet add connected user")
//
//
//                apiInterface.GetAllConnectedUsers(map_current_users).enqueue(object:
//                    Callback<ArrayList<data>> {
//                    override fun onResponse(
//                        call: Call<ArrayList<data>>,
//                        response: Response<ArrayList<data>>
//                    ) {
//                        val users = response.body()
//                        if(users!=null){
//
//                            val list1 = mutableListOf<current_user>()
//
//                            for(i in users){
//                                list1.add(current_user(i.key.toString(),i.value.toString()))
//
//                            }
//
//                            recyclerCurrentUsersAdapter = CurrentUsersAdapter(list1)
//                            recyclerCurrentUser.adapter = recyclerCurrentUsersAdapter
//                            recyclerCurrentUser.layoutManager = LinearLayoutManager(this@ChatRoomActivity, LinearLayoutManager.VERTICAL ,false)
//
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ArrayList<data>>, t: Throwable) {
//                        TODO("Not yet implemented")
//                    }
//                })


//            }
//
//            override fun onFailure(call: Call<User>, t: Throwable) {
//                println("mekhdmtch connected ")
//            }
//
//
//        })


    }
    var onNewUser = Emitter.Listener {
        val name = it[0] as String //This pass the userName!
        val chat = Message(name, "", roomName, MessageType.USER_JOIN.index)
        addItemToRecyclerView(chat)
        Log.d(TAG, "on New User triggered.")
        runOnUiThread {

            recyclerCurrentUsersAdapter.updateData()
            apiInterface.GetAllConnectedUsers(map_current_users).enqueue(object:
                Callback<ArrayList<data>> {
                override fun onResponse(
                    call: Call<ArrayList<data>>,
                    response: Response<ArrayList<data>>
                ) {
                    val users = response.body()
                    val list1 = mutableListOf<current_user>()
                    if(users!=null){



                        for(i in users){
                            list1.add(current_user(i.key.toString(),i.value.toString()))
                            println("test te3 el users suppose yodhhrou lenna wala la hethi meghir update awel mara khatar")
                        }

                        recyclerCurrentUsersAdapter = CurrentUsersAdapter(radioEmail,list1)
                        recyclerCurrentUser.adapter = recyclerCurrentUsersAdapter
                        recyclerCurrentUser.layoutManager = LinearLayoutManager(this@ChatRoomActivity, LinearLayoutManager.VERTICAL ,false)
                    }


                }

                override fun onFailure(call: Call<ArrayList<data>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })



//            if(::recyclerCurrentUsersAdapter.isInitialized) {
//
//                apiInterface.GetAllConnectedUsers(map_current_users).enqueue(object:
//                    Callback<ArrayList<data>> {
//                    override fun onResponse(
//                        call: Call<ArrayList<data>>,
//                        response: Response<ArrayList<data>>
//                    ) {
//                        val users = response.body()
//                        val list1 = mutableListOf<current_user>()
//                        if(users!=null){
//
//
//
//                            for(i in users){
//                                list1.add(current_user(i.key.toString(),i.value.toString()))
//                                println("test te3 el users suppose yodhhrou lenna wala la hethi meghir update awel mara khatar")
//                            }
//
//
//                        }
//                        recyclerCurrentUsersAdapter = CurrentUsersAdapter(list1)
//                        recyclerCurrentUser.adapter = recyclerCurrentUsersAdapter
//                        recyclerCurrentUser.layoutManager = LinearLayoutManager(this@ChatRoomActivity, LinearLayoutManager.VERTICAL ,false)
//
//                    }
//
//                    override fun onFailure(call: Call<ArrayList<data>>, t: Throwable) {
//                        TODO("Not yet implemented")
//                    }
//                })
//            }else {
//
//            } // Stuff that updates the UI
        }

    }

    private fun sendMessage() {
        val content = editText.text.toString()
        val sendData = SendMessage(userName, content, roomName)
        val jsonData = gson.toJson(sendData)
        mSocket.emit("newMessage", jsonData)

        val message = Message(userName, content, roomName, MessageType.CHAT_MINE.index)
        addItemToRecyclerView(message)
    }

    private fun addItemToRecyclerView(message: Message) {

        //Since this function is inside of the listener,
        // You need to do it on UIThread!
        runOnUiThread {
            chatList.add(message)
            chatRoomAdapter.notifyItemInserted(chatList.size)
            editText.setText("")
            recyclerView.scrollToPosition(chatList.size - 1) //move focus on last message
        }
    }


    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.send -> sendMessage()
            R.id.leave -> onDestroy()
        }
    }

    override fun onDestroy() {
//        val map_current_user: HashMap<String, String> = HashMap()
//        map_current_user["email_user"] = email
//        map_current_user["radioName"] = radioName
//        map_current_user["email_radio"] = radioEmail
//        apiInterface.removeConnectedUser(map_current_user).enqueue(object:
//            Callback<User> {
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//                println("khedmet add connected user")
//
//            }
//
//            override fun onFailure(call: Call<User>, t: Throwable) {
//                println("mekhdmtch connected ")
//            }
//
//
//        })

        super.onDestroy()
        val data = initialData(userName, roomName)
        val jsonData = gson.toJson(data)
        mSocket.emit("unsubscribe", jsonData)
        mSocket.disconnect()
    }


}