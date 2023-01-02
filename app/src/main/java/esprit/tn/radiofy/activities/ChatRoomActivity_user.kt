package esprit.tn.radiofy.activities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import esprit.tn.radiofy.R
import com.google.gson.Gson
import com.junga.socketio_android.ChatRoomAdapter
import esprit.tn.radiofy.adapters.CurrentUsersAdapter
import esprit.tn.radiofy.models.*
import esprit.tn.radiofy.utils.ApiInterface
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import kotlinx.android.synthetic.main.activity_chat_room.*


class ChatRoomActivity_user : AppCompatActivity(), View.OnClickListener {


    val TAG = ChatRoomActivity_user::class.java.simpleName


    lateinit var mSocket: Socket;
    lateinit var userName: String;
    lateinit var roomName: String;
//    lateinit var leave: ImageView
    lateinit var send: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText

    val gson: Gson = Gson()

    //For setting the recyclerView.
    val chatList: ArrayList<Message> = arrayListOf();
    val apiInterface = ApiInterface.create()
    lateinit var email:String
    lateinit var radioName:String
    lateinit var radioEmail:String
    lateinit var chatRoomAdapter: ChatRoomAdapter
    lateinit var recyclerCurrentUser: RecyclerView
    lateinit var recyclerCurrentUsersAdapter: CurrentUsersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        email = intent.getStringExtra("email").toString()
        println("email test ======"+email)
        radioName = intent.getStringExtra("radioName").toString()
        radioEmail = intent.getStringExtra("radioEmail").toString()
        println("email test ======"+radioEmail)
        editText = findViewById(R.id.editText)
        recyclerView = findViewById(R.id.recyclerView)
        send = findViewById(R.id.send)
//        leave = findViewById(R.id.leave)
        send.setOnClickListener(this)
//        leave.setOnClickListener(this)
        val map_current_users: HashMap<String, String> = HashMap()
//        recyclerCurrentUser = findViewById(R.id.recyclerCurrentUser)

//        Get the nickname and roomname from entrance activity.
//        try {
//            userName = intent.getStringExtra("userName")!!
//            roomName = intent.getStringExtra("roomName")!!
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//


        println("moemen testing the email in the chat "+email);
        userName = email.toString()
        roomName = radioName.toString()
//        map_current_users["email"] = "ShemsFM@ShemsFM.tn"
        println("map_current_users")
//


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
        mSocket.on("userLeftChatRoom", onUserLeft)        }

//    }

    var onUserLeft = Emitter.Listener {

        val leftUserName = it[0] as String
        val chat: Message = Message(leftUserName, "", "", MessageType.USER_LEAVE.index)
        addItemToRecyclerView(chat)
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
        val map_current_user: HashMap<String, String> = HashMap()
        map_current_user["email_user"] = email
        map_current_user["radioName"] = radioName
        map_current_user["email_radio"] = radioEmail
        apiInterface.addConnectedUser(map_current_user).enqueue(object:
            Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                println("khedmet add connected user")

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("mekhdmtch connected ")
            }


        })


    }

    var onNewUser = Emitter.Listener {
        val name = it[0] as String //This pass the userName!
        val chat = Message(name, "", roomName, MessageType.USER_JOIN.index)
        addItemToRecyclerView(chat)
        Log.d(TAG, "on New User triggered.")
    }


    private fun sendMessage() {
        val map_user_check: HashMap<String, String> = HashMap()
        map_user_check["email"] = email
        map_user_check["radioName"] = radioName
        map_user_check["radio"] = radioEmail
        apiInterface.CheckBannedUser(map_user_check).enqueue(object:
            Callback<data> {
            override fun onResponse(call: Call<data>, response: Response<data>) {
                var res = response.body()
                if (res != null) {
                    if (res.key.toString()=="false"){
                        val content = editText.text.toString()
                        val sendData = SendMessage(userName, content, roomName)
                        val jsonData = gson.toJson(sendData)
                        mSocket.emit("newMessage", jsonData)

                        val message = Message(userName, content, roomName, MessageType.CHAT_MINE.index)
                        addItemToRecyclerView(message)
                    }else {
                        Toast.makeText(this@ChatRoomActivity_user, "You're banned from this room!, you no longer can send message here", Toast.LENGTH_SHORT).show()

                    }
                }
            }

            override fun onFailure(call: Call<data>, t: Throwable) {
                Toast.makeText(this@ChatRoomActivity_user, "An error occured, please try later!", Toast.LENGTH_SHORT).show()
            }


        })


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
        val map_current_user: HashMap<String, String> = HashMap()
        map_current_user["email_user"] = email
        map_current_user["radioName"] = radioName
        map_current_user["email_radio"] = radioEmail
        apiInterface.removeConnectedUser(map_current_user).enqueue(object:
            Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                println("khedmet add connected user")

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("mekhdmtch connected ")
            }


        })

        super.onDestroy()
        val data = initialData(userName, roomName)
        val jsonData = gson.toJson(data)
        mSocket.emit("unsubscribe", jsonData)
        mSocket.disconnect()
    }

}