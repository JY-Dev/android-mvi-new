package com.kanawish.sample.mvi.model

sealed class PermissionState {
    object Denied : PermissionState()
    object Success : PermissionState()
    object None : PermissionState()
}