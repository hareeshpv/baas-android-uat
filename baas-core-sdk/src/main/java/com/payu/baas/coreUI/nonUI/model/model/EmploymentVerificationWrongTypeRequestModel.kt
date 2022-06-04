package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName

class EmploymentVerificationWrongTypeRequestModel {
    @SerializedName("pan")
    var pan: String? = null
    @SerializedName("brandEmpId")
    var brandEmpId: Int? = null
    @SerializedName("mobile")
    var mobile: String? = null
}