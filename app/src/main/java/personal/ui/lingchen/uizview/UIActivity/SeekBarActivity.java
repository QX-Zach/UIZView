package personal.ui.lingchen.uizview.UIActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.BindView;
import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.UIZSeekBar;

public class SeekBarActivity extends AppCompatActivity {

    @BindView(R.id.ui_seekbar)
    UIZSeekBar uiSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        ButterKnife.bind(this);
        uiSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
}
