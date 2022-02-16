package com.example.najdaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;



        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }
    //Array to store infor of sliders
    public int []sliderimg={
            R.drawable.slide1,R.drawable.slide2,
            R.drawable.slide3
    };
    public String[]titles={
            ""+("Appel instantané des urgences\n"),
            String.valueOf("Observation"),
            String.valueOf("localisation aux contacts d’urgence\n"),
    };
    public String[]textes={
            String.valueOf("Appeler les secours n’importe où vous êtes avec un simple geste\n" +
                    "et sans perte de temps."),
            String.valueOf("Soyez en sécurité et conscient de la situation en observant les\n" +
                    "incidents se dérouler et obtenez la vraie histoire.\n"),
            String.valueOf("Message d’alerte comprenant votre localisation partagé à vos\n" +
                    "proches automatiquement"),    };
    LayoutInflater layoutInflater;
    @Override
    public int getCount() {
        return sliderimg.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImgView =(ImageView) view.findViewById(R.id.imageView);
        TextView slideTitle=(TextView) view.findViewById(R.id.title);
        TextView slideText=(TextView) view.findViewById(R.id.text);
        slideImgView.setImageResource(sliderimg[position]);
        slideTitle.setText(titles[position]);
        slideText.setText(textes[position]);
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
