package andy.javacv.matching;

/**
 * Created by ZhangGuohua on 2017/9/7.
 */

import andy.javacv.utils.TemplateMatchingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public abstract class TemplateMatchingInVideoFrame {

    public static void main(String[] args) throws Exception {
        String mp4 = SystemUtils.USER_DIR + "\\video\\small.mp4";
        //visitVideoFrames("e:/ffmpeg/aa.mp4","./target", "screenshot", 5);
        visitVideoFrames(mp4, "./target", "frame");
    }


    public static void visitVideoFrames(String videoFilePath, String targetDirOutputFrames, String targetFileNamePrefix) throws Exception {
        FFmpegFrameGrabber frameGrabber = FFmpegFrameGrabber.createDefault(videoFilePath);
        frameGrabber.start();
        final String rotate = frameGrabber.getVideoMetadata("rotate");
        final int intRotate = StringUtils.isBlank(rotate) ? 0 : Integer.valueOf(rotate);

        final int frameTotal = frameGrabber.getLengthInFrames();
        int frameIndex = 0;
        int targetFrame = -1;
        double maxScore = -1f;
        while (frameIndex < frameTotal) {
            Frame frame = frameGrabber.grabImage();

            if (intRotate > 0) {
                OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
                IplImage src = converter.convert(frame);
                frame = converter.convert(rotate(src, intRotate));
            }
            double score = visitVideoFrame(frame, targetDirOutputFrames, targetFileNamePrefix, frameIndex);
            if (score > maxScore) {
                maxScore = score;
                targetFrame = frameIndex;
            }

            frameIndex++;
        }

        log.info("最匹配帧 : {},{}", targetFrame, maxScore);
        frameGrabber.stop();
    }

    public static IplImage rotate(IplImage src, int angle) {
        IplImage img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
        opencv_core.cvTranspose(src, img);
        opencv_core.cvFlip(img, img, angle);
        return img;
    }

    public static double visitVideoFrame(Frame frame, String targetFilePath, String targetFileName, int index) {
        if (null == frame || null == frame.image) {
            return 0f;
        }

        Java2DFrameConverter converter = new Java2DFrameConverter();

        String ext = "png";
        String fileName = targetFilePath + File.separator + targetFileName + "_" + index + "." + ext;
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        File output = new File(fileName);
        try {
            ImageIO.write(bufferedImage, ext, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pattern = SystemUtils.USER_DIR + "\\video\\patterns\\frame_80.png";
        Pair<Pair<Integer, Integer>, Double> result = TemplateMatchingUtils.findMostMatchingSubImage(fileName, pattern);
        log.debug("Frame : {}, pattern : {}", fileName, result);

        return result.getRight();

    }
}
