package com.example.najdaapp.serviceFall;

import static android.content.Context.POWER_SERVICE;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.najdaapp.R;

public class FallFragment extends Fragment {

    private FallViewModel mViewModel;

    public static FallFragment newInstance() {
        return new FallFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       final View root=inflater.inflate(R.layout.fall_fragment, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS}, 100);
            }
        }

        // this is a special permission required only by devices using
        // Android Q and above. The Access Background Permission is responsible
        // for populating the dialog with "ALLOW ALL THE TIME" option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 100);
        }

        // check for BatteryOptimization,
        PowerManager pm = (PowerManager) requireActivity().getSystemService(POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (pm != null && !pm.isIgnoringBatteryOptimizations(requireActivity().getPackageName())) {
//                requireActivity().askIgnoreOptimization();
            }
        }

        final ImageView imagen = (ImageView)root.findViewById(R.id.select);
        final TextView msg= root.findViewById(R.id.message);
        final Button stop= root.findViewById(R.id.stop);
        imagen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), OnfallService.class);
                requireActivity().startService(i);
                Toast.makeText(getContext(), "Fall service is started", Toast.LENGTH_SHORT).show();
               msg.setText("Fall service is started");
               stop.setVisibility(1);

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                Intent myService = new Intent(getContext(), OnfallService.class);
                //startService(myService);
                requireActivity().stopService(myService);
                Toast.makeText(getContext(), "Fall service is Stoped", Toast.LENGTH_SHORT).show();
                msg.setText(getString(R.string.activate_fall));
            stop.setVisibility(-1);
            }
        });

       return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FallViewModel.class);
        // TODO: Use the ViewModel
    }

}