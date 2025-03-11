package com.example.listdummy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listdummy.model.HiringDataClass
import com.example.listdummy.network.HiringRepository
import com.example.listdummy.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HiringViewModel @Inject constructor(val repository: HiringRepository): ViewModel() {

    private val _hiringState = MutableStateFlow<Resource<List<HiringDataClass>>>(Resource.Loading)
    val hiringState: StateFlow<Resource<List<HiringDataClass>>> = _hiringState

     fun getHiringList(){
         viewModelScope.launch {
             _hiringState.value = Resource.Loading // Show loading state
             // Usage
             val hiringListResource = repository.getHiringList()
             _hiringState.value = processHiringList(hiringListResource)
         }
    }

    private fun processHiringList(resource: Resource<List<HiringDataClass>>): Resource<List<HiringDataClass>> {
        return when (resource) {
            is Resource.Success -> {
                val filteredAndSortedList = resource.data
                    .filter { !it.name.isNullOrBlank() } // Remove items with blank or null names
                    .sortedWith(compareBy({ it.listId }, { it.name })) // Sort by listId, then by name

                Resource.Success(filteredAndSortedList)
            }

            is Resource.Error -> {
                Resource.Error(resource.message, resource.throwable) // Return the error as is
            }

            is Resource.Loading -> {
                Resource.Loading // Return loading state as is
            }
        }
    }



}