package andy.javacv.loadAndShow;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.bytedeco.javacpp.opencv_core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static andy.javacv.loadAndShow.LoadAndShowCommon.buildFrame;
import static andy.javacv.loadAndShow.LoadAndShowCommon.toBufferedImage;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;

/**
 * 加载图片，转变成灰度图片显示
 * Created by ZhangGuohua on 2017/9/5.
 */
@Slf4j
public class LoadImageAndShowInGray {
    public static void main(String[] args) throws Throwable {
        String filename = SystemUtils.USER_DIR + "\\pictures\\sea.jpg";
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
