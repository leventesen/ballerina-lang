import ballerina/http;
import ballerina/config;

listener http:Listener backendEP = new(config:getAsInt("backendEP.port"));

@http:ServiceConfig {
    basePath: config:getAsString("hello.basePath")
}
service hello on backendEP{

    @http:ResourceConfig {
        methods:["GET"],
        path:"/"
    }
    resource function sayHello (http:Caller caller, http:Request request) {
        http:Response response = new;
        response.setTextPayload("Hello World!!!");
        _ = caller -> respond(response);
    }
}
