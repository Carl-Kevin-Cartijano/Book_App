package com.thebrownfoxx.petrealm.utils

import android.content.Context
import android.content.SharedPreferences
import com.thebrownfoxx.petrealm.PetRealmApplication

object SharedPrefManager {
    private const val PREFERENCE_NAME = "MyAppPreferences"

    private val sharedPreferences : SharedPreferences by lazy {
        PetRealmApplication.instance.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value : String){
        sharedPreferences.edit().putString(key,value).apply()
    }

    fun getString(key:String, default: String): String{
        return sharedPreferences.getString(key,default) ?: default
    }

    fun removeString(key:String){
        sharedPreferences.edit().remove(key).apply()
    }
}