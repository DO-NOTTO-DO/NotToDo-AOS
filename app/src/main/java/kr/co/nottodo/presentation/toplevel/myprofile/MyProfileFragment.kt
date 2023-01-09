package kr.co.nottodo.presentation.toplevel.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kr.co.nottodo.databinding.FragmentMyprofileBinding


class MyProfileFragment : Fragment()  {
lateinit var binding: FragmentMyprofileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 1. 뷰 바인딩 설정
        val binding = FragmentMyprofileBinding.inflate(inflater, container, false)

        // 3. 프래그먼트 레이아웃 뷰 반환
        return binding.root
    }
}