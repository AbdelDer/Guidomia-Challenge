package com.kader.guidomia_challenge.presentation.ui.home

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kader.guidomia_challenge.R
import com.kader.guidomia_challenge.common.Resource
import com.kader.guidomia_challenge.doamin.model.Car
import com.kader.guidomia_challenge.doamin.use_case.GetAllCarsUseCase
import com.kader.guidomia_challenge.doamin.use_case.GetAllMakeExistingUseCase
import com.kader.guidomia_challenge.doamin.use_case.GetAllModelExistingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCarsUseCase: GetAllCarsUseCase,
    private val getAllMakeExistingUseCase: GetAllMakeExistingUseCase,
    private val getAllModelExistingUseCase: GetAllModelExistingUseCase,
    private val resource: Resources
) : ViewModel() {

    private val _cars = MutableStateFlow<List<Car>>(emptyList())
    val cars = _cars.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _makeFilterValues = MutableStateFlow<List<String>>(emptyList())
    val makeFilterValues = _makeFilterValues.asStateFlow()

    private val _modelFilterValues = MutableStateFlow<List<String>>(emptyList())
    val modelFilterValues = _modelFilterValues.asStateFlow()

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
                    updateMakeFilterValues()
                    updateModelsFilterValues()
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


    private fun updateMakeFilterValues() {
        viewModelScope.launch {
            getAllMakeExistingUseCase().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _eventFlow.emit(
                            UIEvent.ShowToast(
                                result.message ?: resource.getString(R.string.unknown_error_message)
                            )
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val currentList = result.data?.let { it.toMutableList() } ?: mutableListOf()
                        currentList.add(0, "Any Make")
                        _makeFilterValues.value = currentList.toList()
                    }
                }
            }
        }
    }

    private fun updateModelsFilterValues() {
        viewModelScope.launch {
            getAllModelExistingUseCase().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _eventFlow.emit(
                            UIEvent.ShowToast(
                                result.message ?: resource.getString(R.string.unknown_error_message)
                            )
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        val currentList = result.data?.let { it.toMutableList() } ?: mutableListOf()
                        currentList.add(0, "Any Model")
                        _modelFilterValues.value = currentList.toList()
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowLoading(val show: Boolean) : UIEvent()
        data class ShowToast(val message: String) : UIEvent()
    }
}