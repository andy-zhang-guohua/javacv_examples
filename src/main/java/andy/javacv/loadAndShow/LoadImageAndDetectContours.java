package andy.javacv.loadAndShow;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static andy.javacv.loadAndShow.LoadAndShowCommon.buildFrame;
import static andy.javacv.loadAndShow.LoadAndShowCommon.toBufferedImage;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * 在图片中检测轮廓
 * Created by ZhangGuohua on 2017/9/5.
 */
@Slf4j
public class LoadImageAndDetectContours {
    public static void main(String[] args) throws Throwable {
        String filename = SystemUtils.USER_DIR + "\\pictures\\casino.png";
        // cvLoadImage 目前不支持包含非ASCII字符的路径
        opencv_core.IplImage image = cvLoadImage(filename);
        if (image == null) {
            log.debug("加载图片失败:{}", filename);
            return;
        }

        // 准备一个同等宽高的灰度图像
        int width = image.width();
        int height = image.height();
        opencv_core.IplImage grayImage = opencv_core.IplImage.create(width, height, IPL_DEPTH_8U, 1);
        cvCvtColor(image, grayImage, CV_BGR2GRAY);

        // Let's find some contours! but first some thresholding...
        cvThreshold(grayImage, grayImage, 128, 255, CV_THRESH_BINARY);

        // Objects allocated with a create*() or clone() factory method are automatically released
        // by the garbage collector, but may still be explicitly released by calling release().
        // You shall NOT call cvReleaseImage(), cvReleaseMemStorage(), etc. on objects allocated this way.
        opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();
        // To check if an output argument is null we may call either isNull() or equals(null).
        opencv_core.CvSeq contour = new opencv_core.CvSeq(null);
        cvFindContours(grayImage, storage, contour, Loader.sizeof(opencv_core.CvContour.class), CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
        while (contour != null && !contour.isNull()) {
            if (contour.elem_size() > 0) {
                opencv_core.CvSeq points = cvApproxPoly(contour, Loader.sizeof(opencv_core.CvContour.class), storage, CV_POLY_APPROX_DP, cvContourPerimeter(contour) * 0.02, 0);
                cvDrawContours(grayImage, points, opencv_core.CvScalar.BLUE, opencv_core.CvScalar.BLUE, -1, 1, CV_AA);
            }
            contour = contour.h_next();
        }


        BufferedImage bufferedImage = toBufferedImage(grayImage);

        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImage, 0, 0, null);
            }
        };

        JFrame frame = buildFrame(image.width(), image.height());
        frame.add(pane);
    }
}
