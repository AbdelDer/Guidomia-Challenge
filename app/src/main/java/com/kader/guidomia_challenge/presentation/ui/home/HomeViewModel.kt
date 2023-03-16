package com.kader.guidomia_challenge.presentation.ui.home

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kader.guidomia_challenge.R
import com.kader.guidomia_challenge.common.Resource
import com.kader.guidomia_challenge.doamin.model.Car
import com.kader.guidomia_challenge.doamin.use_case.GetAllCarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCarsUseCase: GetAllCarsUseCase,
    private val resource: Resources
) : ViewModel() {

    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val cars = _cars.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getCars()
    }

    private fun getCars() {
        getAllCarsUseCase(
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UIEvent.ShowLoading(
                            false
                        )
                    )
                    _cars.value = result.data ?: emptyList()
                }
                is Resource.Loading -> {
                    _eventFlow.emit(
                        UIEvent.ShowLoading(
                            true
                        )
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UIEvent.ShowLoading(
                            false
                        )
                    )
                    _eventFlow.emit(
                        UIEvent.ShowToast(
                            result.message ?: resource.getString(R.string.unknown_error_message)
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class UIEvent {
        data class ShowLoading(val show: Boolean) : UIEvent()
        data class ShowToast(val message: String) : UIEvent()
    }
}