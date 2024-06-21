package study_dashboard.dashboard.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import study_dashboard.dashboard.Dto.CommonResponse;

public class ResponseUtils {

    public static <T> ResponseEntity<CommonResponse<T>> buildResponse(T data, String message, HttpStatus status) {
        CommonResponse<T> response = new CommonResponse<>(status.value(), message, data);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<CommonResponse<T>> buildSuccessResponse(T data) {
        return buildResponse(data, "Success", HttpStatus.OK);
    }

    public static <T> ResponseEntity<CommonResponse<T>> buildCreatedResponse(T data) {
        return buildResponse(data, "Resource created successfully", HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<CommonResponse<T>> buildErrorResponse(String message, HttpStatus status) {
        return buildResponse(null, message, status);
    }
}