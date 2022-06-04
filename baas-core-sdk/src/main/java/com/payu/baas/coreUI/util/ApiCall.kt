package com.payu.baas.coreUI.util

import com.payu.baas.coreUI.nonUI.BaaSSDK
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.model.ApiDetails
import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.params.ApiParams
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse

class ApiCall {

     companion object{
         fun callAPI(apiName: ApiName, apiParams: ApiParams, apiHelper: ApiHelperUI) {
             com.payu.baas.coreUI.app.BaasUIApp.ctx?.let {
                 BaaSSDK.callApi(
                     it,
                     ApiDetails(apiName, apiParams),
                     object : SdkCallback {
                         override fun onSuccess(apiResponse: ApiResponse) {
                             apiHelper.onSuccess(apiResponse)
                         }


                         override fun onError(errorResponse: ErrorResponse) {
                             apiHelper.onError(errorResponse)
                         }
                     })
             }

         }
     }



}