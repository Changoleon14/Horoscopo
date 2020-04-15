package com.example.horoscopo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horoscopo.estruc.Datos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Datos datos = new Datos();
    private EditText etNombre, etAP1, etAp2;
    private ImageButton btn;

    MediaPlayer mp, beep;


    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this,R.raw.digitallove);
        mp.start();

        etNombre = findViewById(R.id.etNombre);
        etAP1 = findViewById(R.id.etAp1);
        etAp2 = findViewById(R.id.etAp2);
        mDisplayDate = findViewById(R.id.tvdate);
        btn = findViewById(R.id.btn);



        mDisplayDate.setOnClickListener(this);
        btn.setOnClickListener(this);


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month++;
                Log.d("MainActivity", "OnDateSet: dd/mm/yyyy:" + dayOfMonth + "/" + month + "/" + year  );
                mDisplayDate.setError(null);

                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);

                Calendar cal = Calendar.getInstance();
                cal.set(year,month-1,dayOfMonth);
                datos.setCal(cal);
            }
        };


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvdate:
                int year, mes, dia;
                CharSequence fecha = mDisplayDate.getText();
                Calendar cal = Calendar.getInstance();

                if (fecha.length()==0){

                    year = cal.get(Calendar.YEAR);
                    mes = cal.get(Calendar.MONTH);
                    dia = cal.get(Calendar.DAY_OF_MONTH);
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = null;
                    try {
                        date = sdf.parse(fecha.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    cal.setTime(date);
                    String fecha2=fecha.toString();
                    year = cal.get(Calendar.YEAR);
                    mes = cal.get(Calendar.MONTH);
                    dia = cal.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, mes, dia);

                Calendar cal2 = Calendar.getInstance();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(cal2.getTimeInMillis());
                dialog.show();
                break;
            case R.id.btn:
                beep = MediaPlayer.create(this,R.raw.buton);
                beep.start();
                if (etNombre.getText().length() < 2
                        || etAP1.getText().length() < 2
                        || etAp2.getText().length() < 2
                        || mDisplayDate.getText().length()< 2){
                    Toast.makeText(MainActivity.this, R.string.validacion1, Toast.LENGTH_LONG).show();
                    if (etNombre.getText().length() == 0) etNombre.setError(getText(R.string.Reqer));
                    if (etAP1.getText().length() == 0) etAP1.setError(getText(R.string.Reqer));
                    if (etAp2.getText().length() == 0) etAp2.setError(getText(R.string.Reqer));
                    if (mDisplayDate.getText().length() == 0) mDisplayDate.setError(getText(R.string.Reqer));
                    if (etNombre.getText().length() == 1) etNombre.setError(getText(R.string.txtcorto));
                    if (etAP1.getText().length() == 1) etAP1.setError(getText(R.string.txtcorto));
                    if (etAp2.getText().length() == 1) etAp2.setError(getText(R.string.txtcorto));
                }
                else{
                    datos.setNombre(etNombre.getText().toString());
                    datos.setApellido1(etAP1.getText().toString());
                    datos.setApellido2(etAp2.getText().toString());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info_cliente", datos);
                    Intent intent = new Intent(MainActivity.this,Resultados.class);
                    intent.putExtras(bundle);
                    startActivity(intent);


                }
                break;
            default:
                break;
        }
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
