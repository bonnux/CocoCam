package com.coco.cococam;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

public class PrimaryColor extends Activity implements SeekBar.OnSeekBarChangeListener{

    private ImageView mImageView;
    private SeekBar mSeekbarhue, mSeekSaturation, mSeekbarLum;
    private static int MAX_VALUE=255;
    private static int MID_VALUE=127;
    private float mHue, mSaturation, mLum;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.primary_color);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.text);

        mImageView = (ImageView) findViewById(R.id.imageview);

        mSeekbarhue = (SeekBar) findViewById(R.id.seekbarHue);
        mSeekSaturation = (SeekBar) findViewById(R.id.seekbarSaturation);
        mSeekbarLum = (SeekBar) findViewById(R.id.seekbarLum);

        mSeekbarhue.setOnSeekBarChangeListener(this);
        mSeekSaturation.setOnSeekBarChangeListener(this);
        mSeekbarLum.setOnSeekBarChangeListener(this);

        mSeekbarhue.setMax(MAX_VALUE);
        mSeekSaturation.setMax(MAX_VALUE);
        mSeekbarLum.setMax(MAX_VALUE);

        mSeekbarhue.setProgress(MID_VALUE);
        mSeekSaturation.setProgress(MID_VALUE);
        mSeekbarLum.setProgress(MID_VALUE);

        mImageView.setImageBitmap(bitmap);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        switch (seekBar.getId()){
            case R.id.seekbarHue:
                mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE * 100;
                break;
            case R.id.seekbarSaturation:
                mSaturation = progress * 1.0F / MID_VALUE;
                break;
            case R.id.seekbarLum:
                mLum = progress * 1.0F / MID_VALUE;
                break;
        }
        mImageView.setImageBitmap(ImageHelper.handleImageEffect(bitmap,mHue,mSaturation,mLum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar){

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar){

    }
}
