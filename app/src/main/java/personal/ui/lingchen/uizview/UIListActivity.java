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
import personal.ui.lingchen.uizview.UIActivity.ZheXianActivity;

public class UIListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lvUIList)
    ListView lvUIList;

    private String[] uiItemName = {"折线图->油压"};

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
            default:
                break;
        }
    }
}
