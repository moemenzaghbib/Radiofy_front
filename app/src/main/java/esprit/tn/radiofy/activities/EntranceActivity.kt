package esprit.tn.radiofy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import esprit.tn.radiofy.R
//import kotlinx.android.synthetic.main.activity_entrance.*

class EntranceActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)

//        button.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
            when(p0?.id){
                R.id.button -> enterChatroom()
            }
    }

    private fun enterChatroom(){
       // val userName = userName.text.toString()
       // val roomName = roomname.text.toString()

//        if(!roomName.isNullOrBlank()&&!userName.isNullOrBlank()) {
//            startActivity(Intent(this, ChatRoomActivity ::class.java).apply {
//                       putExtra("userName" , userName)
//                        putExtra("roomName" , roomName)
//            })
//
//        }else{
//            Toast.makeText(this,"Nickname and Roomname should be filled!",Toast.LENGTH_SHORT)
//        }
    }
}
