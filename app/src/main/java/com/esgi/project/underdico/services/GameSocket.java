package com.esgi.project.underdico.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.esgi.project.underdico.models.Room;
import com.esgi.project.underdico.utils.Session;
import com.esgi.project.underdico.views.game.GameView;
import com.esgi.project.underdico.views.main.MainView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Transport;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class GameSocket {
    private Activity activity;
    private GameView view;
    private Socket socket;
    private Room room;
    private final String log = "LOGMSG";

    public GameSocket(GameView view, Activity activity, Room room) {
        this.view = view;
        this.activity = activity;
        this.room = room;

        try {
            SSLContext mySSLContext = SSLContext.getInstance("TLS");
            mySSLContext.init(null, trustAllCerts, null);

            IO.Options opts = new IO.Options();
            opts.sslContext = mySSLContext;
            socket = IO.socket("https://underdico.hdaroit.fr/", opts);

        } catch (Exception e) {
            view.showError("error");
        }
    }

    public void connect() {
        socket.io().on(Manager.EVENT_TRANSPORT, onTransport);
        socket.io().on(Manager.EVENT_ERROR, onError);
        socket.io().on(Manager.EVENT_CONNECT_ERROR, onError);
        socket.io().on(Manager.EVENT_RECONNECT_ERROR, onError);
        socket.on("newPlayer", onNewPlayer);
        socket.on("roomStarted", onRoomStarted);
        socket.on("newRound", onNewRound);

        Log.e(log, "about to connect");
        socket.connect();
    }

    public void play(String answer) {
        socket.emit("play", "");
    }

    public void joinRoom() {
        Log.e(log, "about to send joinRoom event");
        socket.emit("joinRoom", room.getId());
    }

    private Emitter.Listener onTransport = args -> {
        Log.e(log, "in onTransport Listener");
        Transport transport = (Transport) args[0];

        transport.on(Transport.EVENT_REQUEST_HEADERS, args1 -> {

            @SuppressWarnings("unchecked")
            Map<String, List<String>> headers = (Map<String, List<String>>) args1[0];
            String bearer = Session.getCurrentToken().getValue();
            headers.put("Authorization", Arrays.asList(bearer));

        }).on(Transport.EVENT_RESPONSE_HEADERS, args12 -> {

            Log.e(log, "response header event");
        });
    };

    private Emitter.Listener onError = args -> activity.runOnUiThread(() -> {
        Log.e(log, "on error");
        JSONObject data = (JSONObject) args[0];
    });

    private Emitter.Listener onNewPlayer = args -> activity.runOnUiThread(() -> {
        Log.e(log, "on new player");
        JSONObject data = (JSONObject) args[0];
    });

    private Emitter.Listener onRoomStarted = args -> activity.runOnUiThread(() -> {
        Log.e(log, "on room started");
        JSONObject data = (JSONObject) args[0];
    });

    private Emitter.Listener onNewRound = args -> activity.runOnUiThread(() -> {
        Log.e(log, "on new round");
        JSONObject data = (JSONObject) args[0];
    });

    private final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        public void checkClientTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }
    }};

}
