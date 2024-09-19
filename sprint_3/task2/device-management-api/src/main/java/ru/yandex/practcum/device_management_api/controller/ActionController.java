package ru.yandex.practcum.device_management_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practcum.device_management_api.model.rest.*;
import ru.yandex.practcum.device_management_api.service.DeviceActionService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class ActionController {

    private final DeviceActionService service;

    @PostMapping("/device/action")
    public CompletableFuture<ResponseEntity<DeviceActionResponse>> action(@RequestBody DeviceActionRequest request) {
        return service.action(request)
                .thenApply(ResponseEntity::ok);
    }
}
