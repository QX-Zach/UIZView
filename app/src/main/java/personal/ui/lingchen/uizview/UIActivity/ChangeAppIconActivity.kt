package personal.ui.lingchen.uizview.UIActivity

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.blankj.utilcode.util.SPUtils
import kotlinx.android.synthetic.main.activity_change_app_icon.*
import personal.ui.lingchen.uizview.R

/**
 * @author：zach on 2020/12/4 14:20
 * 修改桌面图标
 */
class ChangeAppIconActivity : AppCompatActivity() {
    private val LAST_SELECT_ICON_INDEX = "last_select_icon_index";
    private var selectIconIndex: Int = 0
    private var lastSelectIndex: Int = 0
    private val commentList: MutableList<ComponentName> = mutableListOf()

    private fun initComponent() {
        commentList.add(ComponentName(applicationContext, "personal.ui.lingchen.uizview.defaultIcon"))
        commentList.add(ComponentName(applicationContext, "personal.ui.lingchen.uizview.iconOne"))
        commentList.add(ComponentName(applicationContext, "personal.ui.lingchen.uizview.iconTwo"))
        commentList.add(ComponentName(applicationContext, "personal.ui.lingchen.uizview.iconThree"))
        commentList.add(ComponentName(applicationContext, "personal.ui.lingchen.uizview.iconFour"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_app_icon)
        initComponent()
        initClick()
        lastSelectIndex = SPUtils.getInstance().getInt(LAST_SELECT_ICON_INDEX, 0)
        selectIconIndex = lastSelectIndex
        setSelectIconView(selectIconIndex)
    }

    private fun initClick() {
        llayDefault.setOnClickListener {
            selectIconIndex = 0
            setSelectIconView(selectIconIndex)
        }

        llayOne.setOnClickListener {
            selectIconIndex = 1
            setSelectIconView(selectIconIndex)
        }

        llayTwo.setOnClickListener {
            selectIconIndex = 2
            setSelectIconView(selectIconIndex)
        }

        llayThree.setOnClickListener {
            selectIconIndex = 3
            setSelectIconView(selectIconIndex)
        }

        llayFour.setOnClickListener {
            selectIconIndex = 4
            setSelectIconView(selectIconIndex)
        }

        btnSave.setOnClickListener {
            if (lastSelectIndex == selectIconIndex) {
                Toast.makeText(this, "设置成功", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            disableComponent(commentList[lastSelectIndex])
            enableComponent(commentList[selectIconIndex])
            lastSelectIndex = selectIconIndex
            SPUtils.getInstance().put(LAST_SELECT_ICON_INDEX, selectIconIndex)
            Toast.makeText(this, "设置成功", Toast.LENGTH_LONG).show()
//            this.finish()
        }
    }

    private fun setSelectIconView(position: Int) {
        rbDefault.isChecked = position == 0
        rbOne.isChecked = position == 1
        rbTwo.isChecked = position == 2
        rbThree.isChecked = position == 3
        rbFour.isChecked = position == 4
    }

    private fun enableComponent(componentName: ComponentName) {
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP)
    }

    private fun disableComponent(componentName: ComponentName) {
        packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        )
    }
}