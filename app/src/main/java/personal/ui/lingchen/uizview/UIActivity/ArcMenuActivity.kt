package personal.ui.lingchen.uizview.UIActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_arc_menu.*
import personal.ui.lingchen.uizview.R

class ArcMenuActivity : AppCompatActivity() {
    val TAG:String = "ArcMenuActivity"
    var value = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_menu)
        btnAdd.setOnClickListener {
            if (value >= 100) {
                value = 0
            } else {
                value += 50
            }
            uizPC.apply {
                targetValue = value
            }
        }

        btnZero.setOnClickListener {
            value = 0
            uizPC.apply {
                targetValue = 0
            }
        }

        btn100.setOnClickListener {
            value = 100
            uizPC.apply {
                targetValue = 100
            }
        }
        test()
    }

    fun test(){
        val titleArray = ArrayList<String>()
        titleArray.add("1")
        titleArray.add("2")
        titleArray.add("3")
        titleArray.add("4")
        val str = titleArray.joinToString(",")
        Log.e(TAG, "test: $str")
    }
}
