package com.payu.baas.coreUI.nonUI.singleton

import android.content.Context
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.network.ApiExecutor
import com.payu.baas.coreUI.nonUI.model.ApiHandler
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.apiModels.ApiModel
import com.payu.baas.coreUI.nonUI.model.apiModels.ApiModelFactory
import com.payu.baas.coreUI.nonUI.model.apiModels.GetClientTokenApiModel
import com.payu.baas.coreUI.nonUI.model.apiModels.SendOtpApiModel
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.storage.SessionManager

class ApiCallManager(val context: Context) {
    internal fun executeApi(
        apiName: ApiName,
        requestData: HashMap<String, Any>,
        sdkCallback: SdkCallback
    ) {
        val apiModel = ApiModelFactory(context, apiName, requestData, sdkCallback).getApiModel()
        when (apiModel.getApiType()) {
            ApiType.PRE_LOGIN -> {
                if (SessionManager.getInstance(context).accessToken != null)
                    ApiExecutor(apiModel, ApiHandler(apiModel)).call()
                else
                    fetchClientToken(SendOtpApiModel(context, requestData, sdkCallback))
            }
            else -> ApiExecutor(apiModel, ApiHandler(apiModel)).call()
        }
    }

    private fun fetchClientToken(apiModel: ApiModel) {
        val clientTokenApiModel =
            GetClientTokenApiModel(context, apiModel.requestMap, object : SdkCallback {
                override fun onSuccess(apiResponse: ApiResponse) {
                    ApiExecutor(apiModel, ApiHandler(apiModel)).call()
                }

                override fun onError(errorResponse: ErrorResponse) {
                    apiModel.sdkCallback.onError(errorResponse)
                }
            })
        ApiExecutor(clientTokenApiModel, ApiHandler(clientTokenApiModel)).call()
    }
}