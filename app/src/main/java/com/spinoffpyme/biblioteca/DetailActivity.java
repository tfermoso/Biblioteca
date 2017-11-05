package com.spinoffpyme.biblioteca;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends Activity {
    EditText val1;
    EditText val2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent(); //cogemos el intent que ha generado el lanzamiento
        int numList=intent.getIntExtra(MainActivity.KEY_LIST,0); //los datos que nos pasan

        //ponemos los datos en pantalla
        TextView txt = (TextView) this.findViewById(R.id.txtNombre);
        txt.setText(Datos.lista[numList][0]);
        txt=(TextView)this.findViewById(R.id.txtAuthor);
        txt.setText(Datos.lista[numList][1]);

        val1=(EditText)this.findViewById(R.id.txtVal1);
        val2=(EditText)this.findViewById(R.id.txtVal2);

        //gestionamos el botón de cerrar y devolver datos
        Button bt=(Button)this.findViewById(R.id.cmdVolver);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                //devolvemos cada dato de una forma distinta
                intent.putExtra(MainActivity.KEY_RTN_VAL1,val1.getText().toString());
                intent.setData(Uri.parse(val2.getText().toString()));
                setResult(RESULT_OK,intent); //resultado y datos a devolver
                finish(); //fin de la actividad
            }
        });



    }
}
