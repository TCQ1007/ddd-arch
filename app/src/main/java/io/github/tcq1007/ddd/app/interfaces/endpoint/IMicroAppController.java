package io.github.tcq1007.ddd.app.interfaces.endpoint;


import io.github.tcq1007.ddd.app.interfaces.model.MicroAppQueryRequestDTO;
import io.github.tcq1007.ddd.app.interfaces.model.MicroAppQueryResponseDTO;
import io.github.tcq1007.ddd.core.request.BasePageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "MicroApp", description = "MicroApp Configuration APIs")
@RequestMapping("/api/microapp")
public interface IMicroAppController {

    @Operation(summary = "Query MicroApp list",
            description = "Gets paginated list of FAQ items")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MicroAppQueryRequestDTO.class))})
    @PostMapping(value = "/query-list/v1", consumes = {"application/json"})
    ResponseEntity<List<MicroAppQueryResponseDTO>> query(
            @Parameter(description = "MicroApp query request")
            @Valid @RequestBody MicroAppQueryRequestDTO requestDTO
    );

    @Operation(summary = "Query MicroApp list",
            description = "Gets paginated list of FAQ items")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "successful operation",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = MicroAppQueryRequestDTO.class))})
    @PostMapping(value = "/page-query-list/v1", consumes = {"application/json"})
    ResponseEntity<PagedModel<MicroAppQueryResponseDTO>> pageQuery(
            @Parameter(description = "MicroApp query request")
            @Valid @RequestBody BasePageRequest<MicroAppQueryRequestDTO> requestDTO
    );
}
