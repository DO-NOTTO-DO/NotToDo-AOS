package kr.co.nottodo.util.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import kr.co.nottodo.R

fun FlexboxLayout.addTextview(text: String, view: EditText) {
    // xml로 처음에 그려낸 녀석을 화면에 보이는 객체로 만듬!
    val textView = LayoutInflater.from(context)
        .inflate(R.layout.view_add_situation_list_text, null) as TextView

    // 위에 만든 객체의 크기를 지정해준다
    val layoutParams = ViewGroup.MarginLayoutParams(
        ViewGroup.MarginLayoutParams.WRAP_CONTENT,
        ViewGroup.MarginLayoutParams.WRAP_CONTENT
    )

    // 만들어준 객체가 텍스트뷰니까 텍스트를 넣어야 보이니까, 넣어줍니다!
    textView.text = text


    // 객체에 클릭 이벤트 부여 - 에디트 텍스트에 넣기
    textView.setOnClickListener {
        view.setText(text)
    }

    // textview간에 간격이 있기때문에, 오른쪽 간격을 디자이너가 정해준 만큼 띄워주면 되죠!
    layoutParams.leftMargin = context.dpToPx(4)
    layoutParams.rightMargin = context.dpToPx(4)
    layoutParams.bottomMargin = context.dpToPx(8)
    // 그러고 linearlayout에 넣어준다!
    addView(textView, layoutParams)
}