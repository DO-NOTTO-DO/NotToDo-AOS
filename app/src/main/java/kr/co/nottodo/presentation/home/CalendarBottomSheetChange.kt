package kr.co.nottodo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.nottodo.databinding.FragmentCalendarBottomSheetChangeBinding

class CalendarBottomSheetChange : BottomSheetDialogFragment() {
    private var _binding: FragmentCalendarBottomSheetChangeBinding? = null
    private val binding: FragmentCalendarBottomSheetChangeBinding
        get() = requireNotNull(_binding) { "_binding is null ,,,," }
    private val viewModel: HomeFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBottomSheetChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectDate()
    }

    private fun selectDate() {
        binding.btnCalendarBottomSheetChoice.setOnClickListener {
//            viewModel.additionDate.value =
//                binding.calendarBottomSheet.selectedDate?.toInstant()?.atOffset(
//                    ZoneOffset.UTC
//                )?.format(DateTimeFormatter.ofPattern(datePattern))
//            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val datePattern = "yyyy.MM.dd"
    }
}