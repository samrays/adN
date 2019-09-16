package com.adnaira.naira_ad;
import android.content.Context;
import android.content.Intent;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Display_Add extends AppCompatActivity {
    private ImageView mImageView;
    private ImageView mImageView2;
    private String mId;
    private String mIp;
    private String mPhoto;
    private String mTargetUrl;
    private String mDescription;
    private TextView tv;
    Dialog mDialog;

    public Display_Add() {
    }

    public void showAds(View v, String token){
        //mId = token;
        mIp = getIPAddress();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://ads.adnaira.ng/mobile-ads/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        Naira_add_Server client = retrofit.create(Naira_add_Server.class);
        Call<AddInfo> call = client.getAddInfo(token,getIPAddress());
        call.enqueue(new Callback<AddInfo>() {
            @Override
            public void onResponse(Call<AddInfo> call, Response<AddInfo> response) {
                Log.d("Sample", response.code()+ "this worked");
                AddInfo addInfo = response.body();
                mPhoto = addInfo.getPhoto();
                mTargetUrl = addInfo.getTarget_url();
                mDescription = addInfo.getDescription();
                mImageView2 = mDialog.findViewById(R.id.image_id);
                tv = mDialog.findViewById(R.id.tv2_id);
                tv.setText(mDescription);

                Picasso.get()
                        .load(mPhoto)
                        .fit()
                        .centerCrop()
                        .into(mImageView2);
                mImageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(mTargetUrl));
                        startActivity(intent);
                    }
                });
                Log.d("Sample", getIPAddress());


            }

            @Override
            public void onFailure(Call<AddInfo> call, Throwable t) {
                Log.d("Sample",  "This didn't work");

            }
        });



        mDialog = new Dialog(Display_Add.this);
        TextView txtclose;
        mDialog.setContentView(R.layout.naira_popup);

        mImageView = mDialog.findViewById(R.id.imageView2);
        Picasso.get()
                .load("https://ads.adnaira.ng/assets/ads/ads-by-adnaira.png")
                .fit()
                .centerCrop()
                .into(mImageView);

        txtclose =(TextView) mDialog.findViewById(R.id.txtclose);



        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://adnaira.ng/"));
                startActivity(intent);
            }
        });





    }
    public String getIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    //Check if the IP address is not a loopback address, in that case it is
                    //the IP address of your mobile device
                    if (!inetAddress.isLoopbackAddress())
                        return Formatter.formatIpAddress(inetAddress.hashCode());


                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
