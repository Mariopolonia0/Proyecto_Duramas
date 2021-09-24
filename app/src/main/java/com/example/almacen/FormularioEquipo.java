package com.example.almacen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class FormularioEquipo extends AppCompatActivity {

    private Spinner spinnerTipoEquipo;
    private EditText CodigoEditText;
    private EditText ClienteEditText;
    private EditText MarcaEditText;
    private EditText ModeloEditText;
    private EditText ProblemaEquipoEditText;
    private EditText FechaEntradaEditText;
    private EditText ComentarioTecnicoEditText;
    private EditText ComentarioClienteEditText;
    private EditText NumeroTelefonoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);

        Toast.makeText(  this, "Abriendo El Editor De Equipo", Toast.LENGTH_SHORT).show();
        spinnerTipoEquipo = (Spinner)findViewById(R.id.spinnerTipoEquipo);
        //spinner del selecion de tipo de equipo
        String [] Opciones ={"Laptop","Celular","Tablet","Control","Radio","Otro"};
        ArrayAdapter <String> adtater =new ArrayAdapter<String>(this,R.layout.spinne_edicion,Opciones);
        spinnerTipoEquipo.setAdapter(adtater);

        CodigoEditText = (EditText)findViewById(R.id.editTextNumberCodigo);
        CodigoEditText.setText(OtenerUltimoRegistro());
        ClienteEditText = (EditText)findViewById(R.id.editTextCliente);
        MarcaEditText = (EditText)findViewById(R.id.editTextMarca);
        ModeloEditText = (EditText)findViewById(R.id.editTextModelo);
        ProblemaEquipoEditText = (EditText)findViewById(R.id.editTextProblemaEquipo);
        FechaEntradaEditText = (EditText) findViewById(R.id.DateFechaEntrada);
        ComentarioTecnicoEditText = (EditText)findViewById(R.id.editTextComentarioTecnico);
        ComentarioClienteEditText = (EditText)findViewById(R.id.editTextComentarioCliente);
        NumeroTelefonoEditText =(EditText)findViewById(R.id.editTextNumeroTelefono);

    }


    //metodo para guardar en la base de dato
    public void GuardarEquipo(View view){
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String Codigo = CodigoEditText.getText().toString();
        String Cliente = ClienteEditText.getText().toString();
        String Marca = MarcaEditText.getText().toString();
        String Modelo = ModeloEditText.getText().toString();
        String TipoEquipo = spinnerTipoEquipo.getSelectedItem().toString();
        String ProblemaEquipo = ProblemaEquipoEditText.getText().toString();
        String FechaEntrada = FechaEntradaEditText.getText().toString();
        String ComentarioTecnico = ComentarioTecnicoEditText.getText().toString();
        String ComentarioCliente = ComentarioClienteEditText.getText().toString();
        String NumeroTelefono = NumeroTelefonoEditText.getText().toString();

        if (!Codigo.isEmpty() && !Cliente.isEmpty() && !Marca.isEmpty() && !Modelo.isEmpty() && !ProblemaEquipo.isEmpty() && !FechaEntrada.isEmpty()
                && !ComentarioTecnico.isEmpty() && !ComentarioCliente.isEmpty() && !NumeroTelefono.isEmpty() ) {

            ContentValues registro = new ContentValues();
            registro.put("Codigo",Codigo);
            registro.put("Cliente",Cliente);
            registro.put("Marca",Marca);
            registro.put("Modelo",Modelo);
            registro.put("TipoEquipo",TipoEquipo);
            registro.put("ProblemaEquipo",ProblemaEquipo);
            registro.put("FechaEntrada",FechaEntrada);
            registro.put("ComentarioTecnico",ComentarioTecnico);
            registro.put("ComentarioCliente",ComentarioCliente);
            registro.put("NumeroTelefono",NumeroTelefono);

            if( DataBase.insert("Equipo",null,registro)<0)
            {
                AlertDialog.Builder alerta = new AlertDialog.Builder(FormularioEquipo.this);
                alerta.setMessage("NO SE GUARDO EL REGISTRO\nEL CODIGO YA EXISTE").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta... Lee el Mensaje");
                titulo.show();
                return;
            }

            DataBase.close();
            Limpiar(null);
            VentanaAlerta();

        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(FormularioEquipo.this);
            alerta.setMessage("Hay Campos Vacio\nNo Se puede Guardar").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta... Lee el Mensaje");
            titulo.show();
        }

    }

    public void EliminarEquipo(View view){

        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String Codigo = CodigoEditText.getText().toString();
        String Cliente = ClienteEditText.getText().toString();

        if(!Codigo.isEmpty() && !Cliente.isEmpty()){
            int cantidad = DataBase.delete("Equipo","Codigo ="+Codigo,null);

            if(cantidad == 1){
                AlertDialog.Builder alerta = new AlertDialog.Builder(FormularioEquipo.this);
                alerta.setMessage("Se Elimino Con Exito El Equipo : " +Codigo).setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta... Lee el Mensaje");
                titulo.show();
                Limpiar(null);
            }else{
                AlertDialog.Builder alerta = new AlertDialog.Builder(FormularioEquipo.this);
                alerta.setMessage("No Existe El Equipo : "+ Codigo).setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta... Lee el Mensaje");
                titulo.show();
            }

        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(FormularioEquipo.this);
            alerta.setMessage("El Codigo Y Cliente Esta Vacio ").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta... Lee el Mensaje");
            titulo.show();
        }

    }

    //metodo Buscar
    public void BuscarCodigo(View view){
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String codigo =  CodigoEditText.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = DataBase.rawQuery("select Cliente,Marca,Modelo,TipoEquipo,ProblemaEquipo,FechaEntrada,ComentarioTecnico,ComentarioCliente,NumeroTelefono  from Equipo where Codigo ="+codigo,null);
            //Cliente Marca Modelo TipoEquipo ProblemaEquipo FechaEntrada ComentarioTecnico ComentarioCliente  NumeroTelefono
            if(fila.moveToFirst()){
                ClienteEditText.setText(fila.getString(0));
                MarcaEditText.setText(fila.getString(1));
                ModeloEditText.setText(fila.getString(2));
                //switch para llenar speener
                switch (fila.getString(3)){

                    case "Laptop": spinnerTipoEquipo.setSelection(0);break;
                    case "Celular" : spinnerTipoEquipo.setSelection(1);break;
                    case "Tablet" : spinnerTipoEquipo.setSelection(2);break;
                    case "Control" : spinnerTipoEquipo.setSelection(3);break;
                    case "Radio" : spinnerTipoEquipo.setSelection(4);break;
                    case "Otro" : spinnerTipoEquipo.setSelection(5);break;
                }
                ProblemaEquipoEditText.setText(fila.getString(4));
                FechaEntradaEditText.setText(fila.getString(5));
                ComentarioTecnicoEditText.setText(fila.getString(6));
                ComentarioClienteEditText.setText(fila.getString(7));
                NumeroTelefonoEditText.setText(fila.getString(8));
                DataBase.close();
                Toast.makeText(this,"Equipo Encotrado Perfectamente", Toast.LENGTH_SHORT ).show();
            }else{
                Toast.makeText(this,"No Existe El Equipo", Toast.LENGTH_SHORT ).show();
                DataBase.close();
            }
        }else{
            //dialogo de alerta
            AlertDialog.Builder alerta = new AlertDialog.Builder( FormularioEquipo.this);
            alerta.setMessage("Codigo Equipo\nEsta Vacio").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
        }
    }

    public void Limpiar(View view){
        CodigoEditText.setText("");
        ClienteEditText.setText("");
        MarcaEditText.setText("");
        ModeloEditText.setText("");
        spinnerTipoEquipo.setSelection(0);
        ProblemaEquipoEditText.setText("");
        FechaEntradaEditText.setText("");
        ComentarioTecnicoEditText.setText("");
        ComentarioClienteEditText.setText("");
        NumeroTelefonoEditText.setText("");
        //cambiar el foco al edit text codigo
        CodigoEditText.requestFocus();
        Toast.makeText(this,"Se Limpio La Pantalla", Toast.LENGTH_LONG ).show();
    }

    public void VentanaAlerta(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(FormularioEquipo.this);
        alerta.setMessage("Registro Guardado Con Exito").setCancelable(true);
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Alerta. Lee el Mensaje");
        titulo.show();
    }

    public void VentanaAlertaError(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(FormularioEquipo.this);
        alerta.setMessage("No Se Guardado El Registro\nRevise Los Campo").setCancelable(true);
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Alerta. Lee el Mensaje");
        titulo.show();
    }

    public void ListaEquipo(View view){
        Intent VentanaListaRegistros = new Intent(this,ListaDeEquipos.class);
        startActivity( VentanaListaRegistros);
    }


    public void BuscarNombre(View view){
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String nombre =  ClienteEditText.getText().toString();

        if (!nombre.isEmpty()) {
            Cursor fila = DataBase.rawQuery("select Codigo,Marca,Modelo,TipoEquipo,ProblemaEquipo,FechaEntrada,ComentarioTecnico,ComentarioCliente,NumeroTelefono from Equipo where Cliente ='" + nombre+"'", null);

            if (fila.moveToFirst()) {
                CodigoEditText.setText(fila.getString(0));
                MarcaEditText.setText(fila.getString(1));
                ModeloEditText.setText(fila.getString(2));
                switch (fila.getString(3)){

                    case "Laptop": spinnerTipoEquipo.setSelection(0);break;
                    case "Celular" : spinnerTipoEquipo.setSelection(1);break;
                    case "Tablet" : spinnerTipoEquipo.setSelection(2);break;
                    case "Control" : spinnerTipoEquipo.setSelection(3);break;
                    case "Radio" : spinnerTipoEquipo.setSelection(4);break;
                    case "Otro" : spinnerTipoEquipo.setSelection(5);break;
                }
                ProblemaEquipoEditText.setText(fila.getString(4));
                FechaEntradaEditText.setText(fila.getString(5));
                ComentarioTecnicoEditText.setText(fila.getString(6));
                ComentarioClienteEditText.setText(fila.getString(7));
                NumeroTelefonoEditText.setText(fila.getString(8));
                DataBase.close();
            } else {
                Toast.makeText(this, "Cliente No Existe :(", Toast.LENGTH_SHORT).show();
                DataBase.close();
            }
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder( FormularioEquipo.this);
            alerta.setMessage("Nombre Equipo\nEsta Vacio").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
        }
    }

    public void EntregarEquipo(View view){
        Intent EntregarEquipo = new Intent(this,EntregarEquipo.class);
        startActivity(EntregarEquipo);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void buscarFechaHoy(View view){
        FechaEntradaEditText.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
    }

    public String OtenerUltimoRegistro(){

        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();
        String salida = "0";
        //la conslta buscar codigo del registro mas grande y le suma uno
        Cursor fila = DataBase.rawQuery("select MAX(Codigo) from Equipo",null);
        //la funcion movetofirst comprueba que la consulta devolcio dato
        if(fila.moveToFirst()){
            salida= String.valueOf(fila.getInt(0)+1);
        };
        DataBase.close();
        return salida;
    }

}
