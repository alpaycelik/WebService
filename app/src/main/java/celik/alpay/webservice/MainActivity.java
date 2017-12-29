package celik.alpay.webservice;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.button);
        tv1 = (TextView) findViewById(R.id.tv1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tv1.setText("sunucu cevabı");
                new arkaPlan().execute("http://localhost:8000/api/v1/products");
            }
        });
    }
    class arkaPlan extends AsyncTask<String,String,String> {
        protected String doInBackground(String... params) {
            // İlk elemanı sunucu adresi URL
            HttpURLConnection connection = null;
            BufferedReader br = null;
            try {
                URL url = new URL(params[0]);// http://127.0.0.1:8000/listele
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String satir;
                String dosya = "";
                while ((satir = br.readLine()) != null) {
                    Log.d("satir", satir);
                    dosya += satir;
                }
                return dosya;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "hata";
        }
        protected void onPostExecute(String s){
            Log.d("postExecutetanGelen",s);
            tv1.setText(s);
        }
    }
}
