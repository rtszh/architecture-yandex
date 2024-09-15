package ru.yandex.practcum.device_management_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practcum.device_management_api.model.rest.*;
import ru.yandex.practcum.device_management_api.service.DeviceTelemetryService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class TelemetryController {
    private final DeviceTelemetryService service;

    @PostMapping("/device/telemetry/request/all")
    public CompletableFuture<ResponseEntity<DeviceResponse>> deviceTelemetryRequestAll(@RequestBody DeviceTelemetryRequest request) {
        return service.requestTelemetry(request, "ALL")
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/device/telemetry/request/latest")
    public CompletableFuture<ResponseEntity<DeviceResponse>> deviceTelemetryRequestLatest(@RequestBody DeviceTelemetryRequest request) {
        return service.requestTelemetry(request, "LATEST")
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/device/telemetry/data/{messageId}")
    public ResponseEntity<DeviceTelemetryResponse> deviceTelemetryData(@PathVariable("messageId") String messageId) {
        return ResponseEntity.ok(service.findRegisterDataByMessageId(messageId));
    }
}
