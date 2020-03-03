package com.example.showcase_

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.showcase_.Api.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val handler = Handler()

    private val _progVisible = MutableLiveData<Boolean>()
    val progressVisible: LiveData<Boolean> get() = _progVisible

    private val _hint = MutableLiveData<String>()
    val hint: LiveData<String> get() = _hint

    private val _hint2 = MutableLiveData<String>()
    val hint2: LiveData<String> get() = _hint2

    private val _imgVisible =  MutableLiveData<Boolean>()
    val imageVisiable: LiveData<Boolean> get() = _imgVisible

    private val _img =  MutableLiveData<String>()
    val image: LiveData<String> get() = _img

    private var disposable: Disposable? = null
    private val apiServe by lazy {
        ApiService.create()
    }

    init {
        _imgVisible.value =false
        _hint2.value = "尚未從https://jsonplaceholder.typicode.com搜尋資料"
    }
    fun onLoginClicked(user: String) {
        _progVisible.value = true
        _imgVisible.value = false
        _img.value ="https://source.unsplash.com/random"
        handler.postDelayed({
            _hint.value = user+ "預覽圖片即將出現"
            _progVisible.value = false
            _imgVisible.value = true
            beginSearch()
        }, 1500)
    }


    private fun beginSearch() {
        disposable = apiServe.hitCountCheck()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> _hint2.value = "共 ${result.size} 筆資料  from https://jsonplaceholder.typicode.com" },
                { error -> _hint2.value = "error" }
            )
    }



}