package com.keymedi.utils

import java.time.LocalDateTime
import java.time.ZoneOffset

const val DEFAULT_NICK_NAME = "익명CSO"

fun utcNow(): LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)