package com.xxxxx.sampleapplicationtmdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xxxxx.sampleapplicationtmdb.Injectable
import com.xxxxx.sampleapplicationtmdb.R
import com.xxxxx.sampleapplicationtmdb.ui.movielist.MovieListFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as Injectable).inject(this)

        supportFragmentManager.fragmentFactory = mainFragmentFactory

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, MovieListFragment::class.java, null)
                .commit()
        }
    }
}
