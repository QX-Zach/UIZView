package personal.ui.lingchen.uizview.Utils;

import android.graphics.Color;

/**
 * Created by ozner_67 on 2018/1/15.
 * 邮箱：xinde.zhang@cftcn.com
 */

public class OznerColorUtils {

    public static int caculateColor(float valPer, int firstColor, int secondColor, int[] otherColor) {
        int colorNum = 2;//颜色总个数
        if (otherColor != null && otherColor.length > 0) {
            colorNum += otherColor.length;
        }
        int sectionNum = colorNum - 1;//计算颜色区间个数
        float perStep = 100.f / sectionNum;//计算颜色区间百分比步长
        int perInSection = (int) (valPer / perStep);//计算百分比所落的区间
        int fbegin = firstColor;
        int fend = secondColor;
        if (colorNum > 2) {
            if (perInSection == otherColor.length + 1) {
                return otherColor[otherColor.length - 1];
            } else if (perInSection == 1) {
                fbegin = secondColor;
                fend = otherColor[0];
            } else if (perInSection > 1) {
                fbegin = otherColor[perInSection - 2];
                fend = otherColor[perInSection - 1];
            }
        }
        float tempPer = (valPer - perStep * perInSection) / perStep;
        //计算红色分量
        int fbRed = Color.red(fbegin);
        int feRed = Color.red(fend);
        int interval = feRed - fbRed;
        int rRed = (int) (fbRed + interval * tempPer);
        //计算绿色分量
        int fbGreen = Color.green(fbegin);
        int feGreen = Color.green(fend);
        interval = feGreen - fbGreen;
        int rGreen = (int) (fbGreen + interval * tempPer);
        //计算蓝色分量
        int fbBlue = Color.blue(fbegin);
        int feBlue = Color.blue(fend);
        interval = feBlue - fbBlue;
        int rBlue = (int) (fbBlue + interval * tempPer);

        return Color.argb(0xff, rRed, rGreen, rBlue);
    }
}
