package andy.javacv.loadAndShow;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;


/**
 * Created by ZhangGuohua on 2017/9/3.
 */
@Slf4j
public class LoadAndShowCommon extends andy.javacv.utils.ConversionUtils {
    protected static JFrame buildFrame(int width, int height) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
        return frame;
    }
}
