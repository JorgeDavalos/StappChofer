package com.example.wendy.stappchofer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Sesion extends AppCompatActivity {
    Button btnEntrar;
    TextView Registarse,Conectarse;
    EditText Usuario,Password;
    RequestParams registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sesion);
        btnEntrar=(Button)findViewById(R.id.SbtnEntrar);
        Usuario=(EditText)findViewById(R.id.RtxtUsuario);
        Password=(EditText)findViewById(R.id.RtxtPassword);
        Registarse=(TextView)findViewById(R.id.StexRegistrate);


        final AsyncHttpClient Servidor = new AsyncHttpClient ();
        final String URL = "http://www.stappapp.esy.es/IniciarSesionF.php";
        final RequestParams registrar = new RequestParams();

        Registarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mandando a vista de registro", Toast.LENGTH_SHORT).show();
                Intent b = new Intent(Sesion.this, RegistroUsuarios.class);
                startActivity(b);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Usuario.getText().toString().equals("")||Usuario.getText().toString().equals(null)){
                    Toast.makeText(getApplicationContext(), "Ingrese Usuario", Toast.LENGTH_SHORT).show();
                }else{
                    if(Password.getText().toString().equals("")||Password.getText().toString().equals(null)){
                        Toast.makeText(getApplicationContext(), "Ingrese Password", Toast.LENGTH_SHORT).show();
                    } else{
                        registrar.put("usuario",Usuario.getText().toString());
                        Servidor.post(URL, registrar, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String res = new String(responseBody);
                                //Toast.makeText(Sesion.this, res, Toast.LENGTH_SHORT).show();
                                //Conectarse.setText(res);
                                JSONObject obj= null;
                                try {
                                    obj = new JSONObject(res);
                                    String m = obj.optString("Usuario").toString();

                                    if(Usuario.getText().toString().equals(m)){
                                        String n = obj.optString("Password").toString();
                                        if(Password.getText().toString().equals(n)){
                                            Toast.makeText(Sesion.this,"Iniciando sesion", Toast.LENGTH_SHORT).show();
                                           // Toast.makeText(getApplicationContext(), "Mandando a vista de registro", Toast.LENGTH_SHORT).show();
                                            Intent b = new Intent(Sesion.this, Rutas.class);
                                            startActivity(b);
                                        }else{
                                            Toast.makeText(Sesion.this, "Password Incorrecta", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "El Usuario no existe.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(Sesion.this, "Conexion Fallida. Reintente mas tarde", Toast.LENGTH_SHORT).show();
                                String res = new String(responseBody);
                                Conectarse.setText(res);
                            }
                        });
                    }
                }
            }
        });
    }
}

