package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName

class SetPasscodeRequestModel {
    var newPasscode: String? = null
    var oldPasscode: String? = null
}