package kr.co.nottodo.presentation.mission.bottomsheet.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.nottodo.databinding.FragmentCalendarBottomSheetBinding
import kr.co.nottodo.presentation.mission.addition.viewmodel.AdditionViewModel
import kr.co.nottodo.view.calendar.monthly.util.convertDateToString

class CalendarBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentCalendarBottomSheetBinding? = null
    private val binding: FragmentCalendarBottomSheetBinding
        get() = requireNotNull(_binding) { "_binding is null ,,,," }
    private val viewModel: AdditionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectDate()
    }

    private fun selectDate() {
        binding.btnCalendarBottomSheetChoice.setOnClickListener {
            viewModel.additionDate.value =
                binding.calendarBottomSheet.selectedDate.convertDateToString()
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        val TAG = this::class.java.simpleName
    }
}