package com.ng.revoluttestapp.util

import com.google.gson.JsonElement

inline fun JsonElement.getString(tag: String) = asJsonObject.get(tag).asString
inline fun JsonElement.getObject(tag: String) = asJsonObject.get(tag).asJsonObject