package com.example.almacen;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class CrearFactura extends AppCompatActivity {


    private Spinner spinnerTipodocumento ;
    private EditText CodigoDocumentoEditText;
    private EditText NombreClienteEditText;
    private EditText DireccionEditText;
    private EditText NumeroEditText;
    private EditText FechaEditText;
    private EditText ServicioEditText;
    private TextView PrecioTotalText;
    private EditText EditTextDescripcion;
    private EditText EditTextCantidad;
    private EditText EditTextPrecio;
    private CheckBox CheckBoxITBIS;
    private ActionBar actionBar;

    private ListView listViewlistademateriales;
    ArrayList<DetalleMaterial> ListaMateriales=null;
    public String total="0";
    PlantillaDuramas plantillaDuramas;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_factura);

        CodigoDocumentoEditText = (EditText) findViewById(R.id.editTextCodigoFactura);
        NombreClienteEditText = (EditText) findViewById(R.id.editTextNombreClienteFactura);
        DireccionEditText = (EditText) findViewById(R.id.editTextDirecionFactura);
        NumeroEditText = (EditText) findViewById(R.id.editTextNumeroFactura);
        spinnerTipodocumento = (Spinner)findViewById(R.id.spinnertipodocumento);
        FechaEditText = (EditText) findViewById(R.id.editTextFechaFactura);
        ServicioEditText = (EditText) findViewById(R.id.editTextServicioDocumento);
        EditTextDescripcion = (EditText) findViewById(R.id.editTextDescripcionDocumento);
        EditTextCantidad = (EditText) findViewById(R.id.editTextCantidadDocumento);
        EditTextPrecio = (EditText) findViewById(R.id.editTextPrecioDocumento);
        CheckBoxITBIS = (CheckBox) findViewById(R.id.checkBoxItbis);
        CodigoDocumentoEditText.setText(OtenerUltimoRegistro());
//        actionBar = getSupportActionBar();
//        actionBar.setIcon(R.drawable.ic_round_liquor_);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        PrecioTotalText = (TextView) findViewById(R.id.textViewPrecioTotalDocumento);
        PrecioTotalText.setText("PRECIO $ 0");
        listViewlistademateriales =(ListView) findViewById(R.id.ListViewDocumento);
        listViewlistademateriales.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        ListaMateriales =new ArrayList<DetalleMaterial>();
        String [] Opciones ={"FACTURA","COTIZACION"};
        ArrayAdapter<String> adtater =new ArrayAdapter<String>(this,R.layout.spinne_edicion_tipodocumento,Opciones);
        spinnerTipodocumento.setAdapter(adtater);
        buscarFechaHoy();
        NombreClienteEditText.requestFocus();

    }

    public void GuardarRegistro(View view){
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String Codigo = CodigoDocumentoEditText.getText().toString();
        String Nombre= NombreClienteEditText.getText().toString();
        String Direccion = DireccionEditText.getText().toString();
        String NumeroTelefono = NumeroEditText.getText().toString();
        String TipoDocumento = spinnerTipodocumento.getSelectedItem().toString();
        String Servicio = ServicioEditText.getText().toString();
        String Fecha = FechaEditText.getText().toString();


        if (!Codigo.isEmpty() && !Nombre.isEmpty() && !Direccion.isEmpty() && !NumeroTelefono.isEmpty() && !Servicio.isEmpty() && !Fecha.isEmpty()) {
            //CodigoDocumento ,NombreCliente ,Direccion,Numero,TipoDocumento ,Fecha ,Servicio
            ContentValues registro = new ContentValues();
            registro.put("CodigoDocumento",Codigo);
            registro.put("NombreCliente",Nombre);
            registro.put("Direccion",Direccion);
            registro.put("Numero",NumeroTelefono);
            registro.put("TipoDocumento",TipoDocumento);
            registro.put("Fecha",Fecha);
            registro.put("Servicio",Servicio);

            //el metodo insert debueve -1 si no se guardo el registro informacion para casos de pruebas
            if( DataBase.insert("Documento",null,registro)<0)
            {
                AlertDialog.Builder alerta = new AlertDialog.Builder(CrearFactura.this);
                alerta.setMessage("NO SE GUARDO EL REGISTRO\nEL CODIGO YA EXISTE").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta... Lee el Mensaje");
                titulo.show();
                return;
            }


            DataBase.close();
            Toast.makeText(this,"Dato Guardados Perfectamente", Toast.LENGTH_SHORT ).show();
        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(CrearFactura.this);
            alerta.setMessage("Hay Campos Vacio\nNo Se puede Guardar").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta... Lee el Mensaje");
            titulo.show();
        }
    }



    public void BuscarRegistro(View view){
        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String codigo =  CodigoDocumentoEditText.getText().toString();

        if(!codigo.isEmpty()){    //CodigoDocumento ,NombreCliente ,Direccion,Numero,TipoDocumento ,Fecha ,Servicio
            Cursor fila = DataBase.rawQuery("select NombreCliente,Direccion,Numero,TipoDocumento,Fecha,Servicio from Documento where CodigoDocumento ="+codigo,null);

            if(fila.moveToFirst()){
                NombreClienteEditText.setText(fila.getString(0));
                DireccionEditText.setText(fila.getString(1));
                DireccionEditText.setSelection(1);
                NumeroEditText.setText(fila.getString(2));
                switch (fila.getString(3)){
                    case "FACTURA": spinnerTipodocumento.setSelection(0);break;
                    case "COTIZACION" : spinnerTipodocumento.setSelection(1);break;
                }
                FechaEditText.setText(fila.getString(4));
                ServicioEditText.setText(fila.getString(5));

                DataBase.close();
                Toast.makeText(this,"Equipo Encotrado Perfectamente", Toast.LENGTH_SHORT ).show();
            }else{
                Toast.makeText(this,"No Existe El Equipo", Toast.LENGTH_SHORT ).show();
                DataBase.close();
            }
        }else{
            //dialogo de alerta
            AlertDialog.Builder alerta = new AlertDialog.Builder( CrearFactura.this);
            alerta.setMessage("Codigo Equipo\nEsta Vacio").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
        }
    }

    public String OtenerUltimoRegistro(){

        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();
        String salida = "0";
        //la conslta buscar codigo del registro mas grande y le suma uno
        Cursor fila = DataBase.rawQuery("select MAX(CodigoDocumento)  from Documento",null);
        //la funcion movetofirst comprueba que la consulta devolcio dato
        if(fila.moveToFirst()){
             salida= String.valueOf(fila.getInt(0)+1);
        };
        DataBase.close();
        return salida;
    }

    public void EliminarEquipo(View view){

        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(this,"AdminDatabase",null,1);
        SQLiteDatabase DataBase =admin.getWritableDatabase();

        String Codigo = CodigoDocumentoEditText.getText().toString();
        String Cliente = NombreClienteEditText.getText().toString();

        if(!Codigo.isEmpty() && !Cliente.isEmpty()){
            int cantidad = DataBase.delete("Documento","CodigoDocumento="+Codigo,null);

            if(cantidad == 1){
                AlertDialog.Builder alerta = new AlertDialog.Builder(CrearFactura.this);
                alerta.setMessage("Se Elimino Con Exito El Documento : " +Codigo).setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta... Lee el Mensaje");
                titulo.show();
                //Limpiar(null);
            }else{
                AlertDialog.Builder alerta = new AlertDialog.Builder(CrearFactura.this);
                alerta.setMessage("No Existe El Documento : "+ Codigo).setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta... Lee el Mensaje");
                titulo.show();
            }

        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(CrearFactura.this);
            alerta.setMessage("El Codigo Y Cliente Esta Vacio ").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta... Lee el Mensaje");
            titulo.show();
        }

    }

    public void AgregarMateriales(View view){

        if (!EditTextDescripcion.getText().toString().isEmpty() && !EditTextCantidad.getText().toString().isEmpty() && !EditTextPrecio.getText().toString().isEmpty()  ) {
            DetalleMaterial material=new DetalleMaterial();
            material.setDescripcion(EditTextDescripcion.getText().toString());
            material.setPrecio(EditTextPrecio.getText().toString());
            material.setCantidad(EditTextCantidad.getText().toString());
            material.setTotal(String.valueOf(Float.parseFloat(EditTextPrecio.getText().toString())*Float.parseFloat(EditTextCantidad.getText().toString())));
            if(CheckBoxITBIS.isChecked())
                material.setITBIS(String.valueOf(Float.parseFloat(material.getTotal())*Float.parseFloat("0.18")));
            else
                material.setITBIS(String.valueOf(Float.parseFloat(material.getTotal())*Float.parseFloat("0")));

            material.setTotal(String.valueOf(Float.parseFloat(material.getTotal())+Float.parseFloat(material.getITBIS())));

            ListaMateriales.add(material);
            listViewlistademateriales.setAdapter(new AdactadorMaterial(this,ListaMateriales));
            PrecioTotalText.setText("PRECIO TOTAL $ "+Total(material.getTotal()));
            EditTextDescripcion.setText("");
            EditTextCantidad.setText("");
            EditTextPrecio.setText("");
            EditTextDescripcion.requestFocus();
            Toast.makeText(this,"Se Agrego Correctamente", Toast.LENGTH_SHORT ).show();
        }else{

            if (EditTextDescripcion.getText().toString().isEmpty()){
                AlertDialog.Builder alerta = new AlertDialog.Builder(CrearFactura.this);
                alerta.setMessage("El Campo Descripcion\nEsta Vacio").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta. Lee el Mensaje");
                titulo.show();
            }

            if(EditTextCantidad.getText().toString().isEmpty()){
                AlertDialog.Builder alerta = new AlertDialog.Builder(CrearFactura.this);
                alerta.setMessage("El Campo Cantidad\nEsta Vacio").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta. Lee el Mensaje");
                titulo.show();
            }

            if(EditTextPrecio.getText().toString().isEmpty()){
                AlertDialog.Builder alerta = new AlertDialog.Builder(CrearFactura.this);
                alerta.setMessage("El Campo Precio\nEsta Vacio").setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Alerta. Lee el Mensaje");
                titulo.show();
            }
        }
    }

    public void CrearPdf(View view){
        String encavezadotitulo[] = {spinnerTipodocumento.getSelectedItem().toString()+" #","","FECHA"};
        String encavezadoMateriales[] = {"DESCRIPCION MATERIALES","CANT.","PRECIO","ITBIS","TOTAL"};

        plantillaDuramas=new PlantillaDuramas(getApplicationContext());
        if(plantillaDuramas.openDocument(spinnerTipodocumento.getSelectedItem().toString()+"_"+CodigoDocumentoEditText.getText().toString()) == 1){
            AlertDialog.Builder alerta = new AlertDialog.Builder(CrearFactura.this);
            alerta.setMessage("Por Favor Conceda Los Permiso De Almacenamientos").setCancelable(true);
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Alerta. Lee el Mensaje");
            titulo.show();
        }

        BaseColor colorFondoPdf = new BaseColor(52,131,255);
        BaseColor colorLetraPdf = new BaseColor(255, 255, 255);
        BaseColor colorFondo2Pdf = new BaseColor(255, 250, 250);
        BaseColor colorLetra2Pdf = new BaseColor(0, 0, 0);
        AgregarImagenPdf();
        plantillaDuramas.addTitle("DURAMAS SECURITY          ");
        plantillaDuramas.addParagraphTelefono("TEL.(849-351-7323) (809-767-5085) (829-798-5826) ");
        plantillaDuramas.createTableTitulo(encavezadotitulo,colorFondoPdf,colorLetraPdf);
        String codigoFecha[] = {CodigoDocumentoEditText.getText().toString(),"",FechaEditText.getText().toString()};
        plantillaDuramas.createTableTitulo(codigoFecha,colorFondo2Pdf,colorLetra2Pdf);
        plantillaDuramas.createTableCliente("CLIENTE",colorFondoPdf,colorLetraPdf,35);
        plantillaDuramas.addParagraph("NOMBRE : "+NombreClienteEditText.getText().toString());
        plantillaDuramas.addParagraph("DIRRECION : "+DireccionEditText.getText().toString());
        plantillaDuramas.addParagraph("NUMERO TELEFONO : "+NumeroEditText.getText().toString());
        plantillaDuramas.createTableCliente("SERVICIO",colorFondoPdf,colorLetraPdf,1);
        plantillaDuramas.addParagraph(ServicioEditText.getText().toString());
        plantillaDuramas.createTable(encavezadoMateriales,ListaMateriales);
        String precio0[] = {" "," "};
        plantillaDuramas.createTableprecioTotal(precio0);
        String[] precio = {"",PrecioTotalText.getText().toString()};
        plantillaDuramas.createTableprecioTotal(precio);
        plantillaDuramas.addParagraph(" ");
        plantillaDuramas.addParagraph("                 ______________________                           ______________________");
        plantillaDuramas.addParagraph("                     Firma Representante                                       Firma Cliente");
        plantillaDuramas.closeDocument();
        //plantillaDuramas.PDFView();
        plantillaDuramas.PDFViewApp(this);
    }

    void AgregarImagenPdf(){
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iconoduramas);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.scaleToFit(250, 250);
            imagen.setAbsolutePosition(-10,690);
            plantillaDuramas.addPintura(imagen);
        }catch (Exception e){
            Log.e("EnviarImagen", e.toString());
        }
    }


    public String Total(String precio){
        float precios = Float.parseFloat(precio)+Float.parseFloat(total);
        return total= String.valueOf(precios);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buscarFechaHoy(){
        FechaEditText.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
    }

    /*public void MoverCursor() {
        NombreClienteEditText.setSelection(1);
    }*/

}