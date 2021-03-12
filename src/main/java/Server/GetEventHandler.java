package Server;

import DataAccess.DataAccessException;
import Result.EventAllRes;
import Result.EventIDRes;
import Service.GetEventID;
import Service.GetEvents;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

public class GetEventHandler extends RequestHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().toLowerCase().equals("get")){
            String urlPath = exchange.getRequestURI().toString();
            String[] params = urlPath.split("/");
            String eventID = null;
            String token = exchange.getRequestHeaders().getFirst("Authorization");
            try {
                OutputStream respBody = exchange.getResponseBody();
                String response = null;
                if (params.length == 2) {
                    //Get all events
                    GetEvents allEvents = new GetEvents();
                    EventAllRes eAllRes = allEvents.getEvents(token);
                    if(!eAllRes.isSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    response = Serializer.serialize(eAllRes);
                } else {
                    eventID =  params[2];
                    //Get certain event
                    GetEventID eventGetter = new GetEventID();
                    EventIDRes idRes = eventGetter.getEventByID(token, eventID);
                    if(!idRes.isSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    response = Serializer.serialize(idRes);
                }
                writeToJSON(response, respBody);
                respBody.close();
            } catch (DataAccessException e) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                e.printStackTrace();
            }
        }
    }
}
