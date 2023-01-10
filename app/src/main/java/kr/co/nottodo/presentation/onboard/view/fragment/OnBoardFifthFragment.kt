package kr.co.nottodo.presentation.onboard.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.nottodo.databinding.FragmentOnBoardFifthBinding

class OnBoardFifthFragment : Fragment() {

    private var _binding: FragmentOnBoardFifthBinding? = null
    private val binding: FragmentOnBoardFifthBinding
        get() = requireNotNull(_binding) { "_binding is null ,,," }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardFifthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}