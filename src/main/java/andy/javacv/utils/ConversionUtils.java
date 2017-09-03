package andy.javacv.utils;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.awt.image.BufferedImage;

/**
 * Created by ZhangGuohua on 2017/9/3.
 */
public class ConversionUtils {
    public static opencv_core.IplImage toIplImage(BufferedImage bufImage) {
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
        return iplImage;
    }


    public static BufferedImage toBufferedImage(opencv_core.IplImage src) {
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        org.bytedeco.javacv.Frame frame = iplConverter.convert(src);
        Java2DFrameConverter bimConverter = new Java2DFrameConverter();
        BufferedImage img = bimConverter.convert(frame);
        img.flush();
        return img;
    }

    public static BufferedImage toBufferedImage(opencv_core.Mat src) {
        OpenCVFrameConverter.ToMat iplConverter = new OpenCVFrameConverter.ToMat();
        Java2DFrameConverter bimConverter = new Java2DFrameConverter();
        org.bytedeco.javacv.Frame frame = iplConverter.convert(src);
        BufferedImage img = bimConverter.convert(frame);
        img.flush();
        return img;
    }
}
