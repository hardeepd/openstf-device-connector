package uk.co.hd_tech.openstf.client;

import io.reactivex.Flowable;
import retrofit2.http.*;
import uk.co.hd_tech.openstf.client.rest.*;

public interface STFService {

    /**
     * The devices endpoint return list of all the CommandHandler devices including Disconnected and Offline
     *
     * @return
     */
    @GET("devices")
    Flowable<DeviceListResponse> getDeviceList();

    /**
     * The device enpoint return information about a single device
     *
     * @param serial
     * @return
     */
    @GET("devices/{serial}")
    Flowable<DeviceResponse> getDeviceDetails(@Path("serial") String serial);

    /**
     * The User Profile endpoint returns information about current authorized user
     *
     * @return
     */
    @GET("user")
    Flowable<Object> getUser();

    /**
     * The User Devices endpoint returns device list owner by current authorized user
     *
     * @return
     */
    @GET("user/devices")
    Flowable<DeviceListResponse> getUsersDevices();

    /**
     * The User Devices endpoint will request stf server for a new device
     *
     * @param payload
     * @return
     */
    @POST("user/devices")
    Flowable<GenericResponse> addDeviceToUser(@Body AddUserDevicePayload payload);

    /**
     * The devices enpoint return information about device owned by user
     *
     * @param serial
     * @return
     */
    @GET("user/devices/{serial}")
    Flowable<DeviceResponse> getUserDeviceInfo(@Path("serial") String serial);

    /**
     * The User Devices endpoint will request for device release from stf server.
     * It will return request accepted if device is being used by current user
     *
     * @param serial
     * @return
     */
    @DELETE("user/devices/{serial}")
    Flowable<GenericResponse> releaseDevice(@Path("serial") String serial);

    /**
     * The device connect endpoint will request stf server to connect remotely
     *
     * @param serial
     * @return
     */
    @POST("user/devices/{serial}/remoteConnect")
    Flowable<RemoteConnectUserDeviceResponse> connectToRemoteDevice(@Path("serial") String serial);

    /**
     * The device connect endpoint will request stf server to disconnect remotely
     *
     * @param serial
     * @return
     */
    @DELETE("user/devices/{serial}/remoteConnect")
    Flowable<GenericResponse> releaseRemoteConnection(@Path("serial") String serial);

    /**
     * The Access Tokens endpoints returns titles of all the valid access tokens
     *
     * @return
     */
    @GET("user/accessTokens")
    Flowable<AccessTokensResponse> getAccessTokens();
}
