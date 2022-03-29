package com.example.webflux.basic

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface BasicRepository {
    fun save(basic: Basic): Mono<Basic>
    fun findById(id: Long): Mono<Basic>
    fun findAll(): Mono<List<Basic>>
    fun findFluxAll(): Flux<Basic>
    fun deleteById(id: Long): Mono<Basic>
}