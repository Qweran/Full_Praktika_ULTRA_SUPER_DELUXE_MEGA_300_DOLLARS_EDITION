package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.MainActivity2
import com.example.myapplication.Post
import com.example.myapplication.PostViewModel
import com.example.myapplication.PostsAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMain2Binding
import com.example.myapplication.databinding.FragmentListPostFragmetBinding
import com.example.myapplication.listPostFragment
import com.example.myapplication.mainActivity2
import com.example.myapplication.profileFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class ListPostFragmet : Fragment(),PostsAdapter.Listener {
    val viewModel: PostViewModel by viewModels()
    private lateinit var adapter:PostsAdapter

    lateinit var bind: FragmentListPostFragmetBinding

      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentListPostFragmetBinding.inflate(layoutInflater,container,false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listPostFragment = this



        bind.conteinerEdit.visibility = View.GONE
        bind.editlink.visibility = View.GONE
        var isAdd = false
        val adapter = PostsAdapter(this)
        bind.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
        bind.addBtn.setOnClickListener{
            bind.conteinerEdit.visibility = View.VISIBLE
            bind.editlink.visibility = View.VISIBLE
            isAdd = true

        }
        bind.cancelBtn.setOnClickListener {
            bind.content.text.clear()
            bind.editlink.text.clear()
            bind.conteinerEdit.visibility = View.GONE
            bind.editlink.visibility = View.GONE
            isAdd = false

        }
        bind.saveBtn.setOnClickListener {
            with(bind.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        context,
                        "У вас текста нет, лоооооооооол",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (isAdd){
                    viewModel.addPost(text.toString(),bind.editlink.text.toString() )
                    bind.content.text.clear()
                    bind.editlink.text.clear()
                    bind.conteinerEdit.visibility = View.GONE
                    bind.editlink.visibility = View.GONE
                    isAdd = false
                }
                else{
                    viewModel.changeContent(text.toString(), bind.editlink.text.toString())
                    viewModel.save()
                }


                setText("")
                clearFocus()
                MainActivity2.AndroidUtils.hideKeyboard(this)
            }


            viewModel.edited.observe(viewLifecycleOwner){post->
                if(post.id == 0){
                    return@observe
                }
                with(bind.content){
                    requestFocus()
                    setText(post.content)
                }


            }
            profileFragment.posts = viewModel.data.value!!.filter { it.author == "Вы" }

            mainActivity2.intent?.let {
                if(it.action != Intent.ACTION_SEND){
                    return@let
                }

                val text = it.getStringExtra(Intent.EXTRA_TEXT)
                if(text.isNullOrBlank()){
                    Snackbar.make(bind.root, "ошибка", BaseTransientBottomBar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok){
                            mainActivity2.finish()
                        }
                        .show()
                    return@let
                }

            }
            /*run{
                val preferences = getPreferences(Context.MODE_PRIVATE)
                preferences.edit().apply {
                    putString("key", "value")
                    commit()
                }
            }*/
            /* run {
                 getPreferences(Context.MODE_PRIVATE)
                     .getString("key", "no value")?.let {
                         Snackbar.make(bind.root, it, BaseTransientBottomBar.LENGTH_INDEFINITE)
                             .show()
                     }

             }*/
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
        bind.conteinerEdit.visibility = View.GONE
    }
    override fun showEdit() {
        bind.conteinerEdit.visibility = View.VISIBLE
        bind.editlink.visibility = View.VISIBLE
    }

    override fun add(post: Post) {
        viewModel.save()
    }
}