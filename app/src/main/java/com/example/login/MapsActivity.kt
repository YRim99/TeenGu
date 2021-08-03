package com.example.login

import android.content.Intent
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.login.review
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.startActivity //anko 라이브러리 사용


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    // 지도 조작을 위한 객체.
    private lateinit var mMap: GoogleMap

    // db 객체 선언 및 연결
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var maps_view: LinearLayout
    lateinit var review : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // 어플이 사용되는 동안 화면 끄지 않기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //시작하면 세부 화면은 안 보이게
        maps_view = findViewById(R.id.maps_view)
        maps_view.visibility = View.GONE

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        dbManager = DBManager(this, "map", null, 1)
        sqlitedb = dbManager.readableDatabase
        maps_view = findViewById(R.id.maps_view)


        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM Nowon;", null)

        var num: Int = 0

        // cursor를 이용하여 각 컬럼 값 추출
        while (cursor.moveToNext()) {
            var name = cursor.getString(cursor.getColumnIndex("name")).toString()
            var field = cursor.getString(cursor.getColumnIndex("field")).toString()
            var location = cursor.getString(cursor.getColumnIndex("location")).toString()
            var call = cursor.getString(cursor.getColumnIndex("call"))
            var lat = cursor.getString(cursor.getColumnIndex("lat")).toString()
            var lng = cursor.getString(cursor.getColumnIndex("lng")).toString()

            var pos = LatLng(lat.toDouble(), lng.toDouble()) //위도, 경도

            val markerOptions = MarkerOptions()
            markerOptions.title(name) //병원 이름
            markerOptions.snippet(field) //병원 종류
            markerOptions.position(pos) //위치(위도, 경도 값)

            //핀 추가하면서 나머지 데이터도 미리 저장해 둔다
            val marker: Marker = mMap.addMarker(markerOptions) //핀 추가 및 마커 생성
            marker.tag =  location + "/" + call as String

            //해당 핀으로 카메라 이동
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 14F))
        }
        num++

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        // 마커 클릭 -> 카드뷰 띄움
        googleMap!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
                maps_view.visibility = View.VISIBLE
                var str_name = findViewById<TextView>(R.id.name)

                var str_field = findViewById<TextView>(R.id.field)
                var str_location = findViewById<TextView>(R.id.location)
                var call = findViewById<ImageButton>(R.id.call)
                review = findViewById(R.id.review)

                var arr = marker.tag.toString().split("/") //마커에 붙인 태그
                str_name.text = marker.title
                str_field.text = marker.snippet
                str_location.text = arr[0]
                var str_call : String = arr[1]

                // 아이디 받아오기
                val intent_userid = intent.getStringExtra("intent_userid")

                review.setOnClickListener{
                    // anko
                    startActivity<review>(
                        "$str_name" to "hosName",
                        "$intent_userid" to "intent_userid"
                    )
                }

                call.setOnClickListener {
                    var intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$str_call")
                    if(intent.resolveActivity(packageManager) != null){
                        startActivity(intent)
                    }
                }



                return false
            }
        })

        //맵 클릭 리스너-맵 클릭하면 카드뷰 없어짐
        mMap!!.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(latLng: LatLng) {
                maps_view.visibility = View.GONE
            }
        })

    }
}