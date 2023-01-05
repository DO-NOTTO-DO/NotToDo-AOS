package kr.co.nottodo.presentation.schedule.search.adapter

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemSearchRvBinding


class SearchRecyclerViewAdapter(context: Context, private val sampleList: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var binding: ItemSearchRvBinding
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemSearchRvBinding.inflate(inflater, parent, false)
        return SearchRecyclerViewHolder(binding)
    }

    class SearchRecyclerViewHolder(private val binding: ItemSearchRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(history: String) {
            binding.tvSearchRv.text = history
        }

        fun setBottomLine() {
            binding.root.setBackgroundResource(R.drawable.line_bottom_gray6_0point5)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchRecyclerViewHolder).onBind(sampleList[position])
        if (position != sampleList.size - 1) {
            holder.setBottomLine()
        }
    }

    override fun getItemCount(): Int = sampleList.size

    class SearchRecyclerViewItemDecoration() : ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) != state.itemCount) {
            }
        }
    }

}