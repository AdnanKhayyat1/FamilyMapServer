package Server;

import DataAccess.DataAccessException;
import Result.PersonIDRes;
import Service.GetPersonID;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonIDHandler extends RequestHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().toLowerCase().equals("get")) {
            String urlPath = exchange.getRequestURI().toString();
            String personID = urlPath.substring(urlPath.indexOf('/', 2) + 1);
            GetPersonID getPerson = new GetPersonID();
            String token = exchange.getRequestHeaders().getFirst("Authorization");
            try {
                PersonIDRes pRes = getPerson.getPersonById(personID, token);
                if(pRes.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                String response = Serializer.serialize(pRes);
                OutputStream respBody = exchange.getResponseBody();
                writeToJSON(response, respBody);
                respBody.close();

            } catch (DataAccessException e) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                e.printStackTrace();
            }

        }

    }
}
