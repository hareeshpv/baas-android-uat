package com.payu.baas.coreUI.nonUI.model.apiModels

import android.content.Context
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.ResponseModelCreator
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.GetUserStateResponse
import com.payu.baas.coreUI.nonUI.network.NetworkHeader
import com.payu.baas.coreUI.nonUI.network.NetworkResponseHandler
import com.payu.baas.coreUI.nonUI.storage.SessionManager
import com.payu.baas.coreUI.nonUI.util.BaaSConstants
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.enums.RequestMethod
import com.payu.baas.coreUI.nonUI.enums.TokenType

class GetUserStateApiModel(
    context: Context,
    requestMap: HashMap<String, Any>,
    sdkCallback: SdkCallback
) : ApiModel(
    context, requestMap, ApiName.GET_USER_STATE, sdkCallback
) {
    override fun getRelativeUrl(): String = "user/state"
    override fun getRequestMethod(): RequestMethod = RequestMethod.POST
    override fun getApiType(): ApiType = ApiType.PRE_LOGIN
    override fun getTokenType(): TokenType = TokenType.DEVICE_BINDING_ID
    override fun getResponseModel(): ApiResponse = GetUserStateResponse()

    override fun getResponseHandler(): NetworkResponseHandler {
        return object : NetworkResponseHandler {
            override fun onSuccess(response: String) {
                val apiResponse = ResponseModelCreator(this@GetUserStateApiModel, response).getResponse()
                SessionManager.getInstance(context).userStatusCode = (apiResponse as GetUserStateResponse).message.toString()
                this@GetUserStateApiModel.sdkCallback.onSuccess(apiResponse)
            }
            override fun onError(errorResponse: ErrorResponse) {
                this@GetUserStateApiModel.sdkCallback.onError(errorResponse)
            }

        }
    }
    override fun getAdditionalHeader(): NetworkHeader {
        return NetworkHeader().apply {
            SessionManager.getInstance(context).accessToken?.let {
                put(BaaSConstants.BS_KEY_ACCESS_TOKEN, it)
            }
        }
    }
}