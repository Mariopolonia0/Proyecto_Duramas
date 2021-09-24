package com.example.almacen;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class ListaDeEquipos extends AppCompatActivity {

    private ListView listViewlistadeequipos;
    ArrayList<Equipo> ListaEquipos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_equipos);
        Toast.makeText(  this, "Bienvenido A La Lista De Equipo", Toast.LENGTH_SHORT).show();
        ConsultarEquipos();

        //aqui se desclara la listView y se le enlazan con la clase Adacter y se le pasa la lista de la clase equipo
        listViewlistadeequipos = (ListView) findViewById(R.id.listViewListadeEquipos);
        listViewlistadeequipos.setAdapter(new Adactador(this,ListaEquipos));
    }
    //aqui llenamos la lista de la clase que esta en la base de dato y la lista resultante se agregara al textview
    private void ConsultarEquipos() {

        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        Equipo equipo=null;
        ListaEquipos =new ArrayList<Equipo>();

        Cursor cursor = DataBase.rawQuery("select Codigo,Cliente,Marca,TipoEquipo,NumeroTelefono From Equipo",null);

        while(cursor.moveToNext()){
            equipo = new Equipo();
            equipo.setCodigo(cursor.getString(0));
            equipo.setCliente(cursor.getString(1));
            equipo.setMarca(cursor.getString(2));
            equipo.setTipoEquipo(cursor.getString(3));
            equipo.setTelefono(cursor.getString(4));

            ListaEquipos.add(equipo);
        }
    }
    //esta clase hace una lista sin personalisar osea que solo funciona para un xml como el del speener
    //y no se esta usando
    /*
    private void obtenerLista() {
        ListaInformacion = new ArrayList<String>();

        for(int i=0; i<ListaEquipos.size();i++){
            ListaInformacion.add("\t"+ListaEquipos.get(i).getCodigo()+"\t\t\t\t\t\t"+ListaEquipos.get(i).getCliente()
            +"\t\t"+ListaEquipos.get(i).getTipoEquipo());
        }
    }*/
}

