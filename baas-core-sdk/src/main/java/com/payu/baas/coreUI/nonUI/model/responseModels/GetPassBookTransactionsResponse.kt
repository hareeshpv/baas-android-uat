package com.payu.baas.coreUI.nonUI.model.responseModels

import com.google.gson.annotations.SerializedName

class GetPassBookTransactionsResponse : ApiResponse() {

    @SerializedName("userMessage")
    var userMessage: String? = null

    @SerializedName("systemMessage")
    var systemMessage: String? = null

    @SerializedName("code")
    var code: String? = null

    @SerializedName("transactionList")
    var transactionList: ArrayList<GetPassBookTransactionDetails> = arrayListOf()

    @SerializedName("page")
    var page: String? = null
}