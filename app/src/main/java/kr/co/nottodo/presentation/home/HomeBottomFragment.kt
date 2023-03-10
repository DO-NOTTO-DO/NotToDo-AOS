package kr.co.nottodo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.nottodo.databinding.FragmentHomeBottomBinding


class HomeBottomFragment(title: String, situation: String, private val missionId: Int) :
    BottomSheetDialogFragment() {
    private var _binding: FragmentHomeBottomBinding? = null
    private val binding get() = _binding ?: error("binding not init")

    //todo 뷰모델에 변수 만들어서 거기에 저장해서 바텀시트에서 사용하기
    private var title = title
    private var situation = situation

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
        binding.tvHomeBottomTitleSituation.text = title
        binding.tvHomeBottomTitleNottodo.text = situation
    }

    private fun click() {
        binding.clHomeBottomDelete.setOnClickListener { dismiss() }
        binding.clHomeBottomEditTodo.setOnClickListener { dismiss() }
        binding.clHomeBottomAddAnother.setOnClickListener {
            val calendarBottomSheet =
                childFragmentManager.findFragmentByTag(CalendarBottomSheetChange.TAG) as? CalendarBottomSheetChange
                    ?: CalendarBottomSheetChange(missionId)
            if (!calendarBottomSheet.isAdded) {
                calendarBottomSheet.show(
                    childFragmentManager,
                    CalendarBottomSheetChange.TAG
                )
            }

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}