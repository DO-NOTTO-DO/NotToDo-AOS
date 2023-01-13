package kr.co.nottodo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.nottodo.databinding.FragmentCalendarBottomSheetChangeBinding
import kr.co.nottodo.view.calendar.monthly.util.convertDateToString
import timber.log.Timber

class CalendarBottomSheetChange(private val missionId: Int) : BottomSheetDialogFragment() {
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

    //localDate를 yyy-어쩌고로 바꾸는 함수

    private fun selectDate() {
        binding.btnCalendarBottomSheetChoice.setOnClickListener {
            //홈 바텀시트 다른날에도
            val apiDateList: List<String> = binding.calendarBottomSheet.selectedDays.map {
                it.convertDateToString()!!
            }
            dismiss()
            Timber.tag("ssong-develop").e("apidate$apiDateList")
            viewModel.postMissionAnotherDay(missionId, apiDateList)
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
