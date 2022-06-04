package com.payu.baas.coreUI.nonUI.model.responseModels

class KarzaUserTokenResult {
    var data //user data in inner userToken field
            : KarzaUserData? = null
    var success = false
    var reason: String? = null
}