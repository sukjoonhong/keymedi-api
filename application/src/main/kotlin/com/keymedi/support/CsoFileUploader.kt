package com.keymedi.support

import org.springframework.web.multipart.MultipartFile

interface CsoFileUploader {
    fun upload(file: MultipartFile): String
}
