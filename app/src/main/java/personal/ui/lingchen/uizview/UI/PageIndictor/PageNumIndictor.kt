package personal.ui.lingchen.uizview.UI.PageIndictor

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_page_num_indictor.view.*
import kotlinx.android.synthetic.main.item_page_num.view.*
import personal.ui.lingchen.uizview.R
import kotlin.math.max

/**
 * @author：zach on 2020/10/23 12:17
 * 数字页码指示器
 */

data class PageNumItem(
        var pageIndex: String,
        var enable: Boolean = true,
        var isSelect: Boolean = false
)

class PageNumVH(itemView: View) : RecyclerView.ViewHolder(itemView)

const val PAGE_PER: String = "page_pre"
const val PAGE_NEXT: String = "page_next"
const val PAGE_MORE: String = "···"

class PageNumIndictor @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    interface PageNumClick {
        fun onPageSelect(pageIndex: Int)
    }

    var pageClickListener: PageNumClick? = null

    /**
     * 总页数
     */
    var totalPage = 1

    var maxShowPage = 10

    /**
     * 选中的页码
     */
    private var currentSelectIndex: Int = 1

    private var startIndex: Int = 1
    private var endIndex: Int = totalPage


    private val showList: MutableList<PageNumItem> = mutableListOf()
    private val dataAdapter: PageNumAdapter = PageNumAdapter(context).apply {
        pageIndexClick = object : PageNumAdapter.ItemClick {
            override fun onItemClick(item: PageNumItem, position: Int) {
                var selectIndex = currentSelectIndex
                when (item.pageIndex) {
                    PAGE_PER -> {
                        if (selectIndex > 1) {
                            selectIndex -= 1
                        }
                    }
                    PAGE_NEXT -> {
                        if (selectIndex < totalPage) {
                            selectIndex += 1
                        }
                    }
                    else -> {
                        if (PAGE_MORE.equals(item.pageIndex)) {
                            if (position == 2) {
                                selectIndex = Integer.parseInt(showList[3].pageIndex) - 1
                                start = 0
                                end = 0
                            } else if (position == showList.size - 3) {
                                selectIndex = Integer.parseInt(showList[showList.size - 4].pageIndex) + 1
                                start = 0
                                end = 0
                            }
                        } else {
                            selectIndex = Integer.parseInt(item.pageIndex)
                        }
                    }
                }
                lastSelectIndex = currentSelectIndex
                currentSelectIndex = selectIndex
                calculatePageList(currentSelectIndex, false)
                pageClickListener?.onPageSelect(currentSelectIndex)
            }
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        this.adapter = dataAdapter
    }

    /**
     * 刷新数据更新
     */
    fun refreshPageData() {
        calculatePageList(currentSelectIndex, true)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(expandSpec, heightMeasureSpec)
    }

    /**
     * 数据状态
     * 0-总页数小于等于可显示的最大页数
     * 1-总页数大于可显示的页数，并且当前选择的页码距离1比较近
     * 2-总页数大于可显示的页数，并且当前选择的页码距离结束比较近
     * 3-总页数大于可显示的页数，并且当前选择的页码距离开始和结束都不近
     */
    private var status: Int = 0

    /**
     * 中间开始的
     */
    private var start: Int = 0

    /**
     * 中间结束的
     */
    private var end: Int = 0
    private var lastSelectIndex = 0

    /**
     * 计算要显示的页码
     * @param selectPageIndex 选中的页码
     */
    private fun calculatePageList(selectIndex: Int, isRefresh: Boolean) {
        if (isRefresh) {
            showList.clear()
        }
        if (totalPage <= maxShowPage) {
            status = 0
            if (showList.isEmpty()) {
                showList.add(PageNumItem(PAGE_PER, selectIndex > 1))
                for (i in 0 until totalPage) {
                    showList.add(PageNumItem((i + 1).toString(), true, i == selectIndex - 1))
                }
                showList.add(PageNumItem(PAGE_NEXT, selectIndex < totalPage))
            } else {
                for (i in 0 until showList.size) {
                    showList[i].isSelect = showList[i].pageIndex == selectIndex.toString()
                }
            }
        } else {

            if (totalPage - maxShowPage < 2) {
                if (selectIndex < maxShowPage - 1) {
                    if (status != 1) {
                        status = 1
                        showList.clear()
                        showList.add(PageNumItem(PAGE_PER, selectIndex > 1))
                        for (i in 0 until maxShowPage - 2) {
                            showList.add(PageNumItem((i + 1).toString(), true, i == selectIndex - 1))
                        }
                        showList.add(PageNumItem(PAGE_MORE, true, false))
                        showList.add(PageNumItem(totalPage.toString(), true, false))
                        showList.add(PageNumItem(PAGE_NEXT, true))
                    }
                } else {
                    if (status != 2) {
                        status = 2
                        showList.clear()
                        showList.add(PageNumItem(PAGE_PER, selectIndex > 1))
                        showList.add(PageNumItem("1", true))
                        showList.add(PageNumItem(PAGE_MORE, true))
                        val start = totalPage - maxShowPage + 2
                        for (i in start until maxShowPage) {
                            showList.add(PageNumItem((i + 1).toString(), true, i == selectIndex - 1))
                        }
                        showList.add(PageNumItem(totalPage.toString(), true, selectIndex == totalPage))
                        showList.add(PageNumItem(PAGE_NEXT, true))
                    }
                }
            } else {

                if (selectIndex < maxShowPage - 1) {
                    status = 3
                    showList.clear()
                    showList.add(PageNumItem(PAGE_PER, true))
                    for (i in 0 until maxShowPage - 2) {
                        showList.add(PageNumItem((i + 1).toString(), true, i == selectIndex - 1))
                    }
                    showList.add(PageNumItem(PAGE_MORE, true))
                    showList.add(PageNumItem(totalPage.toString(), true, selectIndex == totalPage))
                    showList.add(PageNumItem(PAGE_NEXT, true))
                } else if (selectIndex > totalPage - maxShowPage + 2) {
                    status = 4
                    showList.clear()
                    showList.add(PageNumItem(PAGE_PER, true))
                    showList.add(PageNumItem("1", true))
                    showList.add(PageNumItem(PAGE_MORE, true))
                    for (i in totalPage - maxShowPage + 2 until totalPage) {
                        showList.add(PageNumItem((i + 1).toString(), true, i == selectIndex - 1))
                    }
                    showList.add(PageNumItem(PAGE_NEXT, true))
                } else {
                    if (status != 5) {
                        start = 0
                        end = 0
                        status = 5
                    }
                    //不在中间区间范围内，重新计算开始值和结束值
                    if (selectIndex <= start || selectIndex > end) {
                        showList.clear()
                        if (lastSelectIndex > selectIndex) {
                            end = selectIndex
                            start = end - maxShowPage + 4
                        }

                        if (lastSelectIndex < selectIndex) {
                            start = selectIndex - 1
                            end = start + maxShowPage - 4
                        }

                        showList.add(PageNumItem(PAGE_PER, true))
                        showList.add(PageNumItem("1", true))
                        showList.add(PageNumItem(PAGE_MORE, true))

                        for (i in start until end) {
                            showList.add(PageNumItem((i + 1).toString(), true, i == selectIndex - 1))
                        }


                        showList.add(PageNumItem(PAGE_MORE, true))
                        showList.add(PageNumItem(totalPage.toString(), true, selectIndex == totalPage))
                        showList.add(PageNumItem(PAGE_NEXT, true))
                    }
                }


            }
        }

        showList[0].enable = selectIndex != 1

        showList[showList.size - 1].enable = selectIndex != totalPage


        for (i in 0 until showList.size) {
            showList[i].isSelect = showList[i].pageIndex == selectIndex.toString()
        }


        dataAdapter.setData(showList)
    }
}


class PageNumAdapter(private val context: Context) : RecyclerView.Adapter<PageNumVH>() {

    interface ItemClick {
        fun onItemClick(item: PageNumItem, position: Int)
    }

    private val dataList: MutableList<PageNumItem> = mutableListOf()

    var pageIndexClick: ItemClick? = null

    fun setData(list: MutableList<PageNumItem>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PageNumVH {
        return PageNumVH(LayoutInflater.from(context).inflate(R.layout.item_page_num, parent, false))
    }

    override fun onBindViewHolder(holder: PageNumVH, position: Int) {
        holder.itemView.clRoot.isSelected = dataList[position].isSelect
        holder.itemView.ivStep.isEnabled = dataList[position].enable
        if (PAGE_PER.equals(dataList[position].pageIndex) || PAGE_NEXT.equals(dataList[position].pageIndex)) {
            holder.itemView.tvIndex.visibility = View.GONE
            holder.itemView.ivStep.visibility = View.VISIBLE
        } else {
            holder.itemView.ivStep.visibility = View.GONE
            holder.itemView.tvIndex.visibility = View.VISIBLE
            holder.itemView.tvIndex.text = dataList[position].pageIndex
        }

        if (PAGE_PER.equals(dataList[position].pageIndex)) {
            holder.itemView.ivStep.setImageResource(R.drawable.left_page_enable_disable_arrow)
        } else if (PAGE_NEXT.equals(dataList[position].pageIndex)) {
            holder.itemView.ivStep.setImageResource(R.drawable.right_page_enable_disable_arrow)
        }

        holder.itemView.clRoot.setOnClickListener {
            if (dataList[position].enable) {
                pageIndexClick?.onItemClick(dataList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}