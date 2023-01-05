package kr.co.nottodo.presentation.schedule.addition.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.nottodo.databinding.FragmentAdditionDropdownBinding
import kr.co.nottodo.presentation.schedule.addition.adapter.AdditionDropdownHeaderAdapter
import kr.co.nottodo.presentation.schedule.addition.adapter.AdditionDropdownRecentSearchAdapter

class AdditionDropdownFragment : DialogFragment() {
    private var _binding: FragmentAdditionDropdownBinding? = null
    private val binding: FragmentAdditionDropdownBinding
        get() = requireNotNull(_binding) { "서치 프래그먼트에서 _binding이 널임" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionDropdownBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        binding.rcvAdditionDropdown.adapter = ConcatAdapter(
            AdditionDropdownHeaderAdapter(requireContext()),
            AdditionDropdownRecentSearchAdapter(requireContext())
        )
        binding.rcvAdditionDropdown.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}