package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName
import com.payu.baas.coreUI.nonUI.model.responseModels.ApiResponse


class TransactionMode : ApiResponse() {

    @SerializedName("allow")
    var allow: Boolean? = null

    @SerializedName("channel")
    var channel: String? = null

}
