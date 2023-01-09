package kr.co.nottodo.presentation.onboard.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.nottodo.databinding.FragmentOnBoardSecondBinding
import kr.co.nottodo.databinding.FragmentOnBoardThirdBinding

class OnBoardThirdFragment : Fragment() {

    private var _binding: FragmentOnBoardThirdBinding? = null
    private val binding: FragmentOnBoardThirdBinding
        get() = requireNotNull(_binding) { "_binding is null ,,," }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}