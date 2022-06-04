package com.payu.baas.coreUI.nonUI.model.responseModels

import com.google.gson.annotations.SerializedName
import com.payu.baas.coreUI.nonUI.model.model.AdvanceUsageHistory
import com.payu.baas.coreUI.nonUI.model.model.CurrentUsage

class GetSalaryAdvanceInfoResponse : ApiResponse() {
    @SerializedName("userMessage")
    var userMessage: String? = null

    @SerializedName("systemMessage")
    var systemMessage: String? = null

    @SerializedName("code")
    var code: String? = null

    var advanceAvailable: String? = null
    @SerializedName("displayMessage")
    var displayMsgForLockedAccount: String? = ""
    @SerializedName("ctaText")
    var buttonTextForLockedAccount: String? = ""

    @SerializedName("currentUsage")
    var currentUsage: CurrentUsage? = null

    @SerializedName("usageHistory")
    var usageHistory: List<AdvanceUsageHistory>? = null

    @SerializedName("blocked")
    var blocked: Boolean? = null

    @SerializedName("active")
    var active: Boolean? = null

}