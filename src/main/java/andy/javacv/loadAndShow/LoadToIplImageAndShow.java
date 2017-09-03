package andy.javacv.loadAndShow;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static andy.javacv.loadAndShow.LoadAndShowCommon.buildFrame;
import static andy.javacv.loadAndShow.LoadAndShowCommon.toBufferedImage;
import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

/**
 * Created by ZhangGuohua on 2017/9/3.
 */
@Slf4j
public class LoadToIplImageAndShow {

    public static void main(String[] args) throws Throwable {
        String filename = SystemUtils.USER_DIR + "\\pictures\\sea.jpg";
        // cvLoadImage 目前不支持包含非ASCII字符的路径
        IplImage image = cvLoadImage(filename);
        if (image == null) {
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

        JFrame frame = buildFrame(image.width(), image.height());
        frame.add(pane);
    }

}
