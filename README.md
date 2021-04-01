# WebsocketTestServer
A Spring Boot web app to send websocket messages.

Useful to act as a websocket server for mobile apps and to send them websocket messages via browser.

Just run with Gradle:

    ./gradlew bootRun

and open in browser:

http://localhost:8080/ws/ui

Then point your app to the server:

http://your-ip-here:8080/ws/service

Type something in the textfield in the browser page and press Submit: all clients connected to the server will receive the message.
