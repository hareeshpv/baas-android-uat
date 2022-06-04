package com.payu.baas.coreUI.nonUI.model.responseModels

import com.google.gson.annotations.SerializedName
import com.payu.baas.coreUI.nonUI.model.model.BeneficiaryBankTransferLogModel

class BeneficiaryBankTransferResponse : ApiResponse() {
    @SerializedName("userMessage")
    var userMessage: String? = null

    @SerializedName("systemMessage")
    var systemMessage: String? = null

    @SerializedName("code")
    var code: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("txn_logs")
    var transactionLogsList: ArrayList<BeneficiaryBankTransferLogModel>? = null

}