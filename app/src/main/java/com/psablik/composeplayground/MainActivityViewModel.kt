package com.psablik.composeplayground

import androidx.lifecycle.ViewModel
import com.psablik.composeplayground.state.MainActivityViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

const val CONTAINER_SIZE_MIN = 1
const val CONTAINER_SIZE_MAX = 10

class MainActivityViewModel : ViewModel() {
    /**
     * state jako [StateFlow] zapewnia emisje do jego 'słuchaczy' dopiero gdy jego wartość się zmienia,
     * jeśli przyjdzie do niego taka sama wartość jaką obecnie posiada to nie emituje tego dalej
     */
    private val _state = MutableStateFlow(MainActivityViewState())
    val state = _state.asStateFlow()

    fun changeContainerSize(newSize: Int) {
        _state.update { currentState ->
            currentState.copy(size = newSize)
        }
    }

    fun generateRandomSize() {
        var newSize: Int

        do {
            newSize = Random.nextInt(
                from = CONTAINER_SIZE_MIN,
                until = CONTAINER_SIZE_MAX
            )
        } while (newSize == state.value.size)

        /**
         * Każdorazowe zmiany nowych wartości w stanie powodują rekompozycje (przebudowanie zmienionych elementów) powiązanego z nim widoku.
         * Compose nie powoduje rekompozycji każdego elementu ale na początku sprawdza które elementy korzystają z wartości,
         * które się zmieniły i tylko je przerysowuje na nowo z nową wartością. Można widzieć rekompozycje na żywo w Layout Inspector:
         * https://developer.android.com/jetpack/compose/tooling/layout-inspector
         */
        _state.update { currentState ->
            currentState.copy(size = newSize)
        }
    }
}
