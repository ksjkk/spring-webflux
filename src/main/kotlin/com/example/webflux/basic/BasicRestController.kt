package com.example.webflux.basic

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
class BasicRestController(
    private val basicService: BasicService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping(value = ["/"])
    fun basic() = Flux.just("Spring","WebFlux")

    @PostMapping(value = ["/basic"])
    fun save(@RequestBody basic: Basic) = basicService.save(basic).wrapLog()

    @GetMapping(value = ["/basic/{id}"])
    fun findById(@PathVariable id: Long) = basicService.findById(id).wrapLog()

    @GetMapping(value = ["/basic"])
    fun findAll() = basicService.findAll().wrapLog()

    @GetMapping(value = ["/basics"])
    fun findFluxAll() = basicService.findFluxAll().wrapLog()

    @DeleteMapping(value = ["/basic/{id}"])
    fun deleteById(@PathVariable id: Long) = basicService.deleteById(id).wrapLog()

    private fun <T> T.wrapLog(): T {
        val result: T = this
        when(result){
            is Mono<*> -> log.info("mono result : ${result.block()}")
            is Flux<*> -> log.info("flux result : ${result.collectList().block()}")
            else -> log.info("result : $result")
        }
        return result
    }
}