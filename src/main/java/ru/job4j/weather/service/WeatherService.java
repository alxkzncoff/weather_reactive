package ru.job4j.weather.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.job4j.weather.domain.Weather;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();

    public WeatherService() {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 25));
        weathers.put(3, new Weather(3, "Bryansk", 30));
        weathers.put(4, new Weather(4, "Smolensk", 15));
        weathers.put(5, new Weather(5, "Kiev", 10));
        weathers.put(6, new Weather(6, "Minsk", 15));
    }

    public Mono<Weather> findById(int id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Mono<Weather> findHottest() {
        return Mono.justOrEmpty(weathers.values()
                .stream()
                .max(Comparator.comparing(Weather::getTemperature)));
    }

    public Flux<Weather> findGreatThen(float value) {
        return Flux.fromIterable(weathers.values()
                .stream()
                .filter(w -> w.getTemperature() > value)
                .collect(Collectors.toList()));
    }

    public Flux<Weather> findAll() {
        System.out.println(weathers.values());
        return Flux.fromIterable(weathers.values());
    }
}
