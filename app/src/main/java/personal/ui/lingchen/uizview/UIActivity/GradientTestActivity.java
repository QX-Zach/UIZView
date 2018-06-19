package personal.ui.lingchen.uizview.UIActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.BindView;
import personal.ui.lingchen.uizview.R;
import personal.ui.lingchen.uizview.UI.UIZTempDial;
import personal.ui.lingchen.uizview.Utils.OznerColorUtils;

public class GradientTestActivity extends AppCompatActivity {

    @BindView(R.id.gtvTest)
    GradientTestView gtvTest;
    @BindView(R.id.pbProgress)
    SeekBar pbProgress;
    @BindView(R.id.tvColorPre)
    TextView tvColorPre;
    @BindView(R.id.uizTempDial)
    UIZTempDial uizTempDial;

    private static final int FIRST_COLOR = 0xff415cec;
    private static final int SECOND_COLOR = 0xff6f6fef;
    private static final int[] OTHER_COLORS = new int[]{0xffe985f7, 0xfff0c170, 0xffec4141};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient_test);
        ButterKnife.bind(this);
        pbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvColorPre.setText("百分比：" + seekBar.getProgress());
                tvColorPre.setBackgroundColor(OznerColorUtils.caculateColor(seekBar.getProgress(),FIRST_COLOR, SECOND_COLOR, OTHER_COLORS));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                uizTempDial.setValue(seekBar.getProgress());
            }
        });
    }

    private void getBlockingThreadPool(){
        new ThreadPoolExecutor(0, Integer.MAX_VALUE, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread rc = new Thread(null,r,"Mqtt test task",1024*512);
                rc.setDaemon(true);
                return null;
            }
        });
    }
}
