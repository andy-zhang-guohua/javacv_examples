package andy.javacv.utils;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.awt.image.BufferedImage;

/**
 * 不同格式图像转换工具方法
 * Created by ZhangGuohua on 2017/9/3.
 */
public class ConversionUtils {
    public static opencv_core.IplImage toIplImage(BufferedImage bufImage) {
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
        return iplImage;
    }

    public static opencv_core.Mat toMat(BufferedImage bufImage) {
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
        opencv_core.Mat mat = matConverter.convert(java2dConverter.convert(bufImage));
        return mat;
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
        OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
        Java2DFrameConverter bimConverter = new Java2DFrameConverter();
        org.bytedeco.javacv.Frame frame = matConverter.convert(src);
        BufferedImage img = bimConverter.convert(frame);
        img.flush();
        return img;
    }
}
