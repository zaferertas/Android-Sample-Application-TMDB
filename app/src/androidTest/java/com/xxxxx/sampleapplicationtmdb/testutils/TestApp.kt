package com.xxxxx.sampleapplicationtmdb.testutils

import android.app.Application
import com.xxxxx.sampleapplicationtmdb.Injectable
import com.xxxxx.sampleapplicationtmdb.ui.MainActivity

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 */
class TestApp : Application(), Injectable {

    override fun inject(activity: MainActivity) {
        //No dagger initialization
    }
}