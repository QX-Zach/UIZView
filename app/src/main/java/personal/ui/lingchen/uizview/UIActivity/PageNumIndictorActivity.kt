package personal.ui.lingchen.uizview.UIActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_page_num_indictor.*
import personal.ui.lingchen.uizview.R
import personal.ui.lingchen.uizview.UI.PageIndictor.PageNumIndictor

class PageNumIndictorActivity : AppCompatActivity() {
    val TAG: String = "PageNumIndictorActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_num_indictor)
        pageNum.pageClickListener = object : PageNumIndictor.PageNumClick {
            override fun onPageSelect(pageIndex: Int) {
                Log.e(TAG, "onPageSelect: 选中页码：$pageIndex")
                tvPageIndex.text = "选中的页码：$pageIndex"
            }

        }
        pageNum.totalPage = 45
        pageNum.refreshPageData()
    }
}