package andy.javacv.matching;


import org.apache.commons.lang3.SystemUtils;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.indexer.FloatIndexer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * 模板匹配样例，修改自 : https://github.com/bytedeco/javacv/blob/master/samples/TemplateMatching.java
 * 基于 Mat 的代码例子，和基于 IplImage  的代码例子
 * <p/>
 * Created by ZhangGuohua on 2017/9/7.
 */

/**
 * 　CV_TM_SQDIFF 平方差匹配法：该方法采用平方差来进行匹配；最好的匹配值为0；匹配越差，匹配值越大。
 * 　CV_TM_CCORR 相关匹配法：该方法采用乘法操作；数值越大表明匹配程度越好。
 * 　CV_TM_CCOEFF 相关系数匹配法：1表示完美的匹配；-1表示最差的匹配。
 * 　CV_TM_SQDIFF_NORMED 归一化平方差匹配法
 * 　CV_TM_CCORR_NORMED 归一化相关匹配法
 * 　CV_TM_CCOEFF_NORMED 归一化相关系数匹配法
 */
public class TemplateMatching {

    public static void main(String[] args) throws Exception {
        newStyle(args);
        //oldStyle(args);
    }


    public static void newStyle(String[] args) {
        String sample_filename = SystemUtils.USER_DIR + "\\pictures\\matching\\casino.png";
        String pattern_filename = SystemUtils.USER_DIR + "\\pictures\\matching\\patterns\\HE_WITH_BLUE_DOT.png";

        //read in image default colors
        Mat sourceColor = imread(sample_filename);
        Mat sourceGrey = new Mat(sourceColor.size(), CV_8UC1);
        cvtColor(sourceColor, sourceGrey, COLOR_BGR2GRAY);
        //load in template in grey
        Mat template = imread(pattern_filename, CV_LOAD_IMAGE_GRAYSCALE);//int = 0
        //Size for the result image
        Size size = new Size(sourceGrey.cols() - template.cols() + 1, sourceGrey.rows() - template.rows() + 1);
        Mat result = new Mat(size, CV_32FC1);
        matchTemplate(sourceGrey, template, result, TM_CCORR_NORMED);

        DoublePointer minVal = new DoublePointer(1);
        DoublePointer maxVal = new DoublePointer(1);
        Point min = new Point();
        Point max = new Point();
        minMaxLoc(result, minVal, maxVal, min, max, null);
        rectangle(sourceColor, new Rect(max.x(), max.y(), template.cols(), template.rows()), randColor(), 2, 0, 0);

        imshow("Original marked", sourceColor);
        imshow("Template", template);
        imshow("Results matrix", result);
        waitKey(0);
        destroyAllWindows();

    }

    // some usefull things.
    public static Scalar randColor() {
        int b, g, r;
        b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        return new Scalar(b, g, r, 0);
    }

    public static List<Point> getPointsFromMatAboveThreshold(Mat m, float t) {
        List<Point> matches = new ArrayList<Point>();
        FloatIndexer indexer = m.createIndexer();
        for (int y = 0; y < m.rows(); y++) {
            for (int x = 0; x < m.cols(); x++) {
                if (indexer.get(y, x) > t) {
                    System.out.println("(" + x + "," + y + ") = " + indexer.get(y, x));
                    matches.add(new Point(x, y));
                }
            }
        }
        return matches;
    }

    public static void oldStyle(String[] args) {
        String sample_filename = SystemUtils.USER_DIR + "\\pictures\\matching\\casino.png";
        String pattern_filename = SystemUtils.USER_DIR + "\\pictures\\matching\\patterns\\XIAN_WITH_RED_DOT.png";

        //get color source image to draw red rect on later
        IplImage srcColor = cvLoadImage(sample_filename);
        //create blank 1 channel image same size as the source
        IplImage src = cvCreateImage(cvGetSize(srcColor), IPL_DEPTH_8U, 1);
        //convert source to grey and copy to src
        cvCvtColor(srcColor, src, CV_BGR2GRAY);
        //get the image to match loaded in greyscale.
        IplImage tmp = cvLoadImage(pattern_filename, 0);
        //this image will hold the strength of the match
        //as the template is translated across the image
        IplImage result = cvCreateImage(
                cvSize(src.width() - tmp.width() + 1,
                        src.height() - tmp.height() + 1), IPL_DEPTH_32F, src.nChannels());

        cvZero(result);

        // Match Template Function from OpenCV
        cvMatchTemplate(src, tmp, result, CV_TM_CCORR_NORMED);

        // double[] min_val = new double[2];
        // double[] max_val = new double[2];
        DoublePointer min_val = new DoublePointer();
        DoublePointer max_val = new DoublePointer();

        CvPoint minLoc = new CvPoint();
        CvPoint maxLoc = new CvPoint();

        cvMinMaxLoc(result, min_val, max_val, minLoc, maxLoc, null);

        // Get the Max or Min Correlation Value
        // System.out.println(Arrays.toString(min_val));
        // System.out.println(Arrays.toString(max_val));

        CvPoint point = new CvPoint();
        point.x(maxLoc.x() + tmp.width());
        point.y(maxLoc.y() + tmp.height());
        // cvMinMaxLoc(src, min_val, max_val,0,0,result);

        cvRectangle(srcColor, maxLoc, point, CvScalar.RED, 2, 8, 0); // Draw a
        // Rectangle for
        // Matched
        // Region

        cvShowImage("Lena Image", srcColor);
        cvWaitKey(0);
        cvReleaseImage(srcColor);
        cvReleaseImage(src);
        cvReleaseImage(tmp);
        cvReleaseImage(result);
    }
}