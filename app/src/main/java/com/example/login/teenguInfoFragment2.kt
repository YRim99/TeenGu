package com.example.login
// 홈 -> 정보 -> 지원단체 화면
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

class teenguInfoFragment2 : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_teengu_info2, container, false)

        val examBanner : ImageButton = view.findViewById(R.id.examBanner)
        val group1 : ImageButton = view.findViewById(R.id.Gfound1)
        val group2 : ImageButton = view.findViewById(R.id.guLove)
        val group3 : ImageButton = view.findViewById(R.id.hanguk)
        val group4 : ImageButton = view.findViewById(R.id.newLife)

        examBanner.setOnClickListener(this)
        group1.setOnClickListener(this)
        group2.setOnClickListener(this)
        group3.setOnClickListener(this)
        group4.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.examBanner -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gumsi.or.kr/ged/usr/info/examdateList.do"))
                startActivity(intent)
            }
            R.id.Gfound1 -> {
                val intent1 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gfound.org/bbs/board.php?bo_table=support_1&wr_id=101&ACE_REF=adwords_g&ACE_KW=%EB%AF%B8%ED%98%BC%EB%AA%A8%EC%A7%80%EC%9B%90%EC%82%AC%EC%97%85&gclid=CjwKCAjwgISIBhBfEiwALE19Sdw8q5WMDQB33ryTdBp5ek7JC4Ug1YpJtsFV1ZQvWO125r1oG0g7cRoCgNEQAvD_BwE"))
                startActivity(intent1)
            }
            R.id.guLove -> {
                val intent2 = Intent(Intent.ACTION_VIEW, Uri.parse("http://godslove.or.kr/kor/html/about/godslovechurch.asp"))
                startActivity(intent2)
            }
            R.id.hanguk-> {
                val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse("https://kumsn.org/"))
                startActivity(intent3)
            }

            R.id.newLife -> {
                val intent4 = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cjnewlife.kr/public/sub3/view_cont/1"))
                startActivity(intent4)
            }


        }
    }

}