package personal.ui.lingchen.uizview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.ui.lingchen.uizview.UIActivity.ArcMenuActivity;
import personal.ui.lingchen.uizview.UIActivity.CirclePercentActivity;
import personal.ui.lingchen.uizview.UIActivity.PercentCircleActivity;
import personal.ui.lingchen.uizview.UIActivity.UIZRippleIntroActivity;
import personal.ui.lingchen.uizview.UIActivity.ZheXianActivity;

public class UIListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lvUIList)
    ListView lvUIList;

    private String[] uiItemName = {
            "折线图->油压",
            "环形百分比",
            "弧形菜单",
            "水波",
            "厨上水机圆环"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uilist);
        ButterKnife.bind(this);
        lvUIList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, uiItemName));
        lvUIList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(UIListActivity.this, ZheXianActivity.class));
                break;
            case 1:
                startActivity(new Intent(UIListActivity.this, CirclePercentActivity.class));
                break;
            case 2:
                startActivity(new Intent(UIListActivity.this, ArcMenuActivity.class));
                break;
            case 3:
                startActivity(new Intent(UIListActivity.this, UIZRippleIntroActivity.class));
                break;
            case 4:
                startActivity(new Intent(UIListActivity.this, PercentCircleActivity.class));
                break;
            default:
                break;
        }
    }
}
