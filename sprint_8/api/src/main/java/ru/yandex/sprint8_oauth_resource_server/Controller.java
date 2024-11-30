/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.yandex.sprint8_oauth_resource_server;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

    @GetMapping("/reports")
    public ResponseEntity<String> reports(Authentication authentication) {
        return ResponseEntity.ok(
                "User: %s, Report: generatedReport".formatted(extractPreferredUsername(authentication))
        );
    }

    private String extractPreferredUsername(Authentication authentication) {
        final Jwt jwt = (Jwt) authentication.getPrincipal();

        return (String) jwt.getClaims().get("preferred_username");
    }
}
