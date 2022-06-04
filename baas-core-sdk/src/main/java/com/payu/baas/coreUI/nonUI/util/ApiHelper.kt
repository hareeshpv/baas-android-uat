package com.payu.baas.coreUI.nonUI.util

import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse

interface ApiHelper {

    fun onSuccess(apiResponse: ApiResponse)

    fun onError(errorResponse: ErrorResponse)

}