package andy.javacv.utils;

import andy.javacv.common.exceptions.JavaCVException;
import org.apache.commons.lang3.StringUtils;
import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

/**
 * 图片加载工具
 * Created by ZhangGuohua on 2017/9/7.
 */
public class ImageLoadUtils {
    public static opencv_core.Mat load(String path) {
        if (StringUtils.isBlank(path)) {
            throw new JavaCVException("文件路径为空:" + path);
        }


        opencv_core.Mat image = imread(path);
        return image;
    }

    public static opencv_core.IplImage loadIplImage(String path) {
        if (StringUtils.isBlank(path)) {
            throw new JavaCVException("文件路径为空:" + path);
        }

        opencv_core.IplImage image = cvLoadImage(path);
        return image;
    }
}
