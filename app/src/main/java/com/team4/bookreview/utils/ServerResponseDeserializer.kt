package com.team4.bookreview.utils

import com.team4.bookreview.dto.ServerResponse
import com.google.gson.*
import java.lang.reflect.Type

class ServerResponseDeserializer: JsonDeserializer<ServerResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ServerResponse {
        val item = ServerResponse()
        json?.let {
            val jsonObject = it.asJsonObject
            val resultCode = jsonObject.get("resultCode").asInt
            val message = jsonObject.get("message").asString
            var dataObject: JsonObject? = null
            if (!jsonObject.get("dataObject").isJsonNull) {
                dataObject = jsonObject.getAsJsonObject("dataObject")
            }
            var dataList: JsonArray? = null
            if (!jsonObject.get("dataList").isJsonNull) {
                dataList = jsonObject.getAsJsonArray("dataList")
            }
            item.resultCode = resultCode
            item.message = message
            item.dataObject = dataObject
            item.dataList = dataList
        }
        return item
    }
}
