package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse

class UsageHistory: ApiResponse()  {
    @SerializedName("advanceAvailable")
    var advanceAvailable: Int? = null

    @SerializedName("advanceUsed")
    var advanceUsed: Int? = null

    @SerializedName("feesCharged")
    var feesCharged: Int? = null

    @SerializedName("latePaymentFees")
    var latePaymentFees: Int? = null

    @SerializedName("paidBack")
    var paidBack: Int? = null

}
