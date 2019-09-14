package com.adnaira.naira_ad;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    Dialog mDialog;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ImageView mImageView;
    private ImageView mImageView2;
    private String mId;
    private String mIp;
    private String mPhoto;
    private String mTargetUrl;
    private String mDescription;
    private TextView tv;

    public BlankFragment() {
        // Required empty public constructor
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



        mDialog = new Dialog(getContext());
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
