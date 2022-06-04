package com.payu.baas.coreUI.nonUI.model.apiModels

import android.content.Context
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.enums.RequestMethod
import com.payu.baas.coreUI.nonUI.enums.TokenType
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.ResponseModelCreator
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.KarzaUserTokenResponse
import com.payu.baas.coreUI.nonUI.network.NetworkHeader
import com.payu.baas.coreUI.nonUI.network.NetworkResponseHandler
import com.payu.baas.coreUI.nonUI.storage.SessionManager
import com.payu.baas.coreUI.nonUI.util.BaaSConstants

class KarzaCustomerTokenApiModel(
    context: Context,
    requestMap: HashMap<String, Any>,
    sdkCallback: SdkCallback
) : ApiModel(
    context, requestMap, ApiName.KARZA_GENERATE_CUSTOMER_TOKEN, sdkCallback
) {
    override fun getRelativeUrl(): String = "https://app.karza.in/test/videokyc/api/v2/generate-usertoken/" +
            "${requestMap[BaaSConstants.BS_KEY_KARZA_TRANSACTION_ID]}"
    override fun getRequestMethod(): RequestMethod = RequestMethod.GET
    override fun getApiType(): ApiType = ApiType.POST_LOGIN
    override fun getTokenType(): TokenType = TokenType.KARZA_TOKEN
    override fun getAdditionalHeader(): NetworkHeader {
        return NetworkHeader().apply {
            SessionManager.getInstance(context).karzaToken?.let {
                put(
                    BaaSConstants.BS_KEY_KARZA_TOKEN,
                    it
                )
            }
        }
    }
    override fun getResponseModel(): ApiResponse = KarzaUserTokenResponse()
    override fun getResponseHandler(): NetworkResponseHandler {
        return object : NetworkResponseHandler {
            override fun onSuccess(response: String) {
                val apiResponse =
                    ResponseModelCreator(this@KarzaCustomerTokenApiModel, response).getResponse()
            SessionManager.getInstance(context).karzaUserToken =
                    (apiResponse as KarzaUserTokenResponse).result!!.data!!.userToken
                this@KarzaCustomerTokenApiModel.sdkCallback.onSuccess(apiResponse)
            }

            override fun onError(errorResponse: ErrorResponse) {
                this@KarzaCustomerTokenApiModel.sdkCallback.onError(errorResponse)
            }

        }
    }
}