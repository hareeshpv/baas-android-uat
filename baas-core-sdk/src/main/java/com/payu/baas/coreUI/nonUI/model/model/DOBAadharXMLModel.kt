package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName

class DOBAadharXMLModel() {
    var formData: Int? = null

    @SerializedName("aadhaarXMLData")
    var dob: String? = null
    var passportOCRData: String? = null
    var panOCRData: String? = null
    var voterOCRData: String? = null
    var dlOCRData: String? = null
    var postpaidMobileBillData: String? = null
    var match: String? = null
    var matchMeta: String? = null
}