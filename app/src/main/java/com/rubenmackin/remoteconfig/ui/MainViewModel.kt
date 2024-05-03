package com.rubenmackin.remoteconfig.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rubenmackin.remoteconfig.data.Repository
import com.rubenmackin.remoteconfig.domain.CanAccessToApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository, private val canAccessToApp: CanAccessToApp) : ViewModel() {

    private val _showBlockDialog = MutableStateFlow<Boolean?>(null)
    val showBlockDialog: StateFlow<Boolean?> = _showBlockDialog

    fun initApp() {
        viewModelScope.launch(Dispatchers.IO) {
            val title = repository.getAppInfo()
            Log.i("title", title)
        }

        viewModelScope.launch {
            val canAccess = withContext(Dispatchers.IO){
                canAccessToApp()
            }

            _showBlockDialog.value = !canAccess

        }
    }
}