package com.example.horoscopo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.horoscopo.estruc.Datos;

public class Resultados extends AppCompatActivity {

    private TextView tvhoros1, tvhoros2, tvchino1, tvchino2, tvtitulo, tvdescripcion;
    private ImageView ivhoros, ivchino ;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        mp = MediaPlayer.create(this,R.raw.scary);
        mp.start();

        Bundle bundle;
        bundle = getIntent().getExtras();
        Datos datos = (Datos) bundle.getSerializable("info_cliente");

        tvhoros1=findViewById(R.id.tvhoros1);
        tvhoros2=findViewById(R.id.tvhoros2);
        tvchino1=findViewById(R.id.tvchino1);
        tvchino2=findViewById(R.id.tvchino2);
        tvtitulo=findViewById(R.id.tvtitulo);
        tvdescripcion=findViewById(R.id.tvdescripcion);
        ivhoros=findViewById(R.id.ivhoros);
        ivchino=findViewById(R.id.ivchino);

        tvtitulo.setText(getString(R.string.saludo)+ datos.getNombre());
        tvdescripcion.setText(getString(R.string.rfc) + datos.getRFC() + getString(R.string.yt) + datos.getAnosCumplidos() + getString(R.string.jov));
        tvhoros1.setText(getString(R.string.hor) + getText(datos.getHoroscopo()) + getString(R.string.tvhoros12) + getText(datos.getConsjeo_horoscopo1()));
        tvhoros2.setText(getText(datos.getConsjeo_horoscopo2()));
        tvchino1.setText(getString(R.string.tvchino1)+ getText(datos.getChinesesign()));
        tvchino2.setText(getString(R.string.tvchino2)+getText(datos.getCaracteristicas_chinas()));
        ivchino.setImageResource(datos.getImg_chino());
        ivhoros.setImageResource(datos.getImg_horoscop());

        Log.d("Resultados", "horoscopo: " + getText(datos.getHoroscopo()) + "\nRFC: " + datos.getRFC() + "\nHORCHINO: " + getText(datos.getChinesesign()) + "\naños cumplidos: " + datos.getAnosCumplidos() );


    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mp.start();
    }
}
