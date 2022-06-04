package com.payu.baas.coreUI.nonUI.model.model

import com.google.gson.annotations.SerializedName

class AddressAadharXMLModel() {

    @SerializedName("formData")
    var formData: Int? = null

    @SerializedName("aadhaarXMLData")
    var address: String? = null
    var passportOCRData: String? = null
    var panOCRData: String? = null
    var voterOCRData: String? = null
    var electricityBillData: String? = null
    var postpaidMobileBillData: String? = null
    var pngBillData: String? = null
    var telephoneBillData: String? = null
    var leaveAndLicenseData: String? = null
    var propertyTaxData: String? = null
    var match: String? = null
    var matchMeta: String? = null
}