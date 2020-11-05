package com.kanawish.sample.mvi.view

import android.content.Context

sealed class PermissionEvent {
    object LocationPermission : PermissionEvent()
}