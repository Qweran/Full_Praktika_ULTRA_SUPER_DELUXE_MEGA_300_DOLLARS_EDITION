package com.example.myapplication

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
interface PostRepository{

    fun like(id:Int)
    // fun rep()
    fun getAll(): LiveData<List<Post>>
    fun getByUser(user:String): LiveData<List<Post>>
    fun removeById(id: Int)
    fun save(post: Post)
    fun share(id: Int): Any
    fun addPost(post: Post, string: String,link:String)
}

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likeByMe = false,
    published = "",
    likeCount = 0,
    repByMe = false,
    repCount = 0,
    link = ""
)
fun getEmptyPost():Post{
    return Post(
        id = 0,
        content = "",
        author = "",
        likeByMe = false,
        published = "",
        likeCount = 0,
        repByMe = false,
        repCount = 0,
        link = ""
    )
}
class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRePositorySharedPrefsImpl(application)
    val data = repository.getAll()
    fun getByUser(user: String) = repository.getByUser(user)

    fun share(id: Int)= repository.share(id)

    fun removeById(id:Int) = repository.removeById(id)
    fun like(id:Int) = repository.like(id )
    val edited = MutableLiveData(empty)
    fun addPost(string: String,link:String){
        edited.value?.let {
            repository.addPost(it,string,link)
        }
        edited.value = getEmptyPost()
    }

    fun save(){
        edited.value?.let {
            repository.save(it)
            return

        }
        edited.value = empty
    }
    fun edit(post: Post){
        edited.value = post
    }


    fun changeContent(content:String,link:String){
        edited.value?.let {
            val text = content.trim()

            edited.value=edited.value?.copy(content = text, link = link)
        }
    }



    //  fun rep()= repository.rep()
}
class PostRePositorySharedPrefsImpl(
    context: Context,
):PostRepository{
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var nextId = 1
    private var posts = emptyList<Post>()
    private var postsOnUser = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private val dataOnUser = MutableLiveData(posts)
    init{
        prefs.getString(key, null)?.let {
            posts=gson.fromJson(it, type)
            data.value= posts
        }
    }

    override fun getAll(): LiveData<List<Post>> =data
    override fun getByUser(user: String): LiveData<List<Post>> {
        dataOnUser.value = posts.filter { it.author == user }
        return dataOnUser
    }

    override fun addPost(post: Post,string: String, link:String) { // Функция добавоения (должна быть объявлена в Post
        posts = listOf(
            post.copy(
                id =nextId(posts),
                author = "Roman",
                content = string,
                link = link,
                likeByMe = false,
                published = "da"
            )
        ) + posts
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if(post.id == 0) {
            posts = listOf(
                post.copy(
                    id = nextId(posts),
                    author = "Roman",
                    likeByMe = false,
                    published = "da"
                )
            ) + posts
            data.value = posts
            return
        }
        posts = posts.map {
            if(it.id != post.id) it else it.copy(content = post.content, link = post.link)
        }
        data.value = posts
    }
    fun nextId(post:List<Post>):Int{
        var id = 1
        posts.forEach{it1->
            posts.forEach{
                if(it.id.toInt() == id) id = (it.id+1).toInt()
            }
        }
        return id
    }
    override fun share(id: Int) {}

    override fun like(id: Int) {
        posts = posts.map {
            if(it.id!=id) it else it.copy(
                likeByMe = !it.likeByMe,
                likeCount = if(it.likeByMe) it.likeCount-1 else it.likeCount+1
            )
        }
        data.value=posts
        sync()
    }

    override fun removeById(id: Int) {
        posts=posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    private fun sync(){
        with(prefs.edit()){
            putString(key,gson.toJson(posts))
            apply()
        }
    }
}

/*
class PostRepositoryFileImpl(    private val context: Context):PostRepository{
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val fileName = "posts.json"
    private var nextId = 0
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private fun sync(){
        context.openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
    init {
        val file = context.filesDir.resolve(fileName)
        if(file.exists()){
            context.openFileInput(fileName).bufferedReader().use{
                posts=gson.fromJson(it,type)
                data.value = posts
            }
        }
        else{

            sync()
        }

    }

    override fun like(id: Int) {

    }

    override fun getAll(): LiveData<List<Post>> =data

    override fun removeById(id: Int) {

    }

    override fun save(post: Post) {
    }

    override fun add(post: Post) {

    }

    override fun share(id: Int) {

    }
}

*/