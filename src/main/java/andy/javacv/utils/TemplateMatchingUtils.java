package andy.javacv.utils;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * Created by ZhangGuohua on 2017/9/7.
 */
public class TemplateMatchingUtils {

    /**
     * 从一个背景图片中找到一个最匹配输入模板 pattern 的子图片，并返回其在原图中的位置和匹配算法的分，缺省匹配算法 TM_CCORR_NORMED
     * 　CV_TM_SQDIFF 平方差匹配法：该方法采用平方差来进行匹配；最好的匹配值为0；匹配越差，匹配值越大。
     * 　CV_TM_CCORR 相关匹配法：该方法采用乘法操作；数值越大表明匹配程度越好。
     * 　CV_TM_CCOEFF 相关系数匹配法：1表示完美的匹配；-1表示最差的匹配。
     * 　CV_TM_SQDIFF_NORMED 归一化平方差匹配法
     * 　CV_TM_CCORR_NORMED 归一化相关匹配法
     * 　CV_TM_CCOEFF_NORMED 归一化相关系数匹配法
     *
     * @param bg
     * @param pattern
     * @return
     */
    static Pair<Pair<Integer, Integer>, Double> findMostMatchingSubImage(String bg, String pattern) {
        opencv_core.Mat sourceColor = imread(bg);
        opencv_core.Mat sourceGrey = new opencv_core.Mat(sourceColor.size(), CV_8UC1);
        cvtColor(sourceColor, sourceGrey, COLOR_BGR2GRAY);
        //load in template in grey
        opencv_core.Mat template = imread(pattern, CV_LOAD_IMAGE_GRAYSCALE);//int = 0
        //Size for the result image
        opencv_core.Size size = new opencv_core.Size(sourceGrey.cols() - template.cols() + 1, sourceGrey.rows() - template.rows() + 1);
        opencv_core.Mat result = new opencv_core.Mat(size, CV_32FC1);
        matchTemplate(sourceGrey, template, result, TM_CCORR_NORMED);

        DoublePointer minVal = new DoublePointer(1);
        DoublePointer maxVal = new DoublePointer(1);
        opencv_core.Point min = new opencv_core.Point();
        opencv_core.Point max = new opencv_core.Point();
        minMaxLoc(result, minVal, maxVal, min, max, null);

        double maxScore = maxVal.get();
        Pair<Integer, Integer> maxLocation = ImmutablePair.of(max.x(), max.y());

        return ImmutablePair.of(maxLocation, maxScore);
    }
}
