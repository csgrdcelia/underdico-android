package com.esgi.project.underdico.services;

import android.util.Log;

import com.esgi.project.underdico.models.Expression;
import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.utils.Session;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import okhttp3.OkHttpClient;

public class GameSocket {

    private Socket socket;
    private Room room;
    private String token;

    private final String log = "LOGMSG";

    public GameSocket(Room room, String token) {
        try {
            this.room = room;
            this.token = token;
            this.socket = IO.socket("https://underdico.hdaroit.fr/", getOptions());
        } catch (URISyntaxException e) {
            Log.e(log, "URISyntaxException : " + e.getMessage());
        }
    }

    public void connect() {
        listenEvents();
        socket.connect();
    }

    private void listenEvents() {
        socket.io().on(Manager.EVENT_TRANSPORT, eventTransportListener);
        socket.on(Socket.EVENT_CONNECT, eventConnectListener);
        socket.on(Manager.EVENT_ERROR, eventErrorListener);
        socket.on(Manager.EVENT_CONNECT_ERROR, eventConnectErrorListener);

        socket.on("newPlayer", newPlayerListener);
        socket.on("playerRemoved", playerRemovedListener);
        socket.on("error", errorListener);
        socket.on("roomStarted", roomStartedListener);
        socket.on("newRound", newRoundListener);
    }

    public void joinRoom() {
        try {
            Log.e(log, "joinRoom : about to send event");

            JSONObject obj = new JSONObject();
            obj.put("roomId", room.getId());
            socket.emit("joinRoom", obj, (Ack) args -> {
                Log.e(log, "joinRoom response");
            });
        } catch (JSONException e) {
            Log.e(log, "joinRoom : json error");
        }
    }

    public void startRoom() {
        try {
            Log.e(log, "startRoom : about to send event");

            JSONObject obj = new JSONObject();
            obj.put("roomId", room.getId());
            socket.emit("startRoom", obj, (Ack) args -> {
                Log.e(log, "startRoom response");
            });
        } catch (JSONException e) {
            Log.e(log, "startRoom : json error");
        }
    }

    public void leaveRoom() {
        try {
            Log.e(log, "leaveRoom : about to send event");

            JSONObject obj = new JSONObject();
            obj.put("roomId", room.getId());
            socket.emit("leaveRoom", obj, (Ack) args -> {
                Log.e(log, "leaveRoom response");
            });
        } catch (JSONException e) {
            Log.e(log, "leaveRoom : json error");
        }
    }

    /**
     * Listeners
     *
     */
    private Emitter.Listener eventTransportListener = args -> {
        Transport transport = (Transport) args[0];

        transport.on(Transport.EVENT_REQUEST_HEADERS, args1 -> {
            Log.e(log, "EVENT_REQUEST_HEADERS : listen");
            @SuppressWarnings("unchecked")
            Map<String, List<String>> headers = (Map<String, List<String>>) args1[0];
            headers.put("Authorization", Arrays.asList(token));

        }).on(Transport.EVENT_RESPONSE_HEADERS, args12 -> {
            Map<String, List<String>> map = (Map<String, List<String>>) args12[0];
            String responseCode = map.values().toArray()[0].toString();
            Log.e(log, "EVENT_RESPONSE_HEADERS : " + responseCode);
        });
    };

    private Emitter.Listener eventConnectListener = args -> {
        Log.e(log, "EVENT_CONNECT : " + (socket.connected() == true ? "connected to socket" : "not connected to socket"));
        joinRoom();
        startRoom();
    };

    private Emitter.Listener roomStartedListener = args -> {
        Log.e(log, "roomStarted");
    };

    private Emitter.Listener newRoundListener = args -> {
        Expression expression = new Gson().fromJson((String) args[0], Expression.class);
        JSONObject obj = (JSONObject) args[0];
        Log.e(log, "newRound");
    };

    private Emitter.Listener errorListener = args -> {
        Log.e(log, "ERROR");
    };

    private Emitter.Listener eventErrorListener = args -> {
        Log.e(log, "EVENT_ERROR");
    };

    private Emitter.Listener eventConnectErrorListener = args -> {
        Log.e(log, "EVENT_CONNECT_ERROR");
    };

    private Emitter.Listener newPlayerListener = args -> {
        Log.e(log, "newPlayer");
    };


    private Emitter.Listener playerRemovedListener = args -> {
        Log.e(log, "playerRemoved");
    };

    private Emitter.Listener eventListener = args -> {

    };

    /**
     * SSL trust
     *
     */
    private IO.Options getOptions() {
        IO.Options options = new IO.Options();
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();

            IO.setDefaultOkHttpWebSocketFactory(okHttpClient);
            IO.setDefaultOkHttpCallFactory(okHttpClient);
            options.callFactory = okHttpClient;
            options.webSocketFactory = okHttpClient;
        } catch (Exception e) {
            Log.e(log, "getOptions : fail");
            throw new RuntimeException(e);
        }
        return options;
    }

    public Socket getSocket() {
        return socket;
    }
}
