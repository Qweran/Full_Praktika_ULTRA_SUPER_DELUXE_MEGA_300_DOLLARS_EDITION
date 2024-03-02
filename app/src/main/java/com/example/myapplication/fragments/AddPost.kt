package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.Post
import com.example.myapplication.PostViewModel
import com.example.myapplication.PostsAdapter
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentAddPostBinding
import com.example.myapplication.listPostFragment
import com.example.myapplication.profileFragment


class AddPost : Fragment(),PostsAdapter.Listener {
    private val viewModel: PostViewModel by viewModels()

    var posts = emptyList<Post>()
   private lateinit var bind:FragmentAddPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind= FragmentAddPostBinding.inflate(layoutInflater)
        return bind.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileFragment = this

       val adapter = PostsAdapter(this)
        bind.list.adapter = adapter
        listPostFragment.viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts.filter { it.author == "Roman" })
       }

    }

    override fun onShare(post: Post){
        viewModel.share(post.id)
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, post.content)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent,"Выберите приложение")
        startActivity(shareIntent)
    }



    override fun OnLikeListener(post: Post) {
        viewModel.like(post.id)
    }

    override fun removeById(post: Post) {
        viewModel.removeById(post.id)
    }

    override fun onEdit(post: Post) {
        viewModel.edit(post)
    }

    override fun hideEdit() {
    //    bind.conteinerEdit.visibility = View.GONE
    }
    override fun showEdit() {
     //   bind.conteinerEdit.visibility = View.VISIBLE
    //   bind.editlink.visibility = View.VISIBLE
    }

    override fun add(post: Post) {
        viewModel.save()
    }


}