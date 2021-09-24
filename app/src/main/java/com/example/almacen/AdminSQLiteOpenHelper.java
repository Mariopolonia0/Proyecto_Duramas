package com.example.almacen;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase Database) {
        Database.execSQL("create table Equipo(Codigo int primary key not null,Cliente text not null,Marca text not null,Modelo text not null,TipoEquipo text not null,ProblemaEquipo text not null,FechaEntrada date not null,ComentarioTecnico text not null,ComentarioCliente text not null,NumeroTelefono text not null);");
        Database.execSQL("create table Herramientas(CodigoHerramienta int primary Key not null,Descripcion Varchar(50) not null,Cantidad int not null,Marca Varchar(50) not null);");
        Database.execSQL("create table Documento(CodigoDocumento int primary Key not null,NombreCliente Varchar(30) not null,Direccion Varchar(75) not null,Numero Varchar(30) not null,TipoDocumento Varchar(20) not null,Fecha Varchar(20) not null,Servicio Varchar(30) not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
