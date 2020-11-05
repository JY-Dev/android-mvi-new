package com.kanawish.sample.mvi.view.permission

import com.kanawish.sample.mvi.model.ModelStore
import com.kanawish.sample.mvi.model.PermissionState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionModelStore  @Inject constructor() :
        ModelStore<PermissionState>(PermissionState.None)