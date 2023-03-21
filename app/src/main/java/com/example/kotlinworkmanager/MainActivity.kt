package com.example.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data=Data.Builder().putInt("intKey",1).build()

        val constraints=Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            //.setRequiresDeviceIdle(true)
            //.setRequiresBatteryNotLow(true)
            .build()

       /* val myWorkRequest:WorkRequest= OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(5,TimeUnit.SECONDS)
            //.addTag("RefreshDataTag")
            .build()*/

        //Android En az 15dk'da bir çalıştırabilirsin diyor
      /**  val myWorkRequest:PeriodicWorkRequest= PeriodicWorkRequestBuilder<RefreshDatabase>(15,TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(5,TimeUnit.SECONDS)
            //.addTag("RefreshDataTag")
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id).observe(this,
            Observer {
              if (it.state==WorkInfo.State.RUNNING)
              {
                  println("workmanager is running")
              }else if (it.state==WorkInfo.State.FAILED)
              {
                  println("workmanager is failed")
              }else if (it.state==WorkInfo.State.SUCCEEDED)
              {
                  println("workmanager is succeeded")
              }
            })*/

        // WorkManager.getInstance(this).cancelAllWork() -> İptal etmek için

        //Chaining
        val oneTimeWorkRequest:OneTimeWorkRequest= OneTimeWorkRequestBuilder<RefreshDatabase>()
          .setConstraints(constraints)
          .setInitialDelay(5,TimeUnit.SECONDS)
          .setInputData(data)
          .build()

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
            .then(oneTimeWorkRequest) //altta basla request geç
            .then(oneTimeWorkRequest)
            .enqueue()




    }
}