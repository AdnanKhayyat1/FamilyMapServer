package Server;

import DataAccess.DataAccessException;
import Request.LoadReq;
import Request.LoginReq;
import Result.LoginRes;
import Service.Login;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoginHandler extends PostRequestHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            if(exchange.getRequestMethod().toLowerCase().equals("post")){
                String inputStr = readInputFromJSON(exchange);
                LoginReq lReq = Serializer.deserialize(inputStr, LoginReq.class);
                Login logObj = new Login();
                LoginRes lRes = logObj.login(lReq);
                if(lRes.isSuccess() == false) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                OutputStream respBody = exchange.getResponseBody();
                String json = Serializer.serialize(lRes);
                writeToJSON(json, respBody);
                System.out.println(exchange.getResponseHeaders());
                respBody.close();
            }
        }catch(IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }
}
