package kr.ac.kumoh.ce.s20210734.s23termproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SkinViewModel() : ViewModel() {
    private val SERVER_URL = "https://port-0-s23w10backend-gj8u2llomgp05v.sel5.cloudtype.app/"
    private val skinApi: SkinApi
    private val _skinList = MutableLiveData<List<Skin>>()
    val skinList: LiveData<List<Skin>>
        get() = _skinList
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        skinApi = retrofit.create(SkinApi::class.java)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = skinApi.getSkins()
                _skinList.value = response
            } catch (e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }
}