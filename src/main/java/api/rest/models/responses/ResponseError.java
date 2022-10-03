package api.rest.models.responses;

import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;

import api.shared.dtos.FieldError;
import lombok.Data;

@Data
public class ResponseError {
    private String message;
    private Collection<FieldError> errors;

    public ResponseError(String message, Collection<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> validation) {
        List<FieldError> errors = validation
                .stream()
                .map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());

        String message = "Validation Error";
        var responseError = new ResponseError(message, errors);

        return responseError;

    }

    public Response withStatusCode(int code) {
        return Response.status(code).entity(getErrors()).build();
    }

}
