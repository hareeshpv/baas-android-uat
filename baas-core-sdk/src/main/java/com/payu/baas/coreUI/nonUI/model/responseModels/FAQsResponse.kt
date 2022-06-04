package com.payu.baas.coreUI.nonUI.model.responseModels

import com.google.gson.annotations.SerializedName

class FAQsResponse : ApiResponse() {
    @SerializedName("value")
    var value: String? = null

}