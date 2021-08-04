package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class teenguInfo : AppCompatActivity()  {

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout : TabLayout
    lateinit var login_id : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teengu_info)

        var id = intent.getStringExtra("intent_userid")
        login_id=id.toString()

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tablayout)

        val adapter = FragmentAdapter(this)
        val fragments = listOf<Fragment>(teenguInfoFragment1(), teenguInfoFragment2())
        val tabTitles = listOf<String>("지원제도", "지원단체")
        adapter.fragments.addAll(fragments)

        viewPager.adapter = adapter

        // 탭 메뉴와 스와이프 연결
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    //홈화면에 쓰일 메뉴 리소스 지정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    //우측 상단 홈 메뉴 누를 시 HomeActivity로 이동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_home ->{
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("intent_userid", login_id)
                startActivity(intent)

                return true
            }
            //우측 상단 채팅 메뉴 누를시 ChatActivity로 이동
            R.id.action_chat ->{
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("intent_userid", login_id)
                startActivity(intent)

                return true
            }

            //마이페이지로 이동
            R.id.action_mypage -> {
                val intent = Intent(this, MypageActivity::class.java)
                intent.putExtra("intent_userid", login_id)
                startActivity(intent)

                return true
            }

        }


        return super.onOptionsItemSelected(item)
    }


}

class FragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}

