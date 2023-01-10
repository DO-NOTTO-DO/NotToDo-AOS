package kr.co.nottodo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.nottodo.databinding.FragmentHomeBottomBinding
import kr.co.nottodo.presentation.schedule.bottomsheet.view.CalendarBottomSheet


class HomeBottomFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentHomeBottomBinding? = null
    private val binding get() = _binding ?: error("binding not init")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBottomBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSheet()
        click()
    }

    private fun initBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun click() {
        binding.tvHomeBottomTrash.setOnClickListener { dismiss() }
        binding.tvHomeBottomEdit.setOnClickListener { dismiss() }
        binding.tvHomeAnatherNatodo.setOnClickListener {
            CalendarBottomSheet().show(childFragmentManager, CalendarBottomSheet().tag)

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}