package com.payu.baas.coreUI.nonUI.model.apiModels

import android.content.Context
import com.payu.baas.coreUI.nonUI.enums.ApiType
import com.payu.baas.coreUI.nonUI.enums.RequestMethod
import com.payu.baas.coreUI.nonUI.enums.TokenType
import com.payu.baas.coreUI.nonUI.interfaces.SdkCallback
import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse
import com.payu.baas.coreUI.nonUI.model.responseModels.GetBeneficiaryResponse
import com.payu.baas.coreUI.nonUI.util.BaaSConstants

class GetBeneficiaryApiModel(
    context: Context,
    requestMap: HashMap<String, Any>,
    sdkCallback: SdkCallback
) : ApiModel(
    context, requestMap, ApiName.GET_BENEFICIARY, sdkCallback
) {
    override fun getRelativeUrl(): String =
        "user/beneficiary?page=${requestMap[BaaSConstants.BS_KEY_PAGE]}&size=${requestMap[BaaSConstants.BS_KEY_SIZE]}&sort=${requestMap[BaaSConstants.BS_KEY_SORT]}"

    override fun getRequestMethod(): RequestMethod = RequestMethod.GET
    override fun getApiType(): ApiType = ApiType.POST_LOGIN
    override fun getTokenType(): TokenType = TokenType.ACCESS_TOKEN
    override fun getRequestData(): String = ""
    override fun getResponseModel(): ApiResponse = GetBeneficiaryResponse()
}