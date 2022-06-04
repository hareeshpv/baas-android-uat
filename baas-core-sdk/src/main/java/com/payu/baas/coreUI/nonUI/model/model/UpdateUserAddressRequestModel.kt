package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName

class UpdateUserAddressRequestModel {
    @SerializedName("address1")
    var address1: String? = null
    @SerializedName("address2")
    var address2: String? = null
}