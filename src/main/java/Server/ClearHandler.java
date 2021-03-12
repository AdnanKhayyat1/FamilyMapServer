package Server;

import DataAccess.DataAccessException;
import Result.ClearRes;
import Service.Clear;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler extends RequestHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            Clear clear = new Clear();
            try {
                ClearRes cr = clear.clear();
                if(cr.isSuccess())
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                Serializer sr = new Serializer();
                String json = sr.serialize(cr);
                writeToJSON(json, respBody);
                respBody.close();

            } catch (DataAccessException e) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                e.printStackTrace();
            }
        }
    }
}
