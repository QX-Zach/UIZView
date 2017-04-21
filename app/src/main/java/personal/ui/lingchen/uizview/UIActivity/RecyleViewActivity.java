package personal.ui.lingchen.uizview.UIActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.RecyleViewTest.DividerItemDecoration;

/**
 * Created by ozner_67 on 2016/10/9.
 * 邮箱：xinde.zhang@cftcn.com
 */
public class RecyleViewActivity extends AppCompatActivity {

    @InjectView(R.id.tv_bottom)
    TextView tvBottom;
    @InjectView(R.id.rv_list)
    RecyclerView rvList;
    private RecyleAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyle_view);
        ButterKnife.inject(this);
        initData();
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(mAdapter = new RecyleAdapter());
        rvList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }



    class RecyleAdapter extends RecyclerView.Adapter<RecyleAdapter.RecyleHolder>{


        @Override
        public RecyleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyleHolder holder = new RecyleHolder(LayoutInflater
                    .from(RecyleViewActivity.this)
                    .inflate(R.layout.recyle_item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyleHolder holder, int position) {
            holder.tv_con.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }


        class RecyleHolder extends RecyclerView.ViewHolder{
            TextView tv_con;

            public RecyleHolder(View itemView) {
                super(itemView);
                tv_con = (TextView)itemView.findViewById(R.id.tv_con);
            }
        }
    }

}
