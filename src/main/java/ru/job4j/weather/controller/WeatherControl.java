package ru.job4j.weather.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.job4j.weather.domain.Weather;
import ru.job4j.weather.service.WeatherService;

import java.time.Duration;

@RestController
@RequestMapping("/weather")
public class WeatherControl {

    private final WeatherService weatherService;

    public WeatherControl(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{id}")
    public Mono<Weather> findById(@PathVariable int id) {
        return weatherService.findById(id);
    }

    @GetMapping(value = "/hottest")
    public Mono<Weather> findHottest() {
        return weatherService.findHottest();
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> findAll() {
        Flux<Weather> data = weatherService.findAll();
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(3));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }

    @GetMapping(value = "/cityGreatThen/{value}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Weather> findByTemperature(@PathVariable float value) {
        Flux<Weather> data = weatherService.findGreatThen(value);
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(3));
        return Flux.zip(data, delay).map(Tuple2::getT1);
    }

}
