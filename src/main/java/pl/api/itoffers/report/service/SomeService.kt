package pl.api.itoffers.report.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SomeService {

    @Value("\${application.report.destinationEmail}")
    lateinit var reportToEmail: String
}