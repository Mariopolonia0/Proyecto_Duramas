package com.example.almacen;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

//import com.itextpdf.io.image

//esta clase maneja la plantilla del pdf factura
public class PlantillaDuramas {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font ftitulo = new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
    private Font fsubtitulo = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD);
    private Font ftexto = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD);
    private Font fnumero = new Font(Font.FontFamily.TIMES_ROMAN,10,Font.BOLD);
    private Font fTotal = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.RED);
    private Font fdatoTabla = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD,BaseColor.BLACK);

    public PlantillaDuramas(Context context) {
        this.context = context;
    }

    public int openDocument(String Nombre){
        createFile(Nombre);
        try{
            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            document.open();

        }catch (Exception e){
            Log.e("openDocumento",e.toString());
            return 1;
        }
        return 0;
    }
    //esta funcion crea el archivo pdf en el dispositivo
    private void createFile(String Nombre){
        File folder = new File(Environment.getExternalStorageDirectory().toString(),"Facturas DuraMas");
        if(!folder.exists()) {
            folder.mkdirs();
        }
        pdfFile = new File(folder,Nombre+".PDF");
    }

    public  void closeDocument(){
        document.close();
    }

    //aqui se le agregas los dato al pdf
    public void addMetaData(String title){
        document.addTitle(title);
    }

    //aqui se crea el parrafo padre
    public void addTitle(String title) {
        try {
            paragraph = new Paragraph();
            addChild(new Paragraph(title, ftitulo));
            paragraph.setSpacingAfter(5);
            document.add(paragraph);

        }catch (Exception e){
            Log.e("addTitle", e.toString());
        }
    }
    //Agrega titulo en este caso el nombre de la empresa
    public void addChild(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_RIGHT);
        paragraph.add(childParagraph);
    }
    //se agrega un parafo normal
    public void addParagraph(String text){
        try{
            paragraph = new Paragraph(text,ftexto);
            document.add(paragraph);

        }catch (Exception e){
            Log.e("addParagraph", e.toString());
        }
    }
    //esta imprime los numeros de telefono de los represetante de la empresa que son lo que estan despues del nombre de la empresa
    public void addParagraphTelefono(String text){
        try{
            paragraph = new Paragraph(text,ftexto);
            paragraph.setFont(fnumero);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

        }catch (Exception e){
            Log.e("addParagraph", e.toString());
        }
    }

    //agrega el precio total a la factura
    public void addPrecioTotal(String text){
        try{
            paragraph = new Paragraph(text,fTotal);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addPrecioTotal", e.toString());
        }

    }

    //aqui se le pasa una instacia de una imagen para agregarla al pdf
    public void addPintura(Image image){
        try{
            document.add(image);
        }catch (Exception e){
            Log.e("addImagen", e.toString());
        }

    }

    public void createTable(String[]header, ArrayList<DetalleMaterial> material){
        try{
            paragraph = new Paragraph();
            paragraph.setFont(ftexto);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            //esta funcio recive un arreglo de numero para darle el tamaño a cada celda usando porcentajes
            pdfPTable.setWidths(new int[]{18,4,4,4,5});
            //esta funcio configura el tamaño que ba a tener la tabla en el pdf usando un porcentajes
            pdfPTable.setWidthPercentage(100);
            // pdfPTable;
            PdfPCell pdfPCell;
            int indexC = 0;
            //rellena la table de encabezado
            while (indexC<header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++],fsubtitulo));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(new BaseColor(52,131,255));
                pdfPTable.addCell(pdfPCell);
            }
            //llenar la tabla
            for(int indexR=0;indexR<material.size();indexR++){
                DetalleMaterial row = material.get(indexR);
                for (indexC=0 ;indexC<header.length;indexC++){
                    if(indexC==0){
                        pdfPCell = new PdfPCell(new Phrase(row.getDescripcion(),fdatoTabla));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        pdfPTable.addCell(pdfPCell);
                        indexC++;
                    }  if(indexC==1){
                        pdfPCell = new PdfPCell(new Phrase(row.getCantidad(),fdatoTabla));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        pdfPTable.addCell(pdfPCell);
                        indexC++;
                    }if(indexC==3){
                        pdfPCell = new PdfPCell(new Phrase(row.getPrecio(),fdatoTabla));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        pdfPTable.addCell(pdfPCell);
                        indexC++;
                    }if(indexC==4) {
                        pdfPCell = new PdfPCell(new Phrase(row.getITBIS(),fdatoTabla));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        pdfPTable.addCell(pdfPCell);
                        indexC++;
                    }if(indexC==5) {
                        pdfPCell = new PdfPCell(new Phrase(row.getTotal(),fdatoTabla));
                        pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        pdfPTable.addCell(pdfPCell);
                        indexC++;
                    }
                }
            }
            paragraph.setSpacingBefore(5);
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("createTable", e.toString());
        }
    }

    public void createTableTitulo(String[]header,BaseColor color,BaseColor colorLetra){
        try{
            fsubtitulo.setColor(colorLetra);
            paragraph = new Paragraph();
            paragraph.setFont(ftexto);
            //paragraph.setSpacingBefore(1);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            //esta funcio recive un arreglo de numero para darle el tamaño a cada celda usando porcentajes
            pdfPTable.setWidths(new int[]{15,5,15});
            //esta funcio configura el tamaño que ba a tener la tabla en el pdf usando un porcentajes
            pdfPTable.setWidthPercentage(60);
            pdfPTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell pdfPCell;

            int indexC = 0;
            //rellena la table de encabezado
            while (indexC<header.length) {

                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fsubtitulo));
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(color);
                pdfPTable.addCell(pdfPCell);
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("createTableTitulo", e.toString());
        }
    }

//aqui se llena los dato del cliente se le pasa los nombre los colores de fondo los colores de letras y el espacio que lleva desde el ultimo testo
    public void createTableCliente(String header,BaseColor color,BaseColor colorLetra,int spacio){
        try{
            fsubtitulo.setColor(colorLetra);
            paragraph = new Paragraph();
            paragraph.setFont(ftexto);

            PdfPTable pdfPTable = new PdfPTable(1);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell pdfPCell;

            pdfPCell = new PdfPCell(new Phrase(header, fsubtitulo));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPCell.setVerticalAlignment(Element.ALIGN_LEFT);
            pdfPCell.setBackgroundColor(color);
            pdfPTable.addCell(pdfPCell);
            paragraph.add(pdfPTable);
            paragraph.setSpacingBefore(spacio);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("createTableCliente", e.toString());
        }
    }

    public void createTableprecioTotal(String[] precio){
        try{
            fsubtitulo.setColor(BaseColor.RED);
            paragraph = new Paragraph();
            paragraph.setFont(ftexto);
            PdfPTable pdfPTable = new PdfPTable(2);
            //esta funcio recive un arreglo de numero para darle el tamaño a cada celda usando porcentajes
            pdfPTable.setWidths(new int[]{53,50});
            //esta funcio configura el tamaño que ba a tener la tabla en el pdf usando un porcentajes
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell pdfPCell;

            int indexC = 0;
            //rellena la table de encabezado
            while (indexC<precio.length) {

                pdfPCell = new PdfPCell(new Phrase(precio[indexC++], fsubtitulo));
                pdfPCell.setVerticalAlignment(Element.ALIGN_RIGHT);
                pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                pdfPTable.addCell(pdfPCell);
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("createTableprecioTotal", e.toString());
        }
    }


    public void PDFView(){
        Intent intent = new Intent(context,VistaPdf.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    //para mostrar el PDF en una aplicacion
    public void PDFViewApp(Activity activity) {

        if (pdfFile.exists()) {
            //aqui se abre el PDF para enviarlo a la aplicacion
            String s = String.valueOf(pdfFile);
            File arch = new File(s);
            if (arch.exists()) {
                //uri es al clase directorio que buscar la ruta del PDF
                Uri uri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", arch);
                //intent es la que llama a la aplicacion para que abrra
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    //aqui se ivoca a la aplicacion
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.i("Error","Error : "+ e);
                }

            } else {
                Toast.makeText(activity.getApplicationContext(), "No Se Encuentra El PDF", Toast.LENGTH_LONG);
            }
        }
    }
}



