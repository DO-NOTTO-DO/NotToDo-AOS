package kr.co.nottodo.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.nottodo.databinding.FragmentCalendarBottomSheetChangeBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarBottomSheetChange : BottomSheetDialogFragment() {
    private var _binding: FragmentCalendarBottomSheetChangeBinding? = null
    private val binding: FragmentCalendarBottomSheetChangeBinding
        get() = requireNotNull(_binding) { "_binding is null ,,,," }
//    private val viewModel: HomeFragmentViewModel by activityViewModels()

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

    //localDate를 yyy-어쩌고로 바꾸는 함수
    private fun Date.convertApiDateString(date: MutableList<Date>) {
        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    private fun selectDate() {
        binding.btnCalendarBottomSheetChoice.setOnClickListener {
//            viewModel.additionDate.value =
//                binding.calendarBottomSheet.selectedDate?.toInstant()?.atOffset(
//                    ZoneOffset.UTC
//                )?.format(DateTimeFormatter.ofPattern(datePattern))
//            dismiss()
            Log.d("ssong-develop", "${binding.calendarBottomSheet.selectedDays}")
            val apiDateList = binding.calendarBottomSheet.selectedDays.map {
                it.convertApiDateString(binding.calendarBottomSheet.selectedDays) // date -> "2023-01-11"
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        val TAG = this::class.java.simpleName
        const val YEARPattern = "yyyy-MM-dd"
    }
}
