package itopia.resolar.application.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Resolar API")
                .version("1.0.0")
                .description("학습 자료 관리 시스템 API 문서<br>" +
                        "- 사용자 인증 및 회원가입<br>" +
                        "- 과목(Subject) 관리<br>" +
                        "- 페이지(학습자료) 관리");

        String jwtSchemeName = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        var local = new Server().url("http://localhost:8080");
        var server = new Server().url("https://37703b8a36c1.ngrok-free.app");

        return new OpenAPI()
                .info(info)
                .servers(List.of(local, server))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
