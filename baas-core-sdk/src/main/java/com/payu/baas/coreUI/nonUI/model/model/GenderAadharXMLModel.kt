package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName

class GenderAadharXMLModel() {
    var formData: Int? = null

    @SerializedName("aadhaarXMLData")
    var gender: String? = null
    var passportOCRData: String? = null
    var panOCRData: String? = null
    var voterOCRData: String? = null
    var match: String? = null
    var matchMeta: String? = null
}