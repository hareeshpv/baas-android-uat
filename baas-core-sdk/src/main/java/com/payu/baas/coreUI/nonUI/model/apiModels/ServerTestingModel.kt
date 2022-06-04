package com.payu.baas.coreUI.nonUI.model.apiModels

import android.content.Context
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.enums.RequestMethod
import com.payu.baas.coreUI.nonUI.enums.TokenType
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.ServerTestingResponse
import com.payu.baas.coreUI.nonUI.util.BaaSConstants

class ServerTestingModel (context: Context, requestMap: HashMap<String, Any>, sdkCallback: SdkCallback
    ) : ApiModel(
    context, requestMap, ApiName.SERVER_CALL, sdkCallback
    ) {
        override fun getRelativeUrl(): String = """${BaaSConstants.BS_URL_INITIAL_SERVER_CALL}actuator/health"""
        override fun getRequestMethod(): RequestMethod = RequestMethod.GET
        override fun getApiType(): ApiType = ApiType.POST_LOGIN
        override fun getTokenType(): TokenType = TokenType.SERVER_TOKEN
        override fun getResponseModel(): ApiResponse = ServerTestingResponse()
    }
