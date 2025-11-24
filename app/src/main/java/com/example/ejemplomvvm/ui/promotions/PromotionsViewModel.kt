package com.example.ejemplomvvm.ui.promotions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemplomvvm.data.Giveaway
import com.example.ejemplomvvm.data.GiveawayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PromotionsViewModel : ViewModel() {

    private val repository = GiveawayRepository()

    private val _giveaways = MutableStateFlow<List<Giveaway>>(emptyList())
    val giveaways: StateFlow<List<Giveaway>> = _giveaways

    init {
        fetchGiveaways()
    }

    private fun fetchGiveaways() {
        viewModelScope.launch {
            _giveaways.value = repository.getGiveaways()
        }
    }
}