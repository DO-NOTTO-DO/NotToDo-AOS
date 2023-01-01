package kr.co.nottodo.presentation.schedule.bottomsheet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.nottodo.databinding.FragmentCalendarBottomSheetBinding

class CalendarBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentCalendarBottomSheetBinding? = null
    private val binding: FragmentCalendarBottomSheetBinding
        get() = requireNotNull(_binding) { "_binding is null ,,,," }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }
}