package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName

class ValidatePanRequestModel {
    @SerializedName("panNumber")
    var panNumber: String? = null
}