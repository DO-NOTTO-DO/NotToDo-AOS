//package kr.co.nottodo.presentation.home
//
//class fdfda() {
//    emojiList = listOf<ImageView>(
//    friendsEmojiBalloon.getContentView().findViewById(R.id.iv_react_heart),
//    friendsEmojiBalloon.getContentView().findViewById(R.id.iv_react_smile),
//    friendsEmojiBalloon.getContentView().findViewById(R.id.iv_react_fun),
//    friendsEmojiBalloon.getContentView().findViewById(R.id.iv_react_flex),
//    friendsEmojiBalloon.getContentView().findViewById(R.id.iv_react_what),
//    friendsEmojiBalloon.getContentView().findViewById(R.id.iv_react_sad)
//    )
//}
//
//private fun addEmoji(list_pos: Int) {
//    emoji_position = -1
//    for (i in emojiList.indices) {
//        emojiList[i].setOnClickListener {
//            emoji_position = i
//
//            friendsConsumeAdapter.getEmojiPosition(emoji_position + 1, list_position)
//            friendsEmojiBalloon.dismiss()
//            initSetEmoji(emoji_position + 1)
//        }
//    }
//}
//
//private fun initSetEmoji(emojiNum: Int) {
//    lifecycleScope.launch {
//        runCatching {
//            service.setFriendsReaction(
//                RequestFriendAddReaction(
//                    emotion = emojiNum,
//                    targetId = clickedPosition
//                )
//            )
//        }.onSuccess {
//            Timber.d("$it")
//        }.onFailure {
//            Timber.d("$it")
//        }
//    }
//}
//
//
//private fun consumeClick() {
//    friendsConsumeAdapter.setConsumeListClickListener(object :
//        FriendsConsumeAdapter.FriendsConsumeListInterface {
//        override fun onClick(data: View, position: Int, addEmoji: Boolean, id: Int) {
//            if (!addEmoji) {
//                if (!friendsBottomSheetFragment.isAdded) {
//                    val bundle = Bundle().apply {
//                        putString("recordId", id.toString())
//                    }
//                    friendsBottomSheetFragment.arguments = bundle
//                    friendsBottomSheetFragment.show(
//                        childFragmentManager, friendsBottomSheetFragment.tag
//                    )
//                }
//            } else {
//                list_position = position
//                clickedPosition = id
//                friendsEmojiBalloon.showAlignBottom(data)
//                addEmoji(list_position)
//            }
//        }
//    })
//}
//}