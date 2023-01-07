package kr.co.nottodo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.nottodo.databinding.FragmentHomeBottomBinding


class HomeBottomFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentHomeBottomBinding? = null
    private val binding get() = _binding ?: error("binding not init")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBottomBinding.inflate(layoutInflater, container, false)
        initBottomSheet()
        click()
        return binding.root
    }

    private fun initBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun click() {
        binding.tvHomeBottomTrash.setOnClickListener { this.dismiss() }
        binding.tvHomeBottomEdit.setOnClickListener { this.dismiss() }
        binding.tvHomeAnatherNatodo.setOnClickListener { this.dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}