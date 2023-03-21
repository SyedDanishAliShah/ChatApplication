package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context:Context, val messageList: ArrayList<Message>): RecyclerView.Adapter<ViewHolder>() {//as we have two viewholders here so we will not pass either of them. Instead we will pass the general viewholder.
    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 1){
            //inflate receive
            val view : View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)//used to connect the views to our code so that we can upgrade or change the ui programatically
            return ReceiveViewHolder(view)

        }else{
            //inflate sent
            val view : View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)//used to connect the views to our code so that we can upgrade or change the ui programatically
            return SentViewHolder(view)
        }
    //that means this is item receive
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentMessage = messageList[position]//message object. getting the current message from the message list by passing the position.

        if (holder.javaClass == SentViewHolder::class.java){
            //do the stuff for sent view holder
            val viewHolder = holder as SentViewHolder//type casting sent view holder
            holder.sentMessage.text = currentMessage.message//it is sentviewholder.We are extracting the text message from this current message object.
    }else{
        //do the stuff for receive view holder
        val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT//means you are logged in and sending the message.

        }else{
            return ITEM_RECEIVE//means you are not logged in and the other person is sending the message.

        }
    //if the uid of the current user is equal to the uid of the sender id of the current message so then we will inflate sentviewholder.

    }// to return views
    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_message)//initializing the text view present in the layout file.

    }//sent viewholder


    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)

    }//receive viewholder
}