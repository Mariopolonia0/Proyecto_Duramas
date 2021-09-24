package com.example.almacen;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    private static  final int SOLICITUD_PERMISO_ALMACENAMIENTO = 1;
    private Intent almacenar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        almacenar = new Intent(Intent.ACTION_MANAGE_PACKAGE_STORAGE);
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            startActivity(almacenar);
            Toast.makeText(  this, "Permiso Concedido", Toast.LENGTH_SHORT).show();
        }
        //WRITE_EXTERNAL_STORAGE
        Toast.makeText(  this, "Bienvenido A Tu Sistema De Equipo", Toast.LENGTH_SHORT).show();

    }

    public void GuardarRegitro(View view){
        Intent VentanaGuardar = new Intent(this,FormularioEquipo.class);
        startActivity(VentanaGuardar);
    }


    public void Herramientas(View view){
        Intent VentanaEditarHerramientas = new Intent(this,AgregarHerramientas.class);
        startActivity(VentanaEditarHerramientas);
    }

    public void CrearFactura(View view){
        Intent VentanaCrearFactura= new Intent(this,CrearFactura.class);
        startActivity(VentanaCrearFactura);
    }

    public void EntregarEquipo(View view){
        Intent EntregarEquipo = new Intent(this,EntregarEquipo.class);
        startActivity(EntregarEquipo);
    }

    public void infor(View view){

        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setMessage("Uso de la App:\nLlevar el control de una tienda de reparación donde se guarden equipo que se repararan y luego se entregaran a un cliente  \n\nHecha Por DURAMAS SECURITY\n\nElaborado Por Mario Peña Polonia").setCancelable(true);
        AlertDialog titulo = alerta.create();

        titulo.setTitle("Informacion De La Aplicacion");
        titulo.show();
        titulo.getWindow().getDecorView().getBackground().setColorFilter(new LightingColorFilter(0x800FF00,0xF2FFFC));
    }

}