package com.payu.baas.coreUI.nonUI.model

import com.payu.baas.coreUI.nonUI.enums.ApiName
import com.payu.baas.coreUI.nonUI.model.params.ApiParams

data class ApiDetails(val apiName: ApiName, val apiParams: ApiParams)