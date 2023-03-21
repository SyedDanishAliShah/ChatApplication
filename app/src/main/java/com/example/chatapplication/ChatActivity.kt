package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sentButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef : DatabaseReference

    var receiverRoom: String? = null//using room property with sender and receiver we create a unique and private room for the sender and receiver so the message is not reflected to all the users
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")//receiving the name using intent from another activity.
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid//this way we have the uid of the currently logged in user.
         mDbRef = FirebaseDatabase.getInstance().getReference()
        senderRoom = receiverUid + senderUid//means to send a message the sender will need the receiver's uid and the currently logged in user's id
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name//setting name on the toolbar


        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sentButton = findViewById(R.id.sentButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        //logic for adding data to the recycler view
        mDbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()//to clear the previous messages
                for (postSnapshot in snapshot.children){

                    val message = postSnapshot.getValue(Message::class.java)//we will create the message using this postsnapshot because as this snapshot contains messages
                    messageList.add(message!!)//adding message to the messagelist

                }//as we have to go to all the children of this snapshot which is of all the messages present in the database
              messageAdapter.notifyDataSetChanged()//informing the message adapter about the changes in data
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        sentButton.setOnClickListener {

            val message = messageBox.text.toString()//we will get what is written in the message box
            val messageObject = Message(message,senderUid)//passing both the parameters from the Message data class

            mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mDbRef.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)

            }
            messageBox.setText("")//will clear the message box as we click the send button to the send the message
            //making an another node in the database with the name chats having a child node messages. Like this we have updated the sender room.
        }//adding the message to the database
    }
}