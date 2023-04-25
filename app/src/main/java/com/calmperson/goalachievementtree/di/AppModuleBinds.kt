package com.calmperson.goalachievementtree.di

import com.calmperson.goalachievementtree.model.ModelContract
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeDrawer
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeGenerator
import com.calmperson.goalachievementtree.model.fractaltree.FractalTreeSerializer
import com.calmperson.goalachievementtree.model.source.FractalTreeRepository
import com.calmperson.goalachievementtree.model.source.GoalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModuleBinds {

    @Binds
    abstract fun fractalTreeDrawer(drawer: FractalTreeDrawer): ModelContract.FractalTreeDrawer

    @Binds
    abstract fun fractalTreeGenerator(generator: FractalTreeGenerator): ModelContract.FractalTreeGenerator

    @Binds
    abstract fun fractalTreeSerializer(serializer: FractalTreeSerializer): ModelContract.FractalTreeSerializer

    @Binds
    abstract fun fractalTreeRepository(repository: FractalTreeRepository): ModelContract.FractalTreeRepository

    @Binds
    abstract fun goalRepository(repository: GoalRepository): ModelContract.GoalRepository

}