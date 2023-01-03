package kr.co.nottodo.presentation.toplevel.recommendation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.NonDisposableHandle.parent
import kr.co.nottodo.R
import kr.co.nottodo.databinding.FragmentRecommendationBinding

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
        recommendationAdapter = RecommendationAdapter()

        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }
}