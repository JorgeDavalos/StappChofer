package com.example.wendy.stappchofer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistroUsuarios extends AppCompatActivity {
    Button RbtnGuardar;
    EditText RtxtNombre,RtxtApellido,RtxtUsuario,RtxtPassword,RtxtPasswordRe;
    RequestParams registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registro_usuarios);

        RbtnGuardar=(Button)findViewById(R.id.RbtnGuardar);
        RtxtNombre=(EditText)findViewById(R.id.RtxtNombre);
        RtxtApellido=(EditText)findViewById(R.id.RtxtApellido);
        RtxtUsuario=(EditText)findViewById(R.id.RtxtUsuario);
        RtxtPassword=(EditText)findViewById(R.id.RtxtPassword);
        RtxtPasswordRe=(EditText)findViewById(R.id.RtxtPassR);
        final AsyncHttpClient Servidor = new AsyncHttpClient ();
        final String URL = "http://www.stappapp.esy.es/RegistrarUsuariosF.php";
        registrar = new RequestParams();

        RbtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RtxtNombre.getText().toString().equals("") || RtxtNombre.getText().toString().equals(null)||
                    RtxtApellido.getText().toString().equals("") || RtxtApellido.getText().toString().equals(null)||
                    RtxtUsuario.getText().toString().equals("") || RtxtUsuario.getText().toString().equals(null)||
                    RtxtPassword.getText().toString().equals("") || RtxtPassword.getText().toString().equals(null)||
                    RtxtPasswordRe.getText().toString().equals("") || RtxtPasswordRe.getText().toString().equals(null)) {
                        Toast.makeText(RegistroUsuarios.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                }else {
                    if(RtxtPassword.getText().toString().equals(RtxtPasswordRe.getText().toString())){
                        registrar.put("nombre", RtxtNombre.getText().toString());
                        registrar.put("apellido", RtxtApellido.getText().toString());
                        registrar.put("usuario", RtxtUsuario.getText().toString());
                        registrar.put("password", RtxtPassword.getText().toString());

                        Servidor.post(URL, registrar, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String res = new String(responseBody);
                                try {
                                    JSONObject obj= new JSONObject(res);
                                    String n = obj.optString("success").toString();
                                    int a= Integer.valueOf(n);
                                    if(a==1) {
                                        Toast.makeText(RegistroUsuarios.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(RegistroUsuarios.this, "A Ocurrido un problema.\nIntente mas tarde", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                String res = new String(responseBody);
                                Toast.makeText(RegistroUsuarios.this,res,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(RegistroUsuarios.this, "Las Password no son iguales", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
