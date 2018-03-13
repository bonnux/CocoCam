package com.coco.cococam;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by 53592 on 2017/12/28.
 */

public class ImageHelper {

    public static Bitmap handleImageEffect(Bitmap bm,float hue,float saturation,float lum){
        //bm 要处理的图像 后三个是要分别调整的属性值
        Bitmap bmp =Bitmap.createBitmap(bm.getWidth(),bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas =new Canvas(bmp);//画布
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0,hue);
        hueMatrix.setRotate(1,hue);
        hueMatrix.setRotate(2,hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix =new ColorMatrix();
        lumMatrix.setScale(lum,lum,lum,1);

        ColorMatrix imageMatrix =new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat( saturationMatrix);
        imageMatrix.postConcat( lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm,0,0,paint);//paint包含3个属性 绘制的画布


        return bmp;
    }
}
