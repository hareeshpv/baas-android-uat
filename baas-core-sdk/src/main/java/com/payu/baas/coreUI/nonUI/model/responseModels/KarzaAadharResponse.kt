package com.payu.baas.coreUI.nonUI.model.responseModels

import com.google.gson.annotations.SerializedName

class KarzaAadharResponse  : ApiResponse() {
    @SerializedName("userMessage")
    var userMessage: String? = null

    @SerializedName("systemMessage")
    var systemMessage: String? = null

    @SerializedName("code")
    var code: String? = null

    @SerializedName("requestId")
    var requestId: String? = null
    var address: String? = null
    var zipFileBase64: String? = null
    var sharecode: String? = null
    @SerializedName("score")
    var score: Double? = null

}