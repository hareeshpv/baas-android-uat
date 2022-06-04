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
import com.payu.baas.coreUI.nonUI.model.responseModels.CreateBeneficiaryResponse
import com.payu.baas.coreUI.nonUI.network.NetworkResponseHandler
import com.payu.baas.coreUI.nonUI.storage.SessionManager

class CreateBeneficiaryApiModel(
    context: Context,
    requestMap: HashMap<String, Any>,
    sdkCallback: SdkCallback
) : ApiModel(
    context, requestMap, ApiName.ADD_BENEFICIARY, sdkCallback
) {
    override fun getRelativeUrl(): String = "user/beneficiary"
    override fun getRequestMethod(): RequestMethod = RequestMethod.POST
    override fun getApiType(): ApiType = ApiType.POST_LOGIN
    override fun getTokenType(): TokenType = TokenType.ACCESS_TOKEN
    override fun getResponseModel(): ApiResponse = CreateBeneficiaryResponse()
    override fun getResponseHandler(): NetworkResponseHandler {
        return object : NetworkResponseHandler {
            override fun onSuccess(response: String) {
                val apiResponse = ResponseModelCreator(this@CreateBeneficiaryApiModel, response).getResponse()
                SessionManager.getInstance(context).beneficiaryIFSCECode = (apiResponse as CreateBeneficiaryResponse).userBeneficiary?.ifsc.toString()
                SessionManager.getInstance(context).beneficiaryId = (apiResponse as CreateBeneficiaryResponse).userBeneficiary?.beneficiaryId.toString()
                this@CreateBeneficiaryApiModel.sdkCallback.onSuccess(apiResponse)
            }
            override fun onError(errorResponse: ErrorResponse) {
                this@CreateBeneficiaryApiModel.sdkCallback.onError(errorResponse)
            }

        }
    }
}