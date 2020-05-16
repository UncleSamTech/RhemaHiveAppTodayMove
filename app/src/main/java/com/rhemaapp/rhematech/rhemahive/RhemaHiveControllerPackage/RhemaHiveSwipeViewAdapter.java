package com.rhemaapp.rhematech.rhemahive.RhemaHiveControllerPackage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rhemaapp.rhematech.rhemahive.R;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveConstantsPackage.RhemaHiveClassReferenceConstants;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveAutoUtilsClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveUtilsPackage.RhemaHiveInstanceManagerClass;
import com.rhemaapp.rhematech.rhemahive.RhemaHiveViewPackage.RhemaPhoneVerFragment;

public class RhemaHiveSwipeViewAdapter extends FragmentPagerAdapter {

    public RhemaHiveSwipeViewAdapter(@NonNull FragmentManager fm) {
       super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position<3){
            return ImageFragment.newInstance(position);
        }
        else{
           return new RhemaPhoneVerFragment();
        }

    }

    @Override
    public int getCount() {
        return RhemaHiveClassReferenceConstants.NUM_ITEMS;
    }

    public static class ImageFragment extends Fragment{
        private int imgPos;

        public ImageFragment() {
        }

        public RhemaHiveAutoUtilsClass getRhemaHiveAutoUtilsClass(){
            RhemaHiveAutoUtilsClass rhemaHiveAutoUtilsClass = RhemaHiveInstanceManagerClass.getRhemaHiveAutoUtilsClass();
            return rhemaHiveAutoUtilsClass;
        }


        public static ImageFragment newInstance(int pos){
            ImageFragment fragment = new ImageFragment();
            Bundle args = new Bundle();
            args.putInt("pos", pos);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try{
            imgPos = getArguments().getInt("pos");
            }catch(NullPointerException np){
                Log.e("SwipeViewClass","Error as a result of : " + np.getLocalizedMessage());
            }
        }


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.swipe_img_lay,container,false);
            ImageView imageView = v.findViewById(R.id.swipe1);
            switch (imgPos){
                case 1:
                    imageView.setImageResource(R.drawable.rhema_logg);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.img_loggo);
                    break;
                case 0:
                default:
                        imageView.setImageResource(R.drawable.f_screen);
                        break;


            }
            return v;
        }


    }



}
