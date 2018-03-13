package com.coco.cococam;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.coco.cococam.R.id.iv;

public class MainActivity extends AppCompatActivity {
    private static int REQ_1 = 1;
    private static int REQ_2 = 2;
    private ImageView mImageView;
    private String mFilePath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.text);
        mImageView = (ImageView) findViewById(iv);
        mImageView.setImageBitmap(bitmap);
        mFilePath = Environment.getExternalStorageDirectory().getPath();
        mFilePath = mFilePath+"/"+"DCIM"+"/"+"cococam";
        //mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CocoCam/";
        mFilePath = mFilePath +"/"+ System.currentTimeMillis()+".jpg";
    }

/*  public void startCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQ_1);
    }
*/

    public void startCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = Uri.fromFile(new File(mFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);//指定的uri
        startActivityForResult(intent, REQ_1);
    }
    public void startImage(View view){
        /*在Activity Action里面有一个“ACTION_GET_CONTENT”字符串常量，
// 该常量让用户选择特定类型的数据，并返回该数据的URI.我们利用该常量，
//然后设置类型为“image/*”，就可获得Android手机内的所有image。*/
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(intent,REQ_2 );


    }

    public void btnPrimaryColor(View view){
        startActivity(new Intent(this,PrimaryColor.class));
        // startActivity(new Intent(this.PrimaryColor.class));
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            /*if(requestCode == REQ_1){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap)bundle.get("data");
                mImageView.setImageBitmap(bitmap);
            }*/
            if (requestCode == REQ_1) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(mFilePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    mImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }if(requestCode == REQ_2){
                Uri uri = data.getData();
                Log.e("uri", uri.toString());
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
        /* 将Bitmap设定到ImageView */
                    mImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(), e);
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

