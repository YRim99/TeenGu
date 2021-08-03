package com.example.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

class teenguInfoFragment1 : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_teengu_info1, container, false)

        val grantDesBanner : ImageButton = view.findViewById(R.id.grantDesBanner)
        val grant_1 : ImageButton = view.findViewById(R.id.grant_1)
        val grant_2 : ImageButton = view.findViewById(R.id.grant_2)
        val grant1Text : TextView = view.findViewById(R.id.grant1Text)
        val grant2Text : TextView = view.findViewById(R.id.grant2Text)
        val grantText1 : TextView = view.findViewById(R.id.grantText1)
        val grantText2 : TextView = view.findViewById(R.id.grantText2)
        val grantText3 : TextView = view.findViewById(R.id.grantText3)
        val grantText4 : TextView = view.findViewById(R.id.grantText4)
        val grantText5 : TextView = view.findViewById(R.id.grantText5)
        val grantText6 : TextView = view.findViewById(R.id.grantText6)
        val grantText7 : TextView = view.findViewById(R.id.grantText7)
        val grantText8 : TextView = view.findViewById(R.id.grantText8)

        grantDesBanner.setOnClickListener(this)
        grant_1.setOnClickListener(this)
        grant_2.setOnClickListener(this)
        grant1Text.setOnClickListener(this)
        grant2Text.setOnClickListener(this)
        grantText1.setOnClickListener(this)
        grantText2.setOnClickListener(this)
        grantText3.setOnClickListener(this)
        grantText4.setOnClickListener(this)
        grantText5.setOnClickListener(this)
        grantText6.setOnClickListener(this)
        grantText7.setOnClickListener(this)
        grantText8.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.grantDesBanner -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://greatinform.com/single-parent/single-parent-family/"))
                startActivity(intent)
            }
            R.id.grant_1, R.id.grant1Text -> {
                val intent_1 = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bokjiro.go.kr/welInfo/retrieveGvmtWelInfo.do?welInfSno=66"))
                startActivity(intent_1)
            }
            R.id.grant_2, R.id.grant2Text -> {
                val intent_2 = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mogef.go.kr/cs/opf/cs_opf_f091.do"))
                startActivity(intent_2)
            }
            R.id.grantText1-> {
                val intent1 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gg.go.kr/contents/contents.do?ciIdx=652&menuId=2367"))
                startActivity(intent1)
            }
            R.id.grantText2-> {
                val intent2 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.xn--hc0by27bu6atul3dc6t.kr/main/contents/prgncyInftiy6"))
                startActivity(intent2)
            }
            R.id.grantText3-> {
                val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chuncheon.go.kr/index.chuncheon?menuCd=DOM_000000504018004000"))
                startActivity(intent3)
            }
            R.id.grantText4-> {
                val intent4 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sejong.go.kr/welfare/sub05_04_06.do"))
                startActivity(intent4)
            }
            R.id.grantText5-> {
                val intent5 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chuncheon.go.kr/index.chuncheon?menuCd=DOM_000000504018003000"))
                startActivity(intent5)
            }
            R.id.grantText6-> {
                val intent6 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sujeong-gu.go.kr/sub/content.asp?cIdx=73"))
                startActivity(intent6)
            }
            R.id.grantText7-> {
                val intent7 = Intent(Intent.ACTION_VIEW, Uri.parse("https://ansan.go.kr/iloveyou/notice/noticeDetail.do?seq=26"))
                startActivity(intent7)
            }
            R.id.grantText8-> {
                val intent8 = Intent(Intent.ACTION_VIEW, Uri.parse("https://sj.go.kr/page.do?mnu_uid=1291&"))
                startActivity(intent8)
            }
        }
    }


}