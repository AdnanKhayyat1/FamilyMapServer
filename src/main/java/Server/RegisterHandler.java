package Server;

import DataAccess.DataAccessException;
import Request.RegisterReq;
import Result.LoginRes;
import Result.RegisterRes;
import Service.Register;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class RegisterHandler extends PostRequestHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            if(exchange.getRequestMethod().toLowerCase().equals("post")){
                String inputStr = readInputFromJSON(exchange);
                RegisterReq rReq = Serializer.deserialize(inputStr, RegisterReq.class);
                Register reg = new Register();
                RegisterRes rRes = reg.register(rReq);
                if(!rRes.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                String json = Serializer.serialize(rRes);
                writeToJSON(json, respBody);
                respBody.close();
            }
        }catch(IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }

    }


}
