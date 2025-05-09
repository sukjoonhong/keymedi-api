package kr.co.medipanda.portal.support

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class S3CsoFileUploader : CsoFileUploader {
    //TODO: not implemented
    override fun upload(file: MultipartFile): String {
        return "/mock-upload/${file.originalFilename}"
    }
}