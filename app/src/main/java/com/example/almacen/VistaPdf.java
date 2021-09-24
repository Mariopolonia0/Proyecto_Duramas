package com.example.almacen;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.barteksc.pdfviewer.PDFView;
import java.io.File;

public class VistaPdf extends AppCompatActivity {

    private PDFView pdfView;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_pdf);
        pdfView =(PDFView)findViewById(R.id.PdfView);
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
            file =new File(bundle.getString("path",""));
        }

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();


    }

}