package com.kairos.android.example;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kairos.Kairos;
import com.kairos.KairosListener;
import com.rahul.media.main.MediaFactory;
import com.rahul.media.model.Define;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MyActivity extends Activity implements KairosListener {

    private MediaFactory.MediaBuilder mediaBuilder;
    private MediaFactory mediaFactory;
    private Kairos myKairos = new Kairos();


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Define.MEDIA_PROVIDER = getString(R.string.image_provider);
        findViewById(R.id.image_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaBuilder = new MediaFactory.MediaBuilder(MyActivity.this)
                        .isSquareCrop(false)
                        .fromCamera();
                mediaFactory = MediaFactory.create().start(mediaBuilder);
            }
        });
        Define.ACTIONBAR_COLOR = getResources().getColor(R.color.color_303030);
        enrollImage("");


        // listener

        /* * * instantiate a new kairos instance * * */
        /* * * set authentication * * */
        String app_id = "c7d15241";
        String api_key = "fd3287889f836397be1857dd4d0adb11";
        myKairos.setAuthentication(this, app_id, api_key);


        try {


            /* * * * * * * * * * * * * * * * * * * * */
            /* * *  Kairos Method Call Examples * * */
            /* * * * * * * * * * * * * * * * * * * */
            /* * * * * * * * * * * * * * * * * * **/
            /* * * * * * * * * * * * * * * * * * */
            /* * * * * * * * * * * * * * * * * **/
            /* * * * * * * * * * * * * * * * * */
            /* * * * * * * * * * * * * * * * **/
            /* * * * * * * * * * * * * * * * */


            //  List galleries
            myKairos.listGalleries(this);
         /*   Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.download);
            String image1 = "http://media.kairos.com/liz.jpg";
            myKairos.detect(image2, null, null, this);*/



            /* * * * * * * * DETECT EXAMPLES * * * * * * *


            // Bare-essentials Example:
            // This example uses only an image url, setting optional params to null
            String image = "http://media.kairos.com/liz.jpg";
            myKairos.detect(image, null, null, listener);



            // Fine-grained Example:
            // This example uses a bitmap image and also optional parameters
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.liz);
            String selector = "FULL";
            String minHeadScale = "0.25";
            myKairos.detect(image, selector, minHeadScale, listener);

            */



            /* * * * * * * * ENROLL EXAMPLES * * * * * * *

            // Bare-essentials Example:
            // This example uses only an image url, setting optional params to null
            String image = "http://media.kairos.com/liz.jpg";
            String subjectId = "Elizabeth";
            String galleryId = "friends";
            myKairos.enroll(image, subjectId, galleryId, null, null, null, listener);


            // Fine-grained Example:
            // This example uses a bitmap image and also optional parameters
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.liz);
            String subjectId = "Elizabeth";
            String galleryId = "friends";
            String selector = "FULL";
            String multipleFaces = "false";
            String minHeadScale = "0.25";
            myKairos.enroll(image,
                    subjectId,
                    galleryId,
                    selector,
                    multipleFaces,
                    minHeadScale,
                    listener);

                    */


//             * * * * * * RECOGNIZE EXAMPLES * * * * * * *

            // Bare-essentials Example:
            // This example uses only an image url, setting optional params to null
           /* String image = "http://media.kairos.com/liz.jpg";
            String galleryId = "friends";
            myKairos.recognize(image, galleryId, null, null, null, null, listener);*/


            // Fine-grained Example:
            // This example uses a bitmap image and also optional parameters
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.liz);
            String galleryId = "Download";
            String selector = "FULL";
            String threshold = "0.75";
            String minHeadScale = "0.25";
            String maxNumResults = "25";
            myKairos.recognize(image,
                    galleryId,
                    selector,
                    threshold,
                    minHeadScale,
                    maxNumResults,
                    this);
            myKairos.listGalleries(this);




            /* * * * GALLERY-MANAGEMENT EXAMPLES * * * *


            //  List galleries
            myKairos.listGalleries(listener);



            //  List subjects in gallery
            myKairos.listSubjectsForGallery("your_gallery_name", listener);



            // Delete subject from gallery
            myKairos.deleteSubject("your_subject_id", "your_gallery_name", listener);



            // Delete an entire gallery
            myKairos.deleteGallery("your_gallery_name", listener);

            */


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private void enrollImage(String imageUrl) {
        String image = "http://media.kairos.com/liz.jpg";
        String subjectId = "Test 1";
        String galleryId = "Test_Face_Images";
        try {
            myKairos.enroll(image, subjectId, galleryId, null, null, null, this);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String response) {
        Log.d("KAIROS DEMO", response);
        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String response) {
        Log.d("KAIROS DEMO", response);
        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
        ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> pathArrayList = mediaFactory.onActivityResult(requestCode, resultCode, data);
        enrollImage(pathArrayList.get(0));

    }
}