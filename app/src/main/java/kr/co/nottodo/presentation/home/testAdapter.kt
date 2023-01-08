//package kr.co.nottodo.presentation.home
//
//import android.content.Context
//
//class testAdapter(val contextT: Context) :
//    RecyclerView.Adapter<FriendsConsumeAdapter.FriendsConsumeViewHolder>() {
//    var context = contextT
//    var clicked_position = 0
//    var clicked_recordId = 0
//    private lateinit var listener: FriendsConsumeListInterface
//    var friendConsumeList = mutableListOf<ResponseFriendsAll>()
//    var getEmoji = -1
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsConsumeViewHolder {
//        val binding = ItemFriendConsumeListBinding.inflate(
//            LayoutInflater.from(parent.context), parent, false
//        )
//        return FriendsConsumeViewHolder(binding, context)
//    }
//
//    override fun onBindViewHolder(holder: FriendsConsumeViewHolder, position: Int) {
//        holder.itemView.setOnSingleClickListener {
//            clicked_recordId = friendConsumeList[position].id
//            listener.onClick(it, position, false, clicked_recordId)
//
//        }
//        holder.bind(friendConsumeList[position])
//        holder.addEmojiButton.setOnSingleClickListener {
//            clicked_recordId = friendConsumeList[position].id
//            listener.onClick(it, position, true, clicked_recordId)
//        }
//        if (clicked_position == position && getEmoji != -1) {
//            holder.setEmoji(getEmoji)
//        }
//    }
//
//    fun getEmojiPosition(position: Int, list_position: Int) {
//        if (position != -1)
//            clicked_position = list_position
//        getEmoji = position
//        //      notifyDataSetChanged()
//        notifyItemChanged(list_position)
//    }
//
//
//    class FriendsConsumeViewHolder(
//        private val binding: ItemFriendConsumeListBinding,
//        val context: Context
//    ) :
//        RecyclerView.ViewHolder(binding.root) {
//        val addEmojiButton = binding.ivAddemotion
//
//        fun bind(friendsConsumeData: ResponseFriendsAll) {
//            binding.apply {
//                tvFriendname.text = friendsConsumeData.nickname
//                tvFrienddate.text = friendsConsumeData.timestamp
//                tvFrienddes.text = friendsConsumeData.content
//                tvFriendprice.text = friendsConsumeData.amount.toString()
//                tvFriendtag.text = friendsConsumeData.goalMessage
//                tvRecordid.text = friendsConsumeData.id.toString()
//                ivFriendimage.load(friendsConsumeData.profileImage)
//                if (friendsConsumeData.plusCount != 0)
//                    tvReactPlusCount.text = friendsConsumeData.plusCount.toString() + "+"
//            }
//
//            when (friendsConsumeData.startEmotion) {
//                1 -> {
//                    binding.ivFriendFirstemotion.setImageResource(R.drawable.ic_emoji_mint_heart)
//                }
//                2 -> {
//                    binding.ivFriendFirstemotion.setImageResource(R.drawable.ic_emoji_mint_what)
//                }
//                3 -> {
//                    binding.ivFriendFirstemotion.setImageResource(R.drawable.ic_emoji_mint_sad)
//                }
//
//            }
//            when (friendsConsumeData.endEmotion) {
//                1 -> {
//                    binding.ivFriendSecondemotion.setImageResource(R.drawable.ic_emoji_happy_pink_34)
//                }
//                2 -> {
//                    binding.ivFriendSecondemotion.setImageResource(R.drawable.ic_emoji_what_pink_34)
//                }
//                3 -> {
//                    binding.ivFriendSecondemotion.setImageResource(R.drawable.ic_emoji_sad_pink_34)
//                }
//                else -> {
//                    binding.ivFriendSecondemotion.setImageResource(R.drawable.ic_question_backgorund_34)
//                }
//            }
//
//            for (i in 0 until friendsConsumeData.reactions.size) {
//                // 무조건 3개가 날라옴..  뒤에 애들이 0인 경우
//                if (i == 0) {
//                    if (friendsConsumeData.reactions[i] == 1) {
//                        binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_happy_mint_28)
//                    } else if (friendsConsumeData.reactions[i] == 2) {
//                        binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_smile_mint_28)
//                    } else if (friendsConsumeData.reactions[i] == 3) {
//                        binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_funny_mint_28)
//                    } else if (friendsConsumeData.reactions[i] == 4) {
//                        binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_flex_mint_28)
//                    } else if (friendsConsumeData.reactions[i] == 5) {
//                        binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_what_mint_28)
//                    } else if (friendsConsumeData.reactions[i] == 6) {
//                        binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_sad_mint_28)
//                    } else { //0번이면
//                        binding.ivAddemotion.setImageResource(R.drawable.ic_btn_emoji_add)
//                    }
//                } else if (i == 1) {
//                    // 동일내용
//                } else {
//                    //동일내용
//                } else {
//                    //동일내용
//                }
//
//            }
//        }
//
//        binding.ivEmotion2.bringToFront()
//        binding.ivEmotion1.bringToFront()
//        binding.ivAddemotion.bringToFront()
//        binding.tvReactPlusCount.bringToFront()
//
//    }
//
//    fun setEmoji(position: Int) {
//        val pos = position
//        when (pos) {
//
//            1 -> {
//                binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_happy_mint_28)
//            }
//            2 -> {
//                binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_smile_mint_28)
//            }
//            3 -> {
//                binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_funny_mint_28)
//            }
//            4 -> {
//                binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_flex_mint_28)
//            }
//            5 -> {
//                binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_what_mint_28)
//            }
//            6 -> {
//                binding.ivAddemotion.setImageResource(R.drawable.ic_emoji_sad_mint_28)
//            }
//        }
//
//    }
//
//    fun changeToDp(): Int {
//        val value = 22
//        var displayMetrics = context.resources.displayMetrics
//        var dp = Math.round(value * displayMetrics.density)
//        return dp
//    }
//}
//
//interface FriendsConsumeListInterface {
//    fun onClick(data: View, position: Int, addEmoji: Boolean, id: Int)
//}
//
//fun setConsumeListClickListener(listener: FriendsConsumeListInterface) {
//    this.listener = listener
//}
//
//override fun getItemCount(): Int = friendConsumeList.size
//}
//}