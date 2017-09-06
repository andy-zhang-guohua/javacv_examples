package andy.javacv.utils;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.awt.image.BufferedImage;

/**
 * 不同格式图像/帧数据结构对象之间的转换工具方法
 * Created by ZhangGuohua on 2017/9/3.
 */
public class ConversionUtils {
    /**
     * 将 BufferedImage 转化为 IplImage
     *
     * @param bufferedImage
     * @return
     */
    public static opencv_core.IplImage toIplImage(BufferedImage bufferedImage) {
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufferedImage));
        return iplImage;
    }

    /**
     * 将 BufferedImage 转化为 Mat
     *
     * @param bufferedImage
     * @return
     */
    public static opencv_core.Mat toMat(BufferedImage bufferedImage) {
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
        opencv_core.Mat mat = matConverter.convert(java2dConverter.convert(bufferedImage));
        return mat;
    }


    /**
     * 将 IplImage 转化为 BufferedImage
     *
     * @param iplImage
     * @return
     */
    public static BufferedImage toBufferedImage(opencv_core.IplImage iplImage) {
        OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        org.bytedeco.javacv.Frame frame = iplConverter.convert(iplImage);
        Java2DFrameConverter bimConverter = new Java2DFrameConverter();
        BufferedImage img = bimConverter.convert(frame);
        img.flush();
        return img;
    }

    /**
     * 将 Mat 转化为 BufferedImage
     *
     * @param mat
     * @return
     */
    public static BufferedImage toBufferedImage(opencv_core.Mat mat) {
        OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
        Java2DFrameConverter bimConverter = new Java2DFrameConverter();
        org.bytedeco.javacv.Frame frame = matConverter.convert(mat);
        BufferedImage img = bimConverter.convert(frame);
        img.flush();
        return img;
    }

    /**
     * 将 Frame 转化为 Mat
     *
     * @param frame
     * @return
     */
    public static opencv_core.Mat toMat(Frame frame) {
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.Mat mat = converter.convertToMat(frame);
        return mat;
    }

    /**
     * 将 Frame 转化为 IplImage
     *
     * @param frame
     * @return
     */
    public static opencv_core.IplImage toIplImage(Frame frame) {
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage iplImage = converter.convert(frame);
        return iplImage;
    }

    /**
     * 将 IplImage 转化为  Frame
     *
     * @param iplImage
     * @return
     */
    public static Frame toFrame(opencv_core.IplImage iplImage) {
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        Frame frame = converter.convert(iplImage);
        return frame;
    }

    /**
     * 将 Mat 转化为  Frame
     *
     * @param mat
     * @return
     */
    public static Frame toFrame(opencv_core.Mat mat) {
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        Frame frame = converter.convert(mat);
        return frame;
    }
}
