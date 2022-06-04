package com.payu.baas.coreUI.nonUI.network

import com.google.gson.Gson
import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.apiModels.ApiModel
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse

class NetworkResponseHandlerFactory(val apiModel: ApiModel) {
    fun getResponseHandler(): NetworkResponseHandler {
        return defaultResponseHandler()
    }

    private fun defaultResponseHandler(): NetworkResponseHandler {
        return object : NetworkResponseHandler {
            override fun onSuccess(response: String) {
                val gson = Gson()
                val responseModel = gson.fromJson(response, apiModel.getResponseModel()::class.java)
                if (responseModel != null) {
                    responseModel.rawResponse = response
                    apiModel.sdkCallback.onSuccess(responseModel)
                }else{
                    apiModel.sdkCallback.onSuccess(ApiResponse())
                }
            }

            override fun onError(errorResponse: ErrorResponse) {
                apiModel.sdkCallback.onError(errorResponse)
            }

        }
    }
}