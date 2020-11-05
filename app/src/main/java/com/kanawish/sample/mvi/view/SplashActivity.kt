package com.kanawish.sample.mvi.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kanawish.sample.mvi.R
import com.kanawish.sample.mvi.intent.PermissionFactory
import com.kanawish.sample.mvi.intent.intent
import com.kanawish.sample.mvi.view.permission.PermissionModelStore
import com.kanawish.sample.mvi.model.PermissionState
import com.kanawish.sample.mvi.view.tasks.TasksActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), StateSubscriber<PermissionState> {
    @Inject
    lateinit var permissionModelStore: PermissionModelStore
    @Inject lateinit var permissionFactory: PermissionFactory
    var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        permissionFactory.process(PermissionEvent.LocationPermission,this)
    }

    override fun onResume() {
        super.onResume()
        disposable += permissionModelStore.modelState().subscribeToState()
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun Observable<PermissionState>.subscribeToState(): Disposable {
        return subscribe {
            when (it) {
                is PermissionState.Success -> {
                    startActivity(android.content.Intent(this@SplashActivity,TasksActivity::class.java))
                    finish()
                }
                is PermissionState.Denied -> {
                    permissionModelStore.process(intent { PermissionState.None})
                    permissionFactory.process(PermissionEvent.LocationPermission,this@SplashActivity)
                }
            }
        }
    }
}