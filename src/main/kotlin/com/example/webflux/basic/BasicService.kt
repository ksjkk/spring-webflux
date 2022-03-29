package com.example.webflux.basic

import org.springframework.stereotype.Service

@Service
class BasicService(
    private val basicRepository: BasicRepository
) {

    fun save(basic: Basic) = basicRepository.save(basic)
    fun findById(id: Long) = basicRepository.findById(id)
    fun findAll() = basicRepository.findAll()
    fun findFluxAll() = basicRepository.findFluxAll()
    fun deleteById(id: Long) = basicRepository.deleteById(id)
}