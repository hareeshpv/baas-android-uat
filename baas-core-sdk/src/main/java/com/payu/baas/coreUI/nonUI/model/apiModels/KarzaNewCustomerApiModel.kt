package com.payu.baas.coreUI.nonUI.model.apiModels

import android.content.Context
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.enums.RequestMethod
import com.payu.baas.coreUI.nonUI.enums.TokenType
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.model.ErrorResponse
import com.payu.baas.coreUI.nonUI.model.ResponseModelCreator
import com.payu.baas.coreUI.nonUI.network.NetworkHeader
import com.payu.baas.coreUI.nonUI.network.NetworkResponseHandler
import com.payu.baas.coreUI.nonUI.storage.SessionManager
import com.payu.baas.coreUI.nonUI.util.BaaSConstants
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.KarzaNewCustomerResponse

class KarzaNewCustomerApiModel(
    context: Context,
    requestMap: HashMap<String, Any>,
    sdkCallback: SdkCallback
) : ApiModel(
    context, requestMap, ApiName.KARZA_ADD_NEW_CUSTOMER, sdkCallback
) {
    override fun getRelativeUrl(): String = "https://app.karza.in/test/videokyc/api/v2/okyc-customers"
    override fun getRequestMethod(): RequestMethod = RequestMethod.POST
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
            put("Referer","https://app.karza.in/test/okyc/add-customer")
        }
    }
    override fun getResponseModel(): ApiResponse = KarzaNewCustomerResponse()
    override fun getResponseHandler(): NetworkResponseHandler {
        return object : NetworkResponseHandler {
            override fun onSuccess(response: String) {
                val apiResponse =
                    ResponseModelCreator(this@KarzaNewCustomerApiModel, response).getResponse()
                var  transactionId =
                    SessionManager.getInstance(context).karzaTransactionId
//                if(transactionId.isNullOrEmpty()){
                    SessionManager.getInstance(context).karzaTransactionId =
                        (apiResponse as KarzaNewCustomerResponse).results!!.data!!.transactionId
//                }
              /*  Logger.getLogger(
                    "karza_transactionId",
                    (apiResponse as KarzaNewCustomerResponse).results!!.data!!.transactionId!!
                )*/
                this@KarzaNewCustomerApiModel.sdkCallback.onSuccess(apiResponse)
            }

            override fun onError(errorResponse: ErrorResponse) {
                this@KarzaNewCustomerApiModel.sdkCallback.onError(errorResponse)
            }

        }
    }
}