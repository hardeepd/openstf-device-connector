package uk.co.hd_tech.openstf;


import io.reactivex.Observable;
import se.vidstige.jadb.ConnectionToRemoteDeviceException;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbException;
import uk.co.hd_tech.openstf.client.STFClient;
import uk.co.hd_tech.openstf.client.STFService;
import uk.co.hd_tech.openstf.client.model.Device;
import uk.co.hd_tech.openstf.client.rest.AddUserDevicePayload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    public void status(String url, String token) {
        System.out.println("Getting status of OpenSTF server: " + url);

        STFClient stfClient = new STFClient(url, token);
        STFService stfService = stfClient.getStfService();

        stfService.getDeviceList()
                .subscribe(deviceListResponseResponse -> showDeviceUsageList(deviceListResponseResponse.getDevices()), this::showError);
    }

    public void connect(String url, String token) {
        System.out.println("Connecting to OpenSTF server: " + url);
        STFClient stfClient = new STFClient(url, token);
        STFService stfService = stfClient.getStfService();

        stfService.getDeviceList()
                .subscribe(
                        deviceListResponse -> {
                            List<Device> devices = deviceListResponse.getDevices();
                            List<String> availableDeviceSerialNumbers = getAvailableDeviceSerialNumbers(devices);

                            Observable.fromIterable(availableDeviceSerialNumbers)
                                    .subscribe(
                                            s -> {
                                                addDeviceToUser(stfService, s);
                                            }
                                    );
                        }
                );
    }

    public void disconnect(String url, String token) {
        System.out.println("Disconnecting from OpenSTF server: " + url);
        STFClient stfClient = new STFClient(url, token);
        STFService stfService = stfClient.getStfService();

        stfService.getUsersDevices()
                .subscribe(
                        deviceListResponse -> {
                            List<Device> devices = deviceListResponse.getDevices();

                            Observable.fromIterable(devices)
                                    .subscribe(
                                            device -> {
                                                String serial = device.getSerial();

                                                String deviceString = String.format("Disconnecting %s - %s : %s", device.getManufacturer(), device.getModel(), device.getSerial());
                                                System.out.println(deviceString);

                                                disconnectAdb(device.getRemoteConnectUrl());

                                                stfService.releaseRemoteConnection(serial)
                                                        .doOnNext(genericResponse -> System.out.println(genericResponse.getDescription() + ":" + serial))
                                                        .subscribe();

                                                stfService.releaseDevice(device.getSerial())
                                                        .doOnNext(genericResponse -> System.out.println(genericResponse.getDescription() + ":" + serial))
                                                        .subscribe();
                                            }
                                    );
                        }
                );
    }

    private void showError(Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
    }

    private void showDeviceUsageList(List<Device> devices) {
        System.out.println("Device status:");
        for (Device device : devices) {
            String deviceString = String.format("%s - %s : %s %s", device.getManufacturer(), device.getModel(), device.getSerial(), device.getPresent() ? "" : "not connected");
            System.out.println(deviceString);
            if (device.getUsing()) {
                System.out.println(String.format("In use by %s, %s", device.getOwner().getName(), device.getOwner().getEmail()));
            }
            System.out.println();
        }
    }

    private List<String> getAvailableDeviceSerialNumbers(List<Device> devices) {
        List<String> serialNumbers = new ArrayList<>();
        for (Device device : devices) {
            if (device.getPresent() && device.getOwner() == null) {
                serialNumbers.add(device.getSerial());

                System.out.println(device.getSerial());
            }
        }

        return serialNumbers;
    }

    private void addDeviceToUser(STFService stfService, String serialNumber) {
        AddUserDevicePayload payload = AddUserDevicePayload
                .builder()
                .serial(serialNumber)
                .timeout(Integer.MAX_VALUE)
                .build();

        stfService.addDeviceToUser(payload)
                .subscribe(
                        genericResponse -> {
                            System.out.println(genericResponse.getDescription() + ": " + serialNumber);
                            connectToDevice(stfService, serialNumber);
                        }
                );
    }

    private void connectToDevice(STFService stfService, String serialNumber) {
        stfService.connectToRemoteDevice(serialNumber)
                .subscribe(
                        remoteConnectUserDeviceResponse -> {
                            String remoteConnectUrl = remoteConnectUserDeviceResponse.getRemoteConnectUrl();
                            System.out.println("Connecting adb to " + remoteConnectUrl);
                            connectAdb(remoteConnectUrl);
                            System.out.println("Connected adb to " + remoteConnectUrl);
                        }
                );
    }

    private void connectAdb(String url) {
        try {
            String[] split = url.split(":");

            JadbConnection jadbConnection = new JadbConnection();
            jadbConnection.connectToTcpDevice(InetSocketAddress.createUnresolved(split[0], Integer.valueOf(split[1])));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JadbException e) {
            e.printStackTrace();
        } catch (ConnectionToRemoteDeviceException e) {
            e.printStackTrace();
        }
    }

    private void disconnectAdb(String url) {
        try {
            String[] split = url.split(":");

            JadbConnection jadbConnection = new JadbConnection();
            jadbConnection.disconnectFromTcpDevice(InetSocketAddress.createUnresolved(split[0], Integer.valueOf(split[1])));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JadbException e) {
            e.printStackTrace();
        } catch (ConnectionToRemoteDeviceException e) {
            e.printStackTrace();
        }
    }
}
