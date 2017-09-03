package andy.javacv.loadAndShow;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.bytedeco.javacpp.opencv_core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static andy.javacv.loadAndShow.LoadAndShowCommon.buildFrame;
import static andy.javacv.loadAndShow.LoadAndShowCommon.toBufferedImage;
import static andy.javacv.utils.ConversionUtils.toMat;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

/**
 * Created by ZhangGuohua on 2017/9/3.
 */
@Slf4j
public class LoadToMatAndShow {

    public static void main(String[] args) throws Throwable {
        String filename = SystemUtils.USER_DIR + "\\pictures\\sea.jpg";
        opencv_core.Mat image = imread(filename);
        if (image == null || image.arrayWidth() == 0 || image.arrayHeight() == 0) {
            log.debug("加载图片失败:{}", filename);
            return;
        }

        BufferedImage bufferedImage = toBufferedImage(image);


        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bufferedImage, 0, 0, null);
            }
        };

        JFrame frame = buildFrame(bufferedImage.getWidth(), bufferedImage.getHeight());
        frame.add(pane);
    }
}
