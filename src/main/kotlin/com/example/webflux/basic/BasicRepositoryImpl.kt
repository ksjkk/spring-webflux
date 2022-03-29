package com.example.webflux.basic

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
@Primary
class BasicRepositoryImpl(
    private val repo: MutableList<Basic> = mutableListOf()

): BasicRepository {

    private val log = LoggerFactory.getLogger(javaClass)

    private fun <T> T?.getOrThrow(): T {
        return this ?: throw RuntimeException("Not Found Source")
    }

    companion object {
        var ID: Long = 0
    }

    override fun save(basic: Basic): Mono<Basic> {
        basic.id = ++ID
        repo.add(basic)
        return Mono.just(basic)
    }

    override fun findById(id: Long): Mono<Basic> {
        val basic = repo.find { it.id == id }.getOrThrow()
        return Mono.just(basic)
    }

    override fun findAll(): Mono<List<Basic>> {
        return Mono.just(getAllListDelay2Sec())
    }

    override fun findFluxAll(): Flux<Basic> {
        return Flux.fromIterable(repo)
    }

    override fun deleteById(id: Long): Mono<Basic> {
        val basic = repo.find { it.id == id }.getOrThrow()
        val new = repo.filterNot { it.id == basic.id }
        repo.clear()
        repo.addAll(new)
        return Mono.just(basic)
    }

    private fun getAllListDelay2Sec(): List<Basic> {
        log.info("load data...")
        Thread.sleep(2000)
        log.info("data : $repo")
        log.info("load data complete")
        return repo
    }
}