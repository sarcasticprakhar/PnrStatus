package com.example.pnrstatus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText pnrNum;
    Button pnrBtn;
    TextView trainName;
    TextView jDate;
    TextView booking;
    TextView current;
    TextView clas;
    TextView chart;
    ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pnrNum=(EditText) findViewById(R.id.pnrnumber);
        pnrBtn=(Button) findViewById(R.id.getstatus);
        trainName=(TextView)findViewById(R.id.trainname);
        jDate=(TextView)findViewById(R.id.date);
        booking=(TextView)findViewById(R.id.bookingstatus);
        current=(TextView)findViewById(R.id.currentstaus);
        clas=(TextView)findViewById(R.id.classof);
        chart=(TextView)findViewById(R.id.chartprepared);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        pnrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pnrNum.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter A number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                String pnrNumb=pnrNum.getText().toString();
                Log.d("HARSH", "onCreate: "+pnrNumb);
                Retrofit retrofit =new Retrofit.Builder()
                        .baseUrl("https://pnr-status-indian-railway.p.rapidapi.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api=retrofit.create(Api.class);
                Call<ModalRes> call=api.search(pnrNumb);
                call.enqueue(new Callback<ModalRes>() {
                    @Override
                    public void onResponse(Call<ModalRes> call, Response<ModalRes> response) {
                        progressBar.setVisibility(View.GONE);
                        trainName.setText(response.body().data.trainInfo.name+"-"+response.body().data.trainInfo.trainNo);
                        jDate.setText(response.body().data.trainInfo.dt+ "::"+response.body().data.boardingInfo.departureTime);
                        current.setText(response.body().data.seatInfo.coach+"-"+response.body().data.seatInfo.berth);
                    }

                    @Override
                    public void onFailure(Call<ModalRes> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);

                    }
                });
            }
        });
    }
}