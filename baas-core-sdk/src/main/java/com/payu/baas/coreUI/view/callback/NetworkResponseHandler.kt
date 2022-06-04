package com.payu.baas.coreUI.view.callback

import com.payu.baas.coreUI.nonUI.model.ErrorResponse

interface NetworkResponseHandler {
    fun onSuccess(response: String)
    fun onError(errorResponse: ErrorResponse)
}