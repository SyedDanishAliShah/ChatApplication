package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context : Context, val userList : ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)//used to connect the views to our code so that we can upgrade or change the ui programatically
        return UserViewHolder(view)

    }

    override fun getItemCount(): Int {
        return userList.size

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser = userList[position]//getting the current user from the userlist
        holder.textName.text = currentUser.name//setting the textview according to the name of the current user
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)//as we r not in an activity and we r in a class so we will pass the context as we have defined above.
            intent.putExtra("name", currentUser.name)//passing name of the user to the chat activity using intent
            intent.putExtra("uid",currentUser.uid)//passing uid of the current user to the chat activity using intent
            context.startActivity(intent)

        }//when user clicks on the name of any of the user

    }//this function will bind the things that we r getting to the text view that we have created in the user_layout.xml
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txt_name)

    }//class made to make view holders for the list of items. Inside this class we have to initialize all the views that we have created in user_layout.xml file
}//class made to provide data to the recycler view, creating view holders for each item and recycling of views. Also adding user view holder to the adapter