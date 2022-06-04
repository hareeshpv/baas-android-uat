package com.payu.baas.coreUI.nonUI.network

import com.payu.baas.coreUI.nonUI.model.ErrorResponse

interface NetworkResponseHandler {
    fun onSuccess(response: String)
    fun onError(errorResponse: ErrorResponse)
}