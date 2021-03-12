package Server;

import DataAccess.DataAccessException;
import Request.LoadReq;
import Result.LoadRes;
import Service.Load;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler extends PostRequestHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String inputStr = readInputFromJSON(exchange);
                LoadReq lReq = Serializer.deserialize(inputStr, LoadReq.class);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Load loadObj = new Load();
                LoadRes lRes = loadObj.load(lReq);
                OutputStream respBody = exchange.getResponseBody();
                String json = Serializer.serialize(lRes);
                writeToJSON(json, respBody);
                respBody.close();
            }
        } catch(IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }
}
