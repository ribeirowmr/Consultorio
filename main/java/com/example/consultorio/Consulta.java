package com.example.consultorio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor; //Navegar entre os registros
import android.widget.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;

public class Consulta extends AppCompatActivity {

    EditText et_nome_consulta, et_grp_sanguineo_consulta, et_celular_consulta, et_telefone_consulta;
    Button button_anterior, button_proximo, button_voltar_consulta;

    SQLiteDatabase db=null;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        et_nome_consulta = (EditText) findViewById(R.id.et_nome_consulta);
        et_grp_sanguineo_consulta = (EditText) findViewById(R.id.et_grp_sanguineo_consulta);
        et_celular_consulta = (EditText) findViewById(R.id.et_celular_consulta);
        et_telefone_consulta = (EditText) findViewById(R.id.et_telefone_consulta);
        button_anterior = (Button) findViewById(R.id.button_anterior);
        button_proximo = (Button) findViewById(R.id.button_proximo);
        button_voltar_consulta = (Button) findViewById(R.id.button_voltar_consulta);

        buscarDados();
    }

    public void fechar_tela(View v) {
        this.finish();
    }

    public void abrirDB(){
        try{
            db=openOrCreateDatabase( "bancoConsulta",MODE_PRIVATE,null);

        }catch (Exception ex){
            msg("Erro ao abrir ou criar banco de dados");
        }//finally{
        //msg("Banco de dados aberto");
        // }
    }

    public void fecharDB(){
        db.close();
    }

    public void buscarDados(){
        abrirDB();
        cursor=db.query("paciente",
                new String[]{"nome","grp_sanguineo","celular","fone"},
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            mostrarDados();
            //return true;
        }else{
            msg("Nenhum registro encontrado");
           // return false;
        }
    }

    public void proximoRegistro(View v){
        try{
            cursor.moveToNext();
            mostrarDados();
        }catch (Exception ex){
            if (cursor.isAfterLast()){
                msg("Não existe mais registros");
            }else{
                msg("Erro ao navegar pelos registros");
            }
        }
    }
    public void registroAnterior(View v){
        try{
            cursor.moveToPrevious();
            mostrarDados();
        }catch (Exception ex){
            if (cursor.isBeforeFirst()){
                msg("Não existem mais registros");
            }else{
                msg("Erro ao navegar pelos registros");
            }

        }
    }

    public void mostrarDados(){
        et_nome_consulta.setText(cursor.getString(cursor.getColumnIndex("nome")));
        et_grp_sanguineo_consulta.setText(cursor.getString(cursor.getColumnIndex("grp_sanguineo")));
        et_celular_consulta.setText(cursor.getString(cursor.getColumnIndex("celular")));
        et_telefone_consulta.setText(cursor.getString(cursor.getColumnIndex("fone")));
    }

    public void msg(String txt){
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setMessage(txt);
        adb.setNeutralButton("OK", null);
        adb.show();
    }

}