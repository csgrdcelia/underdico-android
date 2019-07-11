package com.esgi.project.underdico.services;

import android.app.Activity;
import android.util.Log;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.models.Round;
import com.esgi.project.underdico.models.User;
import com.esgi.project.underdico.presenters.GamePresenter;
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
    private Activity activity;
    private GamePresenter presenter;
    private Socket socket;
    private Room room;
    private String token;

    private final String log = "LOGMSG";

    public GameSocket(Activity activity, GamePresenter presenter, Room room, String token) {
        try {
            this.activity = activity;
            this.presenter = presenter;
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

    public void disconnect() {
        if(socket.connected()) {
            socket.disconnect();
        }
    }

    private void listenEvents() {
        socket.io().on(Manager.EVENT_TRANSPORT, eventTransportListener);
        socket.on(Socket.EVENT_CONNECT, eventConnectListener);
        socket.on(Manager.EVENT_ERROR, eventErrorListener);
        socket.on(Socket.EVENT_DISCONNECT, eventDisconnectListener);
        socket.on(Manager.EVENT_CONNECT_ERROR, eventConnectErrorListener);
        socket.on(Manager.EVENT_CLOSE, eventCloseListener);

        socket.on("newPlayer", newPlayerListener);
        socket.on("playerRemoved", playerRemovedListener);
        socket.on("gameError", errorListener);
        socket.on("roomStarted", roomStartedListener);
        socket.on("newRound", newRoundListener);
        socket.on("goodProposal", goodProposalListener);
        socket.on("wrongProposal", wrongProposalListener);
        socket.on("timeout", timeoutListener);
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
            socket.emit("startRoom", obj);
        } catch (JSONException e) {
            Log.e(log, "startRoom : json error");
        }
    }

    public void play(String answer) {
        try {

        Log.e(log, "play : about to send event");

        JSONObject obj = new JSONObject();
        obj.put("roomId", room.getId());
        obj.put("proposal", answer);

        socket.emit("play", obj);

        } catch (JSONException e) {
            Log.e(log, "play : json error while sending");
        }
    }

    public void leaveRoom() {
        try {
            Log.e(log, "leaveRoom : about to send event");

            JSONObject obj = new JSONObject();
            obj.put("roomId", room.getId());
            socket.emit("leaveRoom", obj);

        } catch (JSONException e) {
            Log.e(log, "leaveRoom : json error while sending");
        }
    }

    /**
     * Listeners
     *
     */
    private Emitter.Listener eventTransportListener = args -> {
        Transport transport = (Transport) args[0];

        transport.on(Transport.EVENT_REQUEST_HEADERS, args1 -> {
            @SuppressWarnings("unchecked")
            Map<String, List<String>> headers = (Map<String, List<String>>) args1[0];
            headers.put("Authorization", Arrays.asList(token));
        });
    };

    private Emitter.Listener eventConnectListener = args -> {

        Log.e(log, "EVENT_CONNECT : " + (socket.connected() == true ? "connected to socket" : "not connected to socket"));
    };

    private Emitter.Listener eventDisconnectListener = args -> {

        Log.e(log, "EVENT_DISCONNECT : " + (socket.connected() == true ? "connected to socket" : "not connected to socket"));
    };

    private Emitter.Listener eventCloseListener = args -> {
        Log.e(log, "EVENT_CLOSE : " + (socket.connected() == true ? "connected to socket" : "not connected to socket"));
    };


    private Emitter.Listener roomStartedListener = args -> activity.runOnUiThread(() -> {
        Log.e(log, "roomStarted");
        presenter.roomIsStarted();
    });

    private Emitter.Listener newRoundListener = args -> activity.runOnUiThread(() -> {
        Log.e(log, "newRound");
        Round round = new Gson().fromJson(((JSONObject) args[0]).toString(), Round.class);
        presenter.newRound(round);
    });

    private Emitter.Listener goodProposalListener = args -> activity.runOnUiThread(() -> {
        Log.e(log, "goodProposal");
        try {
            JSONObject obj = (JSONObject) args[0];
            String playerId = obj.getString("playerId");
            presenter.proposalResult(true, playerId, null);
        } catch (JSONException e) {
            Log.e(log, "goodProposal : json error while retrieving");
        }
    });

    private Emitter.Listener wrongProposalListener = args -> activity.runOnUiThread(() -> {
        Log.e(log, "wrongProposal");
        try {
            JSONObject obj = (JSONObject) args[0];
            String playerId = obj.getString("playerId");
            String nextPlayerId = obj.getString("playerId");
            presenter.proposalResult(false, playerId, nextPlayerId);
        } catch (JSONException e) {
            Log.e(log, "wrongProposal : json error while retrieving");
        }
    });

    private Emitter.Listener timeoutListener = args -> activity.runOnUiThread(() -> {
        Log.e(log, "timeout");
        try {
            JSONObject obj = (JSONObject) args[0];
            String playerId = obj.getString("playerId");
            String nextPlayerId = obj.getString("nextPlayerId");
            presenter.timeout(playerId, nextPlayerId);
        } catch (JSONException e) {
            Log.e(log, "timeout : json error while retrieving");
        }
    });

    private Emitter.Listener errorListener = args -> {
        Log.e(log, "ERROR");
    };

    private Emitter.Listener eventErrorListener = args -> {
        Log.e(log, "EVENT_ERROR");
    };

    private Emitter.Listener eventConnectErrorListener = args -> {
        Log.e(log, "EVENT_CONNECT_ERROR");
    };

    private Emitter.Listener newPlayerListener = args -> activity.runOnUiThread(() -> {
        Log.e(log, "newPlayer");
        User user = new Gson().fromJson(((JSONObject) args[0]).toString(), User.class);
        presenter.displayNewUser(user);
    });


    private Emitter.Listener playerRemovedListener = args -> activity.runOnUiThread(() -> {
        Log.e(log, "playerRemoved");
        User user = new Gson().fromJson(((JSONObject) args[0]).toString(), User.class);
        presenter.removeUser(user);
    });

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
            Log.e(log, "ssl conf : fail");
            throw new RuntimeException(e);
        }
        return options;
    }

}
