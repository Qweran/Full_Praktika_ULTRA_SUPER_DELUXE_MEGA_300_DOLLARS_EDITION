package com.example.myapplication

import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import kotlin.reflect.KClass
/*


class PostRepositoryMemoryImpl:PostRepository {


    private var posts = listOf(
        Post(
            id = 1,
            author = "Бтпит. Университет интернет профессий будущего",
            content = "Здарова\n\n\n\nработяги",
            published = "22 октября в 18:34",
            likeByMe = false,
            likeCount = 1999,
            repByMe = false,
            repCount = 15,
            link = "https://www.youtube.com/watch?v=T4o3quTy0UM"
        ),
        Post(
            id = 2,
            author = "Я хз вообще",
            content = "Ну трололоолололорлдолдололо",
            published = "21 мая в 21:32",
            likeByMe = false,
            likeCount = 22,
            repByMe = false,
            repCount = 33,
            link = ""
        ),
        Post(
            id = 3,
            author = "Я хз",
            content = "Ну",
            published = "21 мая в ц21:32",
            likeByMe = false,
            likeCount = 22,
            repByMe = false,
            repCount = 33,
            link = "https://www.youtube.com/watch?v=6xIeLJGcpfU"

        ),
    )
    fun nextId(post:List<Post>):Int{
        var id = 1
        posts.forEach{it1->
        posts.forEach{
            if(it.id.toInt() == id) id = (it.id+1).toInt()
        }
        }
        return id
    }
    override fun save(post: Post) {
        if(post.id == 0) {
            posts = listOf(
                post.copy(
                    id = nextId(posts),
                    author = "me",
                    likeByMe = false,
                    published = "now"
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

    override fun add(post: Post) {
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
            if(it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun share(id: Int){
        posts= posts.map {
            if(it.id != id) it else
                it.copy(repCount = it.repCount+1)
        }
        data.value = posts

    }

    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data
    override fun like(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(likeByMe = !it.likeByMe)
        }
        data.value = posts

        // if(!post.likeByMe){
        //     post.likeCount++


        // }
        // else{
        //    post.likeCount--
//
        //  }
        //   post = post.copy(likeByMe = !post.likeByMe)
        // data.value = post
    }


    override fun removeById(id: Int) {
        posts= posts.filter { it.id != id }
        data.value = posts
    }




    //  override fun rep(){

    //  post.repCount++


    //data.value  =post
    // }
}


*/