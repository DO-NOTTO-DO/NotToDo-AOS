package kr.co.nottodo.presentation.mypage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.nottodo.R
import kr.co.nottodo.databinding.FragmentMyPageBinding
import kr.co.nottodo.presentation.MainActivity
import kr.co.nottodo.util.extension.setStatusBarColor

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = requireNotNull(_binding) { "_binding is null ,,," }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor(activity as MainActivity, R.color.bg_f5f5f5)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}