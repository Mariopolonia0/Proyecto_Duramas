package com.example.almacen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AgregarHerramientas extends AppCompatActivity {

    public EditText EditTextCodigoHerramientas ;
    public EditText EditTextDescripcion;
    public EditText EditTextCantidad;
    public EditText EditTextMarca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramientas);

        EditTextCodigoHerramientas = (EditText)findViewById(R.id.editTextCodigoHerramientas);
        EditTextCodigoHerramientas.setText(OtenerUltimoRegistro());
        EditTextDescripcion = (EditText)findViewById(R.id.editTextDescripcionHerramientas);
        EditTextCantidad = (EditText)findViewById(R.id.editTextCantidadHerramientas);
        EditTextMarca = (EditText)findViewById(R.id.editTextMarca);
    }

    public void GuardarHerramientas(View view){

        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String CodigoHerramaientas = EditTextCodigoHerramientas.getText().toString();
        String Descripcion = EditTextDescripcion.getText().toString();
        String Cantidad = EditTextCantidad.getText().toString();
        String Marca = EditTextMarca.getText().toString();

        if (!CodigoHerramaientas.isEmpty() && !Descripcion.isEmpty() && !Cantidad.isEmpty()  ) {

            ContentValues registro = new ContentValues();
            registro.put("CodigoHerramienta",CodigoHerramaientas);
            registro.put("Descripcion",Descripcion);
            registro.put("Cantidad",Cantidad);
            registro.put("Marca",Marca);

            if( DataBase.insert("Herramientas",null,registro)<0)
            {
                AlertDialog.Builder alerta = new AlertDialog.Builder(AgregarHerramientas.this);
                alerta.setMessage("NO SE GUARDO EL REGISTRO\nEL CODIGO YA EXISTE").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta... Lee el Mensaje");
                titulo.show();
                return;
            }

            DataBase.close();
            Limpiar(null);
            Toast.makeText(this,"Dato Guardados Perfectamente", Toast.LENGTH_SHORT ).show();
        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder( AgregarHerramientas.this);
            alerta.setMessage("Hay Campo Vacío \nPor Favor Revise Los Campo").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
        }

    }

    public void BuscarHerramienta(View view){
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String codigo =  EditTextCodigoHerramientas.getText().toString();

        if(!codigo.isEmpty()){

            Cursor fila = DataBase.rawQuery("select Descripcion,Cantidad,Marca from Herramientas where CodigoHerramienta ="+codigo,null);

            if(fila.moveToFirst()){
                //EditTextCodigoHerramaientas.setText(fila.getString(0));
                EditTextDescripcion.setText(fila.getString(0));
                EditTextCantidad.setText(fila.getString(1));
                EditTextMarca.setText(fila.getString(2));

                DataBase.close();
                Toast.makeText(this,"Herramienta Encotrado Perfectamente", Toast.LENGTH_SHORT ).show();
            }else{
                AlertDialog.Builder alerta = new AlertDialog.Builder( AgregarHerramientas.this);
                alerta.setMessage("Registro De Herramienta\nNo Existe").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta. Lee el Mensaje");
                titulo.show();
                DataBase.close();
            }
        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder( AgregarHerramientas.this);
            alerta.setMessage("Codigo Herramienta\nEsta Vacio").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
        }
    }

    public void ListaHerramienta(View view){
        Intent VentanaListaHerramienta = new Intent(this,ListaHerramienta.class);
        startActivity(VentanaListaHerramienta);
    }

    public void EliminarHerramientas(View view){
        //se abre la base de dato
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();
        //se lee el codigo que se ba a eliminar
        String Codigo = EditTextCodigoHerramientas.getText().toString();

        if(!Codigo.isEmpty() ){
            //se declara una bariable para toma el valor de 1 si le elimino correctamente
            //y se llama la funcion para eliminar el registro
            int valor = DataBase.delete("Herramientas","CodigoHerramienta ="+Codigo,null);

            if(valor == 1){
                AlertDialog.Builder alerta = new AlertDialog.Builder(AgregarHerramientas.this);
                alerta.setMessage("Se Elimino Con Exito El Registro : " +Codigo).setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta... Lee el Mensaje");
                titulo.show();
                Limpiar(null);
            }else{
                AlertDialog.Builder alerta = new AlertDialog.Builder(AgregarHerramientas.this);
                alerta.setMessage("No Existe El Registro : "+ Codigo).setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta... Lee el Mensaje");
                titulo.show();
            }

        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(AgregarHerramientas.this);
            alerta.setMessage("El Codigo Herramienta\nEsta Vacio ").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta... Lee el Mensaje");
            titulo.show();
        }

    }

    public void Limpiar(View view){

        EditTextCodigoHerramientas.setText("");
        EditTextDescripcion.setText("");
        EditTextCantidad.setText("");
        EditTextMarca.setText("");
        EditTextCodigoHerramientas.requestFocus();
    }

    public String OtenerUltimoRegistro(){

        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();
        String salida = "0";
        //la conslta buscar codigo del registro mas grande y le suma uno
        Cursor fila = DataBase.rawQuery("select MAX(CodigoHerramienta) from Herramientas",null);
        //la funcion movetofirst comprueba que la consulta devolcio dato
        if(fila.moveToFirst()){
            salida= String.valueOf(fila.getInt(0)+1);
        };
        DataBase.close();
        return salida;
    }

}