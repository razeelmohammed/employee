package com.codingtest.controller.employee;

import com.codingtest.dto.employee.EmployeeDTO;
import com.codingtest.helper.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.codingtest.constant.SystemConstant.API_RESPONSE_DESCRIPTION;

@RequestMapping("/employee")
public interface IEmployeeControllerOpenApi3 {


    @Operation(summary = "Update the salaries of employees based on location")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = API_RESPONSE_DESCRIPTION,
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class))))})
    @PutMapping(value = "/place/{place}/salary/{percentage}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse> updateSalaryByPlace(
            @Parameter(description = "Bangalore or Delhi")
            @PathVariable(name = "place") String place,
            @Parameter(description = "Any relevant number eg 5,10 etc")
            @PathVariable(name = "percentage") Double percentage);

    @Operation(summary = "Find the total salary based on input parameters.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = API_RESPONSE_DESCRIPTION,
            content = @Content(schema = @Schema(implementation = Double.class)))})
    @GetMapping(value = "/{param}/{value}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse> getTotalSalaryBasedOnParam(
            @Parameter(description = "BU - for business unit ;PL - for place; SU -for supervisor (number)")
            @PathVariable(name = "param") String param,
            @Parameter(description = "Search string(Exact Match) (BU eg -Technology) (PL eg Bangalore) (SU eg - 1 or 2)")
            @PathVariable(name = "value") String value);

    @Operation(summary = "get employees based on place.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = API_RESPONSE_DESCRIPTION,
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class))))})
    @GetMapping(value = "/{place}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse> getEmployeesBasedOnPlace(
            @Parameter(description = "Enter the place name to get the list of employees (Eg: Bangalore,Delhi )")
            @PathVariable(name = "place") String place);

    @Operation(summary = "Find the salary ranges based on title.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = API_RESPONSE_DESCRIPTION,
            content = @Content(schema = @Schema(implementation = String.class)))})
    @GetMapping(value = "/range/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse> getSalaryRangeBasedOnTitle(
            @Parameter(description = "Eg: SAL 1 , SAL 2 etc")
            @PathVariable(name = "title") String title);

    @Operation(summary = "Get subordiates of a supervisor.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = API_RESPONSE_DESCRIPTION,
            content = @Content(schema = @Schema(implementation = EmployeeDTO.class)))})
    @GetMapping(value = "/getsubordiates/{supervisorid}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StandardResponse> getReportingStructure(
            @Parameter(description = "Supervisor Id")
            @PathVariable(name = "supervisorid") Integer supervisorid);

}
