package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardPostBinding



class PostsAdapter(private var listener: PostsAdapter.Listener): ListAdapter<Post,PostViewHolder>(PostDiffCallback()){
       override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):PostViewHolder{
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post,listener)

    }

    interface Listener
    {
        fun OnLikeListener(post:Post)
        fun removeById(post:Post)
        fun onEdit(post: Post)
        fun hideEdit()
        fun showEdit()
        fun add(post: Post)
        fun onShare(post: Post)

    }
}




class PostViewHolder(
    private val binding: CardPostBinding,

): RecyclerView.ViewHolder(binding.root){
    fun bind(post: Post,listener: PostsAdapter.Listener){
        binding.apply {
            authorText.text = post.author
            dataPostaText.text = post.published
            textView2.text = post.content

            likeCountText.text= toStringNumb( post.likeCount)
            linktxt.text = post.link

            likeBtn.setImageResource(
                if(post.likeByMe) R.drawable.ss else R.drawable.i
            )

            likeBtn.setOnClickListener {
                listener.OnLikeListener(post)
            }
                repostBtn.setOnClickListener{
                    listener.onShare(post)
                }
            menuBTN.setOnClickListener {
                PopupMenu(it.context,it).apply {
                    inflate(R.menu.pop_up_menu)
                    setOnMenuItemClickListener { item->
                        when(item.itemId){
                            R.id.removeBTN->{
                                listener.removeById(post)

                                true
                            }
                            R.id.editBTN->{
                                listener.onEdit(post)
                                listener.showEdit()
                                true
                            }
                            R.id.addBtn->{
                                listener.add(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()





            }
        }
    }
}



fun toStringNumb(count: Int): String {
    return when (count) {
        in 0..999 -> count.toString()
        in 1000..<1_000_000 -> {
            ((count / 100).toFloat() / 10).toString() + "K"
        }

        in 1_000_000..<1_000_000_000 -> {
            ((count / 100_000).toFloat() / 10).toString() + "М"
        }

        else -> "Более МЛРД"

    }

}
class PostDiffCallback:DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}