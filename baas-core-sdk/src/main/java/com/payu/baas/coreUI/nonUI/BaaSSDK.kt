package com.payu.baas.coreUI.nonUI

import android.content.Context
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.model.ApiDetails
import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.RequestDataGenerator
import com.payu.baas.coreUI.nonUI.model.SdkConfig
import com.payu.baas.coreUI.nonUI.singleton.ApiCallManager

object BaaSSDK {

    private var sdkConfig: SdkConfig = SdkConfig()

    @JvmStatic
    fun init(sdkConfig: SdkConfig) {
        BaaSSDK.sdkConfig = sdkConfig
    }
    @JvmStatic
    fun callApi(context: Context, apiDetails: ApiDetails, sdkCallback: SdkCallback) {
        val requestData = RequestDataGenerator(apiDetails).getRequestData()
        if (requestData.isValid) {
            ApiCallManager(context).executeApi(
                apiDetails.apiName,
                requestData.result!!,
                sdkCallback
            )
        } else {
            sdkCallback.onError(ErrorResponse(requestData.errorMessage, requestData.errorCode))
        }
    }
}