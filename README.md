## 2017-09-05
###
    1.在图片中检测轮廓
    2.学习函数 cvThreshold

    void cvThreshold( const CvArr* src,CvArr* dst, double threshold, double max_value, int threshold_type );
    参数说明
        src：原始数组 (单通道 , 8-bit of 32-bit 浮点数)。
        dst：输出数组，必须与 src 的类型一致，或者为 8-bit。
        threshold：阈值
        max_value：使用 CV_THRESH_BINARY 和 CV_THRESH_BINARY_INV 的最大值。
        threshold_type：阈值类型
            threshold_type=CV_THRESH_BINARY:如果 src(x,y)>threshold ,dst(x,y) = max_value; 否则,dst（x,y）=0;
            threshold_type=CV_THRESH_BINARY_INV:如果 src(x,y)>threshold,dst(x,y) = 0; 否则,dst(x,y) = max_value.
            threshold_type=CV_THRESH_TRUNC:如果 src(x,y)>threshold，dst(x,y) = max_value; 否则dst(x,y) = src(x,y).
            threshold_type=CV_THRESH_TOZERO:如果src(x,y)>threshold，dst(x,y) = src(x,y) ; 否则 dst(x,y) = 0。
            threshold_type=CV_THRESH_TOZERO_INV:如果 src(x,y)>threshold，dst(x,y) = 0 ; 否则dst(x,y) = src(x,y).

## 2017-09-03
## 开始项目 javacv examples
    使用 javacv 版本 1.3.3 + java 1.8 + opencv 3.2.0
### 图片和格式转换
    1.提供给 cvLoadImage() 的路径中不能包含非ASCII字符
    2.加载图片到 IplImage,转换到 java 2d BufferedImage 在 JFrame 中展示
    3.加载图片到 Mat,转换到 java 2d BufferedImage 在 JFrame 中展示
    