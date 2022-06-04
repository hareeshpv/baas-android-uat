package com.payu.baas.coreUI.nonUI.model.responseModels

class KarzaUserTokenResponse : ApiResponse() {
    var statusCode: Int? = null
    var requestId: String? = null
    var result: KarzaUserTokenResult? = null
    var error: String? = null
    var status: Int? = null
}