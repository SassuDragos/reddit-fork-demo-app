package com.sogard.domain

/***
 * This class will be used in cases when LiveData bridges the communication between UI <-> Domain /
 * Domain <-> Data.
 */
//TODO:
sealed class ResultState {

    /**
     * A state of [data] which shows that we know there is still an update to come.
     */
    object Loading : ResultState()

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : ResultState()

    /**
     * A state to show a [throwable] is thrown beside the [lastData] which is cached.
     */
    data class Error(val throwable: Throwable) : ResultState()
}