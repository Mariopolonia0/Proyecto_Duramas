package com.example.almacen;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class ListaHerramienta extends AppCompatActivity {

    private ListView listViewlistadeherramientas;
    ArrayList <Herramienta> ListaHerramientas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_herramienta);
        Toast.makeText(this, "Bienvenido A La Lista De Herramientas", Toast.LENGTH_SHORT).show();
        Consultarherramientas();

        //aqui se desclara la listView y se le enlazan con la clase Adacter y se le pasa la lista de la clase equipo
        listViewlistadeherramientas = (ListView) findViewById(R.id.listViewListadeHerramienta);
        listViewlistadeherramientas.setAdapter(new AdactadorHerramienta(this, ListaHerramientas));
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