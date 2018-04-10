package com.example.pc_b_013.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    RelativeLayout l;
    Snackbar snak;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l = findViewById(R.id.milayout);
        boton =  findViewById(R.id.button2);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
            }
        });
        boton.setEnabled(false);
        if(verificarPermisos())
        {
            cargarApp();
        }
        else
        {
            solicitarPermisos();
        }
    }

    private void solicitarPermisos() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, ACCESS_COARSE_LOCATION))
        {
            snak = Snackbar.make(l,"Falto otorgar permisos",Snackbar.LENGTH_INDEFINITE);
            snak.setAction("solicitar", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            ACCESS_FINE_LOCATION,
                            ACCESS_COARSE_LOCATION
                    },100);
                }
            });
            snak.show();
        }
        else
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION
            },100);
        }
    }

    private boolean verificarPermisos() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(MainActivity.this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        return false;
    }

    private void cargarApp() {
        boton.setEnabled(true);
        snak = Snackbar.make(l,"La aplicacion esta lista para iniciar",Snackbar.LENGTH_LONG);
        snak.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 100:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED)
                {
                    snak = Snackbar.make(l,"La aplicacion esta lista para iniciar",Snackbar.LENGTH_LONG);
                    snak.show();
                    boton.setEnabled(true);
                }
                else
                {
                    snak = Snackbar.make(l,"Falto otorgar permisos",Snackbar.LENGTH_INDEFINITE);
                    snak.setAction("solicitar", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                                    ACCESS_FINE_LOCATION,
                                    ACCESS_COARSE_LOCATION
                            },100);
                        }
                    });
                    snak.show();
                }
                break;
            default:
                break;
        }
    }
}
