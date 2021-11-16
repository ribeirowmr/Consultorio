package com.example.consultorio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase; //Banco de Dados
import android.database.Cursor; //Navegar entre os registros
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText et_nome, et_grp_sanguineo, et_celular, et_telefone;
    Button button_gravar, button_consultar, button_fechar;

    SQLiteDatabase db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nome=(EditText) findViewById(R.id.et_nome);
        et_telefone=(EditText) findViewById(R.id.et_telefone);
        et_grp_sanguineo=(EditText) findViewById(R.id.et_grp_sanguineo);
        et_celular=(EditText) findViewById(R.id.et_celular);
        button_gravar=(Button) findViewById(R.id.button_gravar);
        button_consultar=(Button) findViewById(R.id.button_consultar);
        button_fechar=(Button) findViewById(R.id.button_fechar);

        abrirDB();
        abrirOuCriarTabela();
        fecharDB();

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

    public void abrirOuCriarTabela(){
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS paciente (id INTEGER PRIMARY KEY, nome TEXT, grp_sanguineo TEXT, celular TEXT, fone TEXT);");
        }catch (Exception ex){
            msg("Erro ao criar tabela");
        }//finally{
            //msg("Tabela contatos criada com sucesso");
       // }

    }

    public void inserirRegistro(View v){
        String st_nome, st_grp_sanguineo, st_celular, st_telefone;
        st_nome=et_nome.getText().toString();
        st_grp_sanguineo=et_grp_sanguineo.getText().toString();
        st_celular=et_celular.getText().toString();
        st_telefone=et_telefone.getText().toString();


        if(st_nome=="" || st_grp_sanguineo=="" || st_celular=="" || st_telefone==""){
            msg("Campos não podem estar vazios");
            return;
        }
        abrirDB();
        try{
            db.execSQL("INSERT INTO paciente (nome, grp_sanguineo, celular, fone) VALUES ('"+st_nome+"','"+st_grp_sanguineo+"','"+st_celular+"','"+st_telefone+"')");

        }catch (Exception ex){
            msg("Erro ao inserir registro!");
        }finally {
            msg("Registro inserido com sucesso");
        }
        fecharDB();
        et_nome.setText(null);
        et_grp_sanguineo.setText(null);
        et_celular.setText(null);
        et_telefone.setText(null);
    }

    public void abrir_tela_consulta(View v){
        Intent it_tela_consulta=new Intent(this, Consulta.class);
        startActivity(it_tela_consulta);
    }

    public void fechar_tela(View v) {
        this.finish();
    }

    public void msg(String txt){
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setMessage(txt);
        adb.setNeutralButton("OK", null);
        adb.show();
    }
}