package com.example.almacen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Image;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class EntregarEquipo extends AppCompatActivity {
    public TextView textViewcodigo;
    public TextView textViewcliente;
    public TextView textViewMarca;
    public TextView textCantidad;
    public TextView textViewtipoEquipo;
    public TextView textViewtotal;
    public EditText textbuscar;
    public EditText textdescripcion;
    public EditText textPrecio;
    String fechaEntrada;
    String problemaEquipo;
    private ListView listViewlistademateriales;
    ArrayList<DetalleMaterial> ListaMateriales=null;
    public String total="0";
    TemplatePDF templatePDF ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregar_equipo);

        textViewcodigo = (TextView)findViewById(R.id.textViewCodigo);
        textViewcliente = (TextView)findViewById(R.id.textViewCliente);
        textViewMarca = (TextView)findViewById(R.id.textViewMarca);
        textViewtipoEquipo = (TextView)findViewById(R.id.textViewTipoEquipo);
        textViewtotal =(TextView) findViewById(R.id.textViewtotal);
        textViewtotal.setText("0");
        textbuscar =(EditText)findViewById(R.id.editTextBuscar);
        textdescripcion =(EditText)findViewById(R.id.editTextdescripcion);
        textPrecio =(EditText)findViewById(R.id.editTextPrecio);
        textCantidad = (EditText)findViewById(R.id.editTextCantidad);
        textViewcliente = (TextView)findViewById(R.id.textViewCliente);
       //ImageView imgPreview = (ImageView) findViewById(R.id.imageViewIcono);
        ListaMateriales =new ArrayList<DetalleMaterial>();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void VistaPDF(View view){
        //si el text de vista del codigo esta vacio da una alecta de que no se puede imprimir
        if(textViewcodigo.getText().length() == 0){
            AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
            alerta.setMessage("No Hay Cliente Para Entregar Equipo").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
            return;
        }
        //si la lista de materiales usado esta vacia da una alecta de que no se puede imprimir
        if(ListaMateriales.isEmpty()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
            alerta.setMessage("No Hay Dato En La Lista\nDe Materiales Gastado").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
            return;
        }
        CrearPdf();
        templatePDF.PDFView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void AbrirPDFApp(View view){
        //si el text de vista del codigo esta vacio da una alecta de que no se puede imprimir
        if(textViewcodigo.getText().length() == 0){
            AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
            alerta.setMessage("No Hay Cliente\nPara Entregar Equipo").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
            return;
        }
        //si la lista de materiales usado esta vacia da una alecta de que no se puede imprimir
        if(ListaMateriales.isEmpty()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
            alerta.setMessage("No Hay Dato En La Lista\nDe Materiales Gastado").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
            return;
        }
        CrearPdf();
        Log.i("hola mario","llamo la clase vista en la app");
        templatePDF.PDFViewApp(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void CrearPdf(){
        String date;
        String encavezado[] = {"Descripcion","Cantidad","Precio","Total"};

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        templatePDF =new TemplatePDF(getApplicationContext());
        if(templatePDF.openDocument() == 1){
            AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
            alerta.setMessage("Por Favor Conceda Los Permiso De Almacenamientos").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
        }

        templatePDF.addMetaData( "Codigo","subtitulo","creador");
        AgregarImagenPdf();
        templatePDF.addTitle("DURAMAS SECURITY","Factura De Entraga",date);
        templatePDF.addParagraph("Codigo : "+textViewcodigo.getText()+"     Cliente : "+textViewcliente.getText());
        templatePDF.addParagraph("Marca : "+textViewMarca.getText()+"   Fecha Entrada : "+fechaEntrada);
        templatePDF.addParagraph("Tipo Equipo : "+textViewtipoEquipo.getText()+"    Problema Equipo : "+problemaEquipo);
        templatePDF.addParagraph("\n");
        templatePDF.createTable(encavezado,ListaMateriales);
        templatePDF.addPrecioTotal("Precio Total : "+textViewtotal.getText());
        templatePDF.addParagraph(" ");
        templatePDF.addParagraph("                    ______________________                  ______________________");
        templatePDF.addParagraph("            Firma Representante                                  Firma Cliente");
        templatePDF.closeDocument();

    }

    void AgregarImagenPdf(){
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iconoduramas);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.scaleToFit(250, 250);
            imagen.setAbsolutePosition(-30,680);
            templatePDF.addPintura(imagen);
        }catch (Exception e){
            Log.e("EnviarImagen", e.toString());
        }
    }

    public void AgregarMateriales(View view){

        if (!textdescripcion.getText().toString().isEmpty() && !textPrecio.getText().toString().isEmpty() && !textCantidad.getText().toString().isEmpty() ) {
            DetalleMaterial material=new DetalleMaterial();
            material.setDescripcion(textdescripcion.getText().toString());
            material.setPrecio(textPrecio.getText().toString());
            material.setCantidad(textCantidad.getText().toString());
            material.setTotal(String.valueOf(Float.parseFloat(textPrecio.getText().toString())*Float.parseFloat(textCantidad.getText().toString())));

            ListaMateriales.add(material);
            listViewlistademateriales =(ListView) findViewById(R.id.listViewListadeMateriale);
            listViewlistademateriales.setAdapter(new AdactadorMaterial(this,ListaMateriales));
            textViewtotal.setText(Total(material.getTotal()));
            textdescripcion.setText("");
            textCantidad.setText("");
            textPrecio.setText("");
            textdescripcion.requestFocus();
            Toast.makeText(this,"Se Agrego Correctamente", Toast.LENGTH_SHORT ).show();
        }else{

            if (textdescripcion.getText().toString().isEmpty()){
                AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
                alerta.setMessage("El Campo Descripcion\nEsta Vacio").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta. Lee el Mensaje");
                titulo.show();
            }

            if(textCantidad.getText().toString().isEmpty()){
                AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
                alerta.setMessage("El Campo Cantidad\nEsta Vacio").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta. Lee el Mensaje");
                titulo.show();
            }

            if(textPrecio.getText().toString().isEmpty()){
                AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
                alerta.setMessage("El Campo Precio\nEsta Vacio").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta. Lee el Mensaje");
                titulo.show();
            }
        }
    }


    public String Total(String precio){
        float precios = Float.parseFloat(precio)+Float.parseFloat(total);
        return total= String.valueOf(precios);
    }

    public void Buscar(View view){
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String codigo =  textbuscar.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = DataBase.rawQuery("select Cliente,Marca,TipoEquipo,ProblemaEquipo,FechaEntrada  from Equipo where Codigo ="+codigo,null);
            //Cliente Marca Modelo TipoEquipo ProblemaEquipo FechaEntrada ComentarioTecnico ComentarioCliente  NumeroTelefono
            if(fila.moveToFirst()){
                textViewcliente.setText(fila.getString(0));
                textViewMarca.setText(fila.getString(1));
                textViewtipoEquipo.setText(fila.getString(2));
                problemaEquipo = fila.getString(3);
                fechaEntrada = fila.getString(4);
                textViewcodigo.setText(textbuscar.getText().toString());
                DataBase.close();
                Toast.makeText(this,"Equipo Encotrado Perfectamente", Toast.LENGTH_SHORT ).show();
            }else{
                AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
                alerta.setMessage(" El Codigo Registrado\n No Exite").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta. Lee el Mensaje");
                titulo.show();
                DataBase.close();
            }
        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(EntregarEquipo.this);
            alerta.setMessage("El Campo Codigo\nEsta Vacio").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
        }
    }

    public void LinpiarCliente(View view){
        textViewcodigo.setText("");
        textViewcliente.setText("");
        textViewMarca.setText("");
        textViewtipoEquipo.setText("");
        textbuscar.setText("");
    }

    public void LinpiarListView(View view){
        if (listViewlistademateriales==null)
            return;
        listViewlistademateriales.setAdapter(null);
        ListaMateriales.clear();
        total = "0";
        textViewtotal.setText("0");
    }


}