package kr.co.nottodo.presentation.onboard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.co.nottodo.presentation.onboard.view.fragment.OnBoardFifthFragment
import kr.co.nottodo.presentation.onboard.view.fragment.OnBoardFourthFragment
import kr.co.nottodo.presentation.onboard.view.fragment.OnBoardSecondFragment
import kr.co.nottodo.presentation.onboard.view.fragment.OnBoardThirdFragment

class OnBoardViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var fragments: ArrayList<Fragment> = arrayListOf(
        OnBoardSecondFragment(),
        OnBoardThirdFragment(),
        OnBoardFourthFragment(),
        OnBoardFifthFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}