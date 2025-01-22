package response;

import dto.BaseDto;

import java.util.List;

public class RestApiAppResponse<T extends BaseDto> {

    // STATES
    public final boolean status;
    public final List<T> data;
    public final String message;

    // CONSTRUCTORS
    public RestApiAppResponse(boolean status, List<T> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    // FUNCTIONS
    public static RestApiAppResponse<?> ofError(String message) {
        return new RestApiAppResponse<>(false, null, message);
    }

    public static <A extends BaseDto> RestApiAppResponse<A> ofSuccess(List<A> list) {
        return new RestApiAppResponse<A>(true, list, null);
    }
}