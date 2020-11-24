package com.demo.mb.util.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module//@Module注解，表示这一个用于提供依赖注入实例的模块。
@InstallIn(ActivityComponent::class)
abstract class EngineModule {

    @BindGasEngine
    @Binds
    abstract fun bindGasEngine(gasEngine: GasEngine): Engine

    @BindElectricEngine
    @Binds
    abstract fun bindElectricEngine(electricEngine: ElectricEngine): Engine
}