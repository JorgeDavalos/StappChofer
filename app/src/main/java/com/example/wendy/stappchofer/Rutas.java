package com.example.wendy.stappchofer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Rutas extends AppCompatActivity {
    Button aceptar;
    Spinner spinnerRutas;
    String[] arregloRutas;
    EditText NumeroCamion;
    TextView Resultado;
    ArrayList<String> a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rutas);
        NumeroCamion=(EditText)findViewById(R.id.etNumeroCamion);
        Resultado=(TextView)findViewById(R.id.tvResultado);
        aceptar=(Button)findViewById(R.id.aceptar);
        spinnerRutas=(Spinner)findViewById(R.id.spinnerRuta);
        arregloRutas=getResources().getStringArray(R.array.arregloNombreRuta);

        ArrayAdapter adaptador= new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arregloRutas);
        spinnerRutas.setAdapter(adaptador);
        final AsyncHttpClient varPHP = new AsyncHttpClient ();
        final String URL = "http://www.stappapp.esy.es/JalarRutas.php";
        final RequestParams rp = new RequestParams();
        a= new ArrayList<String>();
        String b;


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                varPHP.post(URL, rp, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String res = new String(responseBody);
                        Log.i("mio", res);
                        JSONObject obj= null;
                        Toast.makeText(Rutas.this, res, Toast.LENGTH_LONG).show();
                        //Resultado.setText(res);
                        //try {
                            //JSONArray jarr = new JSONArray(res);
                            //obj = (JSONObject)jarr.getJSONObject(0);

                            //String m = obj.getString("NombreRuta");
                            Resultado.setText(res);
                        /*} catch (JSONException e) {
                            e.printStackTrace();
                        }*/


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(Rutas.this, "Conexion Fallida. Reintente mas tarde", Toast.LENGTH_SHORT).show();
                        String res = new String(responseBody);

                    }
                });
            }
        });
    }
}