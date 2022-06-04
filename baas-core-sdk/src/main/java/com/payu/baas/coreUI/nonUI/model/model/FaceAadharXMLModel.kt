package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName

class FaceAadharXMLModel() {

    @SerializedName("matchScore")
    var matchScore: Double? = 0.0
//    var agentStatus: String? = null

    @SerializedName("matchMeta")
    var matchMeta: String? = null
//    var aadhaarBase64: String? = null
}