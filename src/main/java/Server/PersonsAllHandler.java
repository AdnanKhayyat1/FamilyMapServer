package Server;

import DataAccess.DataAccessException;
import Result.PersonAllRes;
import Result.PersonIDRes;
import Service.GetPersonID;
import Service.GetPersons;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonsAllHandler extends RequestHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toLowerCase().equals("get")) {
            GetPersons getAllPersons = new GetPersons();
            String token = exchange.getRequestHeaders().getFirst("Authorization");
            try {
                PersonAllRes pRes = getAllPersons.getPersons(token);
                if(pRes.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                String response = Serializer.serialize(pRes);
                OutputStream respBody = exchange.getResponseBody();
                writeToJSON(response, respBody);
                System.out.println(exchange.getResponseHeaders());
                respBody.close();

            } catch (DataAccessException e) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                e.printStackTrace();
            }

        }
    }
}
