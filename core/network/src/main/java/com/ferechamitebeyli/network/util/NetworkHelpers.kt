package com.ferechamitebeyli.network.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.ferechamitebeyli.network.R
import com.ferechamitebeyli.network.dto.common.response.GenericResponseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

/**
 * Utility function to fetch and return the response that oBilet Mobile api sends.
 *
 * @param ResponseType represents the response type.
 * @param MappedResponseType represents the [ResponseType] that will be mapped out to this type via [mapFromModel] function.
 * @param dispatcher is [CoroutineDispatcher] which will execute the [apiCall] in.
 * @param apiCall is service function that will fetch the data from oBilet Mobile api.
 */
suspend fun <ResponseType, MappedResponseType> safeApiCall(
    mapFromModel: ((List<ResponseType>) -> MappedResponseType),
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Response<GenericResponseModel<ResponseType>>
): Flow<Resource<MappedResponseType>> {
    return flow {
        try {
            emit(Resource.Loading())
            //fetch the response from the api within the given coroutine context.
            val response = withContext(dispatcher) { apiCall() }
            if (response.isSuccessful) {
                /* if the response is success
                 * which means response.code is in between 200-300
                 * we are checking if the data is null or not
                 * if not null, we are mapping it out and return
                 * if null, we are return error with the corresponding message.
                 */
                response.body()?.let { model ->
                    if (model.status == ResponseStatusEnum.SUCCESS.status) {
                        model.data?.let { list ->
                            emit(Resource.Success(mapFromModel.invoke(list)))
                        }
                    } else {
                        emit(
                            Resource.Error(
                                text = UiText.DynamicString(model.userMessage)
                            )
                        )
                    }

                } ?: emit(
                    Resource.Error(
                        text = UiText.StringResource(R.string.message_safeApiCall_noResult)
                    )
                )
            } else {
                emit(
                    Resource.Error(text = UiText.StringResource(R.string.message_safeApiCall_operationFailed))
                )

            }
        } catch (exception: Exception) {
            //handling exceptions
            when (exception) {
                is TimeoutCancellationException -> {
                    emit(Resource.Error(text = UiText.StringResource(R.string.message_safeApiCall_timeoutError)))
                }

                is IOException -> {
                    emit(
                        Resource.Error(
                            text = exception.localizedMessage?.let { message ->
                                UiText.DynamicString(message)
                            }
                                ?: UiText.StringResource(R.string.message_safeApiCall_operationFailed)
                        ))
                }

                else -> {
                    emit(Resource.Error(text = UiText.StringResource(R.string.message_safeApiCall_unknownError)))
                }
            }
        }
    }
}