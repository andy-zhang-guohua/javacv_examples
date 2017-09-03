package andy.javacv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvSmooth;

/**
 * Created by ZhangGuohua on 2017/9/3.
 */
@Slf4j
public class Smoother {

    public static void main(String[] args) throws Throwable {
        String filename = SystemUtils.USER_DIR + "\\pictures\\sea.jpg";
        //BufferedImage bimg = ImageIO.read(new File(filename));
        //opencv_core.IplImage image = toIplImage(bimg);
        // cvcvLoadImage 目前不支持包含非ASCII字符的路径
        IplImage image = cvLoadImage(filename);
        if (image == null) {
            log.debug("加载图片失败:{}", filename);
            return;
        }
        cvSmooth(image, image);


        BufferedImage sbimg = toBufferedImage(image);

        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(sbimg, 0, 0, null);
            }
        };

        JFrame frame = buildFrame(image.width(), image.height());
        frame.add(pane);
    }

    public static IplImage toIplImage(BufferedImage bufImage) {
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
        return iplImage;
    }


    public static BufferedImage toBufferedImage(IplImage src) {
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter bimConverter = new Java2DFrameConverter();
        org.bytedeco.javacv.Frame frame = iplConverter.convert(src);
        BufferedImage img = bimConverter.convert(frame);
        img.flush();
        return img;
    }

    private static JFrame buildFrame(int width, int height) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        return frame;
    }
}
