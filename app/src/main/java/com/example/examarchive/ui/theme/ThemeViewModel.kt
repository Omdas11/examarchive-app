package com.example.examarchive.ui.theme

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.examarchive.data.ThemePreferenceRepository
import com.example.examarchive.data.themeDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val repository: ThemePreferenceRepository
) : ViewModel() {

    val themeOption: StateFlow<ThemeOption> = repository.themeOption.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ThemeOption.System
    )

    fun setTheme(option: ThemeOption) {
        viewModelScope.launch {
            repository.setTheme(option)
        }
    }

    companion object {
        fun factory(application: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repository = ThemePreferenceRepository(application.themeDataStore)
                    return ThemeViewModel(repository) as T
                }
            }
    }
}
