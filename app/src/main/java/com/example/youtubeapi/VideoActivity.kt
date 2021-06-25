package com.example.youtubeapi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.youtubeapi.adapters.CommentAdapter
import com.example.youtubeapi.adapters.VideoAdapter
import com.example.youtubeapi.databinding.ActivityVideoBinding
import com.example.youtubeapi.models.CommentModel
import com.example.youtubeapi.models.Item
import com.example.youtubeapi.network.ApiClient
import com.example.youtubeapi.room.AppDataBase
import com.example.youtubeapi.utils.Status
import com.example.youtubeapi.viewModels.ViewModelFactory
import com.example.youtubeapi.viewModels.YoutubeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
class VideoActivity : AppCompatActivity(),PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: ActivityVideoBinding
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var commentList:ArrayList<CommentModel>
    private lateinit var appDataBase: AppDataBase
    private lateinit var obj:CommentModel
    private var posEdit=0
    private var videoName:String?=null
    private var videoDescription:String?=null
    private var videoId:String?=null
    private var like=0
    private var dislike=0
    private var likeVideo=0
    private var dislikeVideo=0
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var youtubeViewModel: YoutubeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appDataBase= AppDataBase.getInstance(this)
        videoName = intent.getStringExtra("videoTittle")
        videoDescription = intent.getStringExtra("videoDescription")
        videoId = intent.getStringExtra("videoDeId")
        lifecycle.addObserver(binding.youtubePlayerView)
        loadVideo(videoId!!)
        binding.name.text=videoName
        binding.descrp.text=videoName
        binding.decription.text=videoDescription
        videoAdapter= VideoAdapter(object: VideoAdapter.OnItemClickListener{
            override fun onItemClick(item: Item) {
                lifecycle.addObserver(binding.youtubePlayerView)
                loadVideo(item.id.videoId)
            }
            override fun onItemClickMore(item: Item, view: View) {
                val popup = PopupMenu(this@VideoActivity,view)
                popup.setOnMenuItemClickListener(this@VideoActivity)
                popup.inflate(R.menu.menu_popup)
                popup.show()
            }
        })
        youtubeViewModel= ViewModelProviders.of(this, ViewModelFactory(ApiClient.apiService))[YoutubeViewModel::class.java]
        youtubeViewModel.getYoutubeLiveData().observe(this,{
            when(it.status){
                Status.ERROR->{
                    binding.progress.visibility= View.INVISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING->{
                    binding.progress.visibility= View.VISIBLE
                }
                Status.SUCCESS->{
                    binding.progress.visibility= View.INVISIBLE
                    videoAdapter.submitList(it.data?.items)
                }
            }
        })
        binding.recView.adapter=videoAdapter
        binding.cancel.setOnClickListener {
            binding.linear5.visibility= View.GONE
            binding.nestedComment.visibility= View.GONE
            binding.nestedMain.visibility= View.VISIBLE
            binding.commentPanel.visibility=View.VISIBLE
            if(commentList.size==0){
                binding.commentPanel.visibility=View.GONE
            }
        }
        binding.comment.setOnClickListener {
            binding.linear5.visibility= View.VISIBLE
            binding.nestedComment.visibility= View.VISIBLE
            binding.nestedMain.visibility= View.GONE
            binding.number1.text="${commentList.size}"
        }
        binding.openDescription.setOnClickListener {
            binding.nestedMain.visibility= View.GONE
            binding.linear8.visibility=View.VISIBLE
            binding.nestedDescription.visibility=View.VISIBLE
        }
        binding.cancelDescription.setOnClickListener {
            binding.nestedMain.visibility= View.VISIBLE
            binding.linear8.visibility=View.GONE
            binding.nestedDescription.visibility=View.GONE
        }
        //adapter comments
        commentList= ArrayList(appDataBase.commentDao().getAllComments(videoName!!))
        if(commentList.size!=0){
            binding.commentPanel.visibility=View.VISIBLE
            binding.number.text="${commentList.size}"
            binding.lastComment.text="${commentList[commentList.size-1].text}"
        }else{
            binding.number.text="0"
        }
        commentAdapter= CommentAdapter(commentList,object:CommentAdapter.OnItemClickListener{
            override fun onRemoveComment(commentModel: CommentModel,position:Int) {
                appDataBase.commentDao().removeComment(commentModel)
                commentList.remove(commentModel)
                binding.number.text="${commentList.size}"
                binding.number1.text="${commentList.size}"
                if(commentList.size>1){
                    binding.lastComment.text="${commentList[commentList.size-1].text}"
                }
                commentAdapter.notifyItemRemoved(position)
                commentAdapter.notifyItemRangeChanged(position,commentList.size)
            }
            override fun onClickLike(textView: TextView) {
                ++like
                textView.text=like.toString()
            }
            override fun onClickDislike(textView: TextView) {
                ++dislike
                textView.text=dislike.toString()
            }
            override fun onClickEdit(commentModel: CommentModel,position:Int) {
                posEdit=position
                obj=commentModel
                binding.editText.setText(commentModel.text)
                binding.send.visibility=View.GONE
                binding.update.visibility=View.VISIBLE
            }
        })
        binding.recView2.adapter=commentAdapter
        //add comments
        binding.send.setOnClickListener {
            val text=binding.editText.text.toString()
            val commentModel=CommentModel(videoName = videoName!!,text = text)
            commentList.add(commentModel)
            appDataBase.commentDao().addComment(commentModel)
            commentAdapter.notifyItemInserted(commentList.size)
            Toast.makeText(this, "Comment added", Toast.LENGTH_SHORT).show()
            binding.editText.setText("")
            binding.number.text="${commentList.size}"
            binding.number1.text="${commentList.size}"
            binding.lastComment.text="${commentList[commentList.size-1].text}"
        }
        binding.update.setOnClickListener {
            val newText=binding.editText.text.toString()
            val commentModel=CommentModel(obj.id,newText,obj.videoName)
            appDataBase.commentDao().editComment(commentModel)
            commentAdapter.notifyItemChanged(posEdit)
            binding.send.visibility=View.VISIBLE
            binding.update.visibility=View.GONE
            binding.editText.setText("")
        }
        //-------------------------------------------------
        binding.addLike.setOnClickListener {
            binding.like.text=likeVideo.toString()
            likeVideo++
        }
        binding.addDislike.setOnClickListener {
            binding.dislike.text=dislikeVideo.toString()
            dislikeVideo++
        }
        binding.share.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "https://www.youtube.com/watch?v=${videoId}"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.save_later->{

            }else->{
            return false
         }
        }
        return false
    }
    private fun loadVideo(videoId:String){
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0F)
            }
        })
    }
}