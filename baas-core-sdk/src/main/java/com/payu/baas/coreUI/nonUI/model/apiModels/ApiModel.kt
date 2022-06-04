package com.payu.baas.coreUI.nonUI.model.apiModels

import android.content.Context
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.enums.ContentType
import com.payu.baas.coreUI.nonUI.enums.RequestMethod
import com.payu.baas.coreUI.nonUI.enums.TokenType
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.model.RequestCreator
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.network.NetworkHeader
import com.payu.baas.coreUI.nonUI.network.NetworkHeaderFactory
import com.payu.baas.coreUI.nonUI.network.NetworkResponseHandler
import com.payu.baas.coreUI.nonUI.network.NetworkResponseHandlerFactory

abstract class ApiModel(
    val context: Context,
    val requestMap: HashMap<String, Any>,
    val apiName: ApiName,
    val sdkCallback: SdkCallback
) {
    abstract fun getRelativeUrl(): String
    abstract fun getRequestMethod(): RequestMethod
    abstract fun getApiType(): ApiType
    abstract fun getTokenType(): TokenType
    abstract fun getResponseModel(): ApiResponse
    open fun getContentType(): ContentType =  ContentType.APPLICATION_JSON
    fun getHeader(): NetworkHeader = NetworkHeaderFactory(this).getHeader()

    open fun getAdditionalHeader(): NetworkHeader = NetworkHeader()
    open fun getRequestData(): String = RequestCreator(getContentType(), requestMap).createRequest()
    open fun getResponseHandler(): NetworkResponseHandler =
        NetworkResponseHandlerFactory(this).getResponseHandler()
}