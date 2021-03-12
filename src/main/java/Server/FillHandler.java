package Server;

import DataAccess.DataAccessException;
import Request.FillReq;
import Request.LoadReq;
import Result.FillRes;
import Service.Fill;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler extends PostRequestHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().toLowerCase().equals("post")){
            String urlPath = exchange.getRequestURI().toString();
            String[] params = urlPath.split("/");
            String username = params[2];
            FillRes fRes;
            int generations = 4;
            try {
                if(checkUserNameExists(username)){
                    if(params.length == 4){
                        generations = Integer.parseInt(params[3]);
                        if(generations < 0){
                            throw new DataAccessException("error: please enter a valid generation");
                        }
                    }
                    FillReq fReq = new FillReq(username, generations);
                    Fill filler = new Fill();
                    fRes = filler.fill(fReq);
                    if(fRes.isSuccess() == false) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                    fRes = new FillRes("Username is not in database error", false);
                }
                OutputStream respBody = exchange.getResponseBody();
                String json = Serializer.serialize(fRes);
                writeToJSON(json, respBody);
                respBody.close();
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
