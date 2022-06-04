package com.payu.baas.coreUI.nonUI.interfaces

import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse

interface SdkCallback {
    fun onSuccess(apiResponse: ApiResponse)
    fun onError(errorResponse: ErrorResponse)
}