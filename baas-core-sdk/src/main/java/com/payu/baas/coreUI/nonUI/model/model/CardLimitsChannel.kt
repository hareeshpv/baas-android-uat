package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse


class CardLimitsChannel: ApiResponse()  {
    @SerializedName("perTransaction")
    var perTransaction: Long? = null

    @SerializedName("daily")
    var daily: Long? = null

}
