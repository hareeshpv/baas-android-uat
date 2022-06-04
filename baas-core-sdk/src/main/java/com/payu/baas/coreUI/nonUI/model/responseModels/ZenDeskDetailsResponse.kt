package com.payu.baas.coreUI.nonUI.model.responseModels

import com.google.gson.annotations.SerializedName

class ZenDeskDetailsResponse : ApiResponse() {
    @SerializedName("zendeskUrl")
    var zendeskUrl: String? = null

    @SerializedName("appId")
    var appId: String? = null

    @SerializedName("clientId")
    var clientId: String? = null

}