package personal.ui.lingchen.uizview.UI.OznerAirDial;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;

/**
 * Created by ozner_67 on 2017/12/11.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class SnowFlake {
    private static final String TAG = "SnowFlake";
    //雪花半径上下限
    private static final float FLAKE_SIZE_LOWER = 1F;
    private static final float FLAKE_SIZE_UPPER = 6F;
    //雪花移动速度上下限
    private static final float INCREMENT_LOWER = 2F;
    private static final float INCREMENT_UPPER = 4F;
    //角度偏移最大值
    private static final float ANGLE_SWEEP = 10F;
    private Path sPath;



    //雪花半径
    private float sRadius;
    //雪花移动角度
    private float sAngle;
    //雪花移动速度
    private float sIncrement;
    //雪花位置
    private Point sPos;

    public static SnowFlake Create() {

        return new SnowFlake();
    }

    public void test(Canvas can){

    }




}
