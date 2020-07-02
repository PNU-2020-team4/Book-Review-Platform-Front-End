package com.team4.bookreview.dto

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName


class ServerResponse(
    @SerializedName("resultCode") var resultCode: Int = 0,
    @SerializedName("message") var message: String? = null,
    @SerializedName("dataObject") var dataObject: JsonObject? = null,
    @SerializedName("dataList") var dataList: JsonArray? = null) {
    override fun toString(): String {
        return "ServerResponse(resultCode=$resultCode, message=$message, dataObject=$dataObject, dataList=$dataList)"
    }
}