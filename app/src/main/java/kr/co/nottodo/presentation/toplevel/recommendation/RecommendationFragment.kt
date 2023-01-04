package kr.co.nottodo.presentation.toplevel.recommendation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent
import kr.co.nottodo.R
import kr.co.nottodo.databinding.FragmentRecommendationBinding
import kr.co.nottodo.databinding.ItemRecommendBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.Recommendationdata

class RecommendViewHolder(val binding: ItemRecommendBinding) : RecyclerView.ViewHolder(binding.root)

class RecommendationFragment : Fragment() {

    private lateinit var binding: FragmentRecommendationBinding
    private lateinit var recommendationAdapter: RecommendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentRecommendationBinding>(
            inflater,R.layout.fragment_recommendation, container,false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 여기서 프라그먼트 작업을 해주시면 좋습니다
        val itemList: List<Recommendationdata> = listOf(Recommendationdata("ㅎㅇ", "ㅎㅇ"), Recommendationdata("ㅎㅇ", "ㅎㅇ") )
        recommendationAdapter = RecommendationAdapter(itemList)

        binding.rvRecommendation.adapter = recommendationAdapter
        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }
}