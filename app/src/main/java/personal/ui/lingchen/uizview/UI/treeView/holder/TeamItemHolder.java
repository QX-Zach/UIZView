package personal.ui.lingchen.uizview.UI.treeView.holder;

import android.accounts.Account;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.treeView.model.TreeNode;

/**
 *
 */
public class TeamItemHolder extends TreeNode.BaseNodeViewHolder<String> {
    private LayoutInflater layoutInflater;
    private TextView tvTitle;
    private ImageView ivIcon;
    private TreeNode treeNode;

    public TeamItemHolder(Context context) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View createNodeView(TreeNode node, String value) {
        treeNode = node;
        final View view = layoutInflater.inflate(R.layout.layout_team_tree_view_item, null, false);
        tvTitle = view.findViewById(R.id.tvTitle);
        ivIcon = view.findViewById(R.id.ivIcon);

        tvTitle.setText(value);

        if (node.isLeaf()) {
            ivIcon.setVisibility(View.GONE);
            tvTitle.setPadding(20, 5, 10, 5);
        } else {
            ivIcon.setVisibility(View.VISIBLE);
            tvTitle.setPadding(20, 5, 20, 5);
        }

        return view;
    }

    @Override
    public void toggle(boolean active) {
        if (treeNode.isLeaf()) {
            ivIcon.setVisibility(View.GONE);
            tvTitle.setPadding(20, 5, 10, 5);
        } else {
            ivIcon.setVisibility(View.VISIBLE);
            tvTitle.setPadding(20, 5, 20, 5);
        }
        ivIcon.setImageDrawable(ContextCompat.getDrawable(context, active ? R.mipmap.partner_circle_minus_gray : R.mipmap.partner_circle_add_gray));
    }
}
