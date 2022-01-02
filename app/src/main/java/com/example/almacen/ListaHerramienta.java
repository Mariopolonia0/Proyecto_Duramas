package com.example.almacen;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.almacen.conexion_api.MaterialApi;
import com.example.almacen.conexion_api.Materials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaHerramienta extends AppCompatActivity {

    private ListView listViewlistadeherramientas;
    ArrayList <Herramienta> ListaHerramientas;
    List<Materials> materials ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_herramienta);
        Toast.makeText(this, "Bienvenido A La Lista De Herramientas", Toast.LENGTH_SHORT).show();
        //Consultarherramientas();
        //aqui se desclara la listView y se le enlazan con la clase Adacter y se le pasa la lista de la clase equipo
        listViewlistadeherramientas = (ListView) findViewById(R.id.listViewListadeHerramienta);
        start();

    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.apiduramas.somee.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MaterialApi materialApi = retrofit.create(MaterialApi.class);

        Call<List<Materials>> material = materialApi.getMateriales();

        material.enqueue(new Callback<List<Materials>>() {
            @Override
            public void onResponse(Call<List<Materials>> call, Response<List<Materials>> response) {
                if(response.isSuccessful()) {
                    materials = response.body();
                    listViewlistadeherramientas.setAdapter(new AdactadorHerramienta(getBaseContext(), materials));
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Materials>> call, Throwable t) {

                t.printStackTrace();
                //Consultarherramientas();
               // listViewlistadeherramientas.setAdapter(new AdactadorHerramienta(getBaseContext(), ));
            }
        });
    }

    public void Consultarherramientas() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "AdminDatabase", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        Herramienta herramienta = null;
        ListaHerramientas = new ArrayList<Herramienta>();

        Cursor cursor = DataBase.rawQuery("select * From Herramientas", null);

        while (cursor.moveToNext()) {
            herramienta = new Herramienta();
            herramienta.setCodigoHerramienta(cursor.getInt(0));
            herramienta.setDescripcionHerramienta(cursor.getString(1));
            herramienta.setCantidadHerramienta(cursor.getInt(2));

            ListaHerramientas.add(herramienta);

        }
    }
}