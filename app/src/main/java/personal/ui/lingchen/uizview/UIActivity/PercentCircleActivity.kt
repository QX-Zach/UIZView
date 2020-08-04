package personal.ui.lingchen.uizview.UIActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_percent_circle.*
import personal.ui.lingchen.uizview.R

class PercentCircleActivity : AppCompatActivity() {
    var currentPercent = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_percent_circle)
        btnAdd.setOnClickListener {
            if(currentPercent<100){
                currentPercent += 10
                pcView.apply {
                    targetPercent = currentPercent
                }

            }
        }

        btnMinus.setOnClickListener {
            if(currentPercent>0){
                currentPercent -= 10
                pcView.apply {
                    targetPercent = currentPercent
                }

            }
        }

        btnSet.setOnClickListener {
            currentPercent = 100
            pcView.apply {
                targetPercent = currentPercent
            }
        }

        btnClear.setOnClickListener {
            currentPercent = 0
            pcView.apply {
                targetPercent = currentPercent
            }
        }
    }
}
