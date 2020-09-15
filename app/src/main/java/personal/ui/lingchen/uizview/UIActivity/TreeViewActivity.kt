package personal.ui.lingchen.uizview.UIActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_tree_view.*
import personal.ui.lingchen.uizview.R
import personal.ui.lingchen.uizview.UI.treeView.holder.SimpleViewHolder
import personal.ui.lingchen.uizview.UI.treeView.holder.TeamItemHolder
import personal.ui.lingchen.uizview.UI.treeView.model.TreeNode
import personal.ui.lingchen.uizview.UI.treeView.view.AndroidTreeView

/**
 * @author：zach on 2020/9/15 15:55
 * tree view
 */
class TreeViewActivity : AppCompatActivity() {
    lateinit var tView: AndroidTreeView
    val title: String = "zach item with very long title"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree_view)
        val root: TreeNode = TreeNode.root()
        val s1: TreeNode = TreeNode("zach S1").apply {
            setViewHolder(TeamItemHolder(this@TreeViewActivity))
        }
        val s2: TreeNode = TreeNode("zach S2").apply {
            setViewHolder(TeamItemHolder(this@TreeViewActivity))
        }

        val s3: TreeNode = TreeNode("zach S1_S3").apply {
            setViewHolder(TeamItemHolder(this@TreeViewActivity))
        }


        fillFolder(s3)
        s1.root.addChild(s3)
        fillFolder(s1)
        fillFolder(s2)
        root.addChildren(s1, s2)
        tView = AndroidTreeView(this, root)
        tView.setDefaultAnimation(true)
        tView.setUse2dScroll(true)

        tView.setDefaultContainerStyle(R.style.TreeNodeStyle, true)
        val view = tView.getView()
        rlayContainer.addView(view)
        if (savedInstanceState != null) {
            val state = savedInstanceState.getString("tState")
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state)
            }
        }
    }

    private fun fillFolder(folder: TreeNode) {
        var currentNode = folder
        for (i in 0..9) {
            val file = TreeNode("zach$i $title").setViewHolder(TeamItemHolder(this))
            currentNode.addChild(file)
            currentNode = file
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("tState", tView.saveState)
    }
}