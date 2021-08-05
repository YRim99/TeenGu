package com.example.login
//CommunityActivity 내에서 게시판목록을 보여주는 프래그먼트
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout

class BoardFragment : Fragment() {
    lateinit var LinearLayout1 : LinearLayout
    lateinit var LinearLayout2 : LinearLayout
    lateinit var LinearLayout3 : LinearLayout
    lateinit var LinearLayout4 : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("intent_userid")

        LinearLayout1 = view.findViewById(R.id.LinearLayout1)
        LinearLayout2 = view.findViewById(R.id.LinearLayout2)
        LinearLayout3 = view.findViewById(R.id.LinearLayout3)
        LinearLayout4 = view.findViewById(R.id.LinearLayout4)


        //게시판 이동
        LinearLayout1.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Board_infoActivity::class.java)
                intent.putExtra("intent_userid",id)
                startActivity(intent)
            }
        }
        LinearLayout2.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Board_qaActivity::class.java)
                intent.putExtra("intent_userid",id)
                startActivity(intent)
            }
        }
        LinearLayout3.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Board_marketActivity::class.java)
                intent.putExtra("intent_userid",id) //주원 여기 추가했어
                startActivity(intent)
            }
        }
        LinearLayout4.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Board_adActivity::class.java)
                intent.putExtra("intent_userid",id) //주원 여기 추가했어
                startActivity(intent)
            }
        }
    }

    //위에 상단 메뉴바

}