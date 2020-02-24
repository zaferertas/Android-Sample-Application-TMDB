package com.xxxxx.sampleapplicationtmdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xxxxx.sampleapplicationtmdb.ui.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragmentContainerView,
                    MainFragment.newInstance()
                )
                .commit()
        }
    }
}
