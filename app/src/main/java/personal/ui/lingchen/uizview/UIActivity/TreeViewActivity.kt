package personal.ui.lingchen.uizview.UIActivity

import android.media.tv.TvView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_tree_view.*
import kotlinx.android.synthetic.main.layout_team_tree_view_item.*
import personal.ui.lingchen.uizview.R
import personal.ui.lingchen.uizview.UI.treeView.holder.SimpleViewHolder
import personal.ui.lingchen.uizview.UI.treeView.holder.TeamItemHolder
import personal.ui.lingchen.uizview.UI.treeView.model.TreeNode
import personal.ui.lingchen.uizview.UI.treeView.view.AndroidTreeView
import personal.ui.lingchen.uizview.UI.treeView.view.TwoDScrollView

/**
 * @author：zach on 2020/9/15 15:55
 * tree view
 */
class TreeViewActivity : AppCompatActivity() {
    val TAG: String = "TreeViewActivity"
    lateinit var tView: AndroidTreeView
    val title: String = "item"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree_view)
        val root: TreeNode = TreeNode.root()
        val s1: TreeNode = TreeNode("zach S1")
//                .apply {
//            setViewHolder(TeamItemHolder(this@TreeViewActivity))
//        }
        val s2: TreeNode = TreeNode("zach S2")
//                .apply {
//            setViewHolder(TeamItemHolder(this@TreeViewActivity))
//        }

        val s3: TreeNode = TreeNode("zach S1_S3")
//                .apply {
//            setViewHolder(TeamItemHolder(this@TreeViewActivity))
//        }


        fillFolder(s3)
//        fillFolder(s1)
        fillFolder(s2)
        root.addChildren(s2,s3)
//        root.addChild(s1)
        tView = AndroidTreeView(this, root)
        tView.setDefaultAnimation(false)
        tView.setUse2dScroll(true)

        tView.setDefaultNodeClickListener({ treeNode: TreeNode, any: Any ->
            Log.e(TAG, "onCreate:点击${treeNode.value} ")
        })
        tView.setDefaultContainerStyle(R.style.TreeNodeStyle, true)
        val view = tView.getView()
        rlayContainer.addView(view)
        tView.addNode(root, s1)
//        tView.addNode(s1, s3)
        tView.expandNode(s1)
    }

    private fun fillFolder(folder: TreeNode) {
        var currentNode = folder
        for (i in 0..40) {
            val file = TreeNode("zach$i $title")//.setViewHolder(TeamItemHolder(this))
            currentNode.addChild(file)
//            currentNode = file
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("tState", tView.saveState)
    }
}