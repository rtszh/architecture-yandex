package ru.yandex.practcum.device_management_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practcum.device_management_api.model.kafka.DeviceRegisterDataResponseMessage;
import ru.yandex.practcum.device_management_api.model.redis.DeviceRegisterData;
import ru.yandex.practcum.device_management_api.model.rest.DeviceRegisterDataResponse;

@Mapper
public interface DeviceRegisterDataMapper {

    @Mapping(target = "deviceActions", source = "deviceActions")
    @Mapping(target = "deviceName", source = "name")
    DeviceRegisterData toDeviceRegisterDataResponse(DeviceRegisterDataResponseMessage message);
    @Mapping(target = "deviceActions", source = "deviceActions")
    DeviceRegisterDataResponse toDeviceRegisterDataResponse(DeviceRegisterData data);
}
