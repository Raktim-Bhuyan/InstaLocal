package rb.example.instalocal

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import rb.example.instalocal.databinding.ActivityPostsBinding
import rb.example.instalocal.databinding.ItemPostsBinding
import rb.example.instalocal.models.Post
import rb.example.instalocal.models.User

private const val EXTRA_USERNAME = "EXTRA_USERNAME"
open class PostsActivity : AppCompatActivity() {

    private var signedInUser: User?=null
    private lateinit var firestoreDb:FirebaseFirestore
    private lateinit var posts:MutableList<Post>
    private lateinit var adapter: PostsAdapter
    private lateinit var binding: ActivityPostsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPostsBinding.inflate(layoutInflater)
        //instantiates a layout file into its corresponding view
        setContentView(binding.root)

        //Create the layout file which represents one post-Done
        //Create data source -Done
        posts = mutableListOf()
        //Create the adapter -Done
        adapter = PostsAdapter(this,posts)
        //Bind the adapter and layout manager to the RV
        binding.rvPosts.adapter = adapter
        binding.rvPosts.layoutManager = LinearLayoutManager(this)



        firestoreDb = FirebaseFirestore.getInstance()

        var postsReference = firestoreDb.collection("posts")
            .limit(20)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if(username!=null){
            postsReference=postsReference.whereEqualTo("user.username",username)
        }
        postsReference.addSnapshotListener{snapshot,exception->
            if(exception!=null||snapshot==null){
                Log.e(TAG,"Exception when queryong posts",exception)
                return@addSnapshotListener
            }
            val postlist = snapshot.toObjects(Post::class.java)
            //Whenever we get new data , we have to update
            posts.clear() //Clearing the old data
            posts.addAll(postlist)
            adapter.notifyDataSetChanged()



            for(post  in postlist){
                Log.i(TAG,"Post ${post}")
            }
        }
        binding.fabCreate.setOnClickListener{
            val intent = Intent(this,CreateActivity::class.java)
            startActivity(intent)
        }
        //Todo:make a query to firestore to retreive data
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_profile){
            val intent = Intent(this,ProfileActivity::class.java)

            intent.putExtra(EXTRA_USERNAME, signedInUser?.username)
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }
}