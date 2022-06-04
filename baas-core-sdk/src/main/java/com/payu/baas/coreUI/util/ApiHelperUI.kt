package com.payu.baas.coreUI.util

import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse


interface ApiHelperUI {

    fun onSuccess(apiResponse: ApiResponse)

    fun onError(errorResponse: ErrorResponse)

}