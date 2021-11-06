package rb.example.instalocal

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rb.example.instalocal.databinding.ActivityPostsBinding
import rb.example.instalocal.databinding.ItemPostsBinding
import rb.example.instalocal.models.Post
import kotlin.coroutines.coroutineContext


class PostsAdapter(val context: Context,val posts:List<Post>) :
RecyclerView.Adapter<PostsAdapter.ViewHolder>(){

     class ViewHolder(val binding: ItemPostsBinding):RecyclerView.ViewHolder(binding.root) {
         fun bind(post: Post) {
             binding.tvUsername.text = post.user?.username
             binding.tvDescription.text = post.description


             Glide.with(binding.root).load(post.image_url).into(binding.ivPost)
             binding.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.creation_time_ms)

         }


     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemPostsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount()= posts.size
}