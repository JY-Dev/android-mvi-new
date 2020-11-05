package com.kanawish.sample.mvi.intent

import android.Manifest
import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kanawish.sample.mvi.view.PermissionEvent
import com.kanawish.sample.mvi.view.permission.PermissionModelStore
import com.kanawish.sample.mvi.model.PermissionState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionFactory @Inject constructor(
        private val permissionModelStore: PermissionModelStore
){
    fun process(event:PermissionEvent,context: Context){
        permissionModelStore.process(toIntent(event,context))
    }

    private fun toIntent(viewEvent: PermissionEvent,context: Context): Intent<PermissionState> {
        return when(viewEvent) {
            PermissionEvent.LocationPermission -> buildLocationPermission(context)
        }
    }

    private fun buildLocationPermission(context: Context) : Intent<PermissionState>{
        return sideEffect {
            TedPermission.with(context)
                    .setPermissionListener(permissionListener)
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                    .check()
        }
    }

    private val permissionListener = object : PermissionListener {
        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            permissionModelStore.process(intent { PermissionState.Denied})
        }
        override fun onPermissionGranted() {
            permissionModelStore.process(intent { PermissionState.Success})
        }
    }
}