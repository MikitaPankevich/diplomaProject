package com.demo.diplomaproject.di

// see: https://youtrack.jetbrains.com/issue/KT-18918
data class PrimitiveWrapper<out T>(val value: T)
