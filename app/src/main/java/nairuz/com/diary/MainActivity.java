package nairuz.com.diary;

import android.content.Context;
import android.content.Intent;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class MainActivity extends SherlockActivity {


    private EditText jornal_EditText;
    private Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jornal_EditText = (EditText) findViewById(R.id.editText_jornal);
        save_button = (Button) findViewById(R.id.button_save);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!jornal_EditText.getText().toString().equals("")){

                    String data = jornal_EditText.getText().toString();
                    writeFile(data);


                }else{
                    Toast.makeText(getApplicationContext(),"Please Enter text",Toast.LENGTH_LONG).show();
                }

            }
        });

        //Check the file if it is there:
        if (readFile() != null){
            jornal_EditText.setText(readFile());
        }
    }


    public void writeFile(String myData) {
        //to write ina file use try and catch to check for errors
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("Jornal.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(myData);
            outputStreamWriter.close(); //always close the file
        }catch (IOException e ){
            Log.v("MainActivity",e.toString());
        }
    }

    public String readFile (){

        String input = " ";

        try{
            InputStream inputStream = openFileInput("Jornal.txt");

            if (inputStream != null){//if the file is not empty, read it

                InputStreamReader read_file = new InputStreamReader(inputStream);//read the file

                //create a buffer reader
                BufferedReader bufferedReader = new BufferedReader(read_file);

                //create a temporary empty string
                String tempText = " ";

                StringBuilder stringBuilder = new StringBuilder();// use string build for appending texts

                while((tempText = bufferedReader.readLine()) != null ){

                    //if the file is not empty, append it to the StringBuilder
                    stringBuilder.append(tempText);
                }

                inputStream.close(); //close the file
                input= stringBuilder.toString(); //to retrieve files in text format.
            }


        }catch(FileNotFoundException e){
            Log.v("MainActivity", "File not found" + e.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
}