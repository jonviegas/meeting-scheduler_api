# :necktie: Meeting Scheduler API
- - -

## :book: Introduction
This REST API provides a system to manage meetings, being able to register a room by its date, start time, and end time. It also updates meeting information after its creation and deletes it if desired.

Check the frontend repository of this app *[here](https://github.com/jonviegas/meeting-scheduler-frontend)*.

- - -
## :cloud: Cloud

The project is also hosted on *Heroku*, [click here](https://meeting-scheduler-system.herokuapp.com/rooms) to access it.
- - -
## :green_book: Documentation

[Click here](https://meeting-scheduler-system.herokuapp.com/swagger-ui.html) to access the complete documentation made with *swagger*.
- - -

## :computer: Usage

- ### **GET**
    ```/rooms```
    Returns all rooms.
\
    ```/rooms/{id}```
    Returns a room, if exists, by its id.
\
    ```/rooms/search/{roomName}```
    Returns a room, if exists, by its name.

- ### **POST**
    ```/rooms```
    Allows you to register a new room.
\

    **Example:**
    ``` JSON
    {
      "name": "Docker Basics",
      "date": "10/06/2022",
      "startHour": "10:00",
      "endHour": "12:00"
    }
    ```

- ### **PUT**
     ```/rooms/{id}```
    Allows you to update a room, if exists, by id.
\

    **Example:**
    ``` JSON
    {
      "name": "Docker-Compose Basics",
      "date": "10/06/2022",
      "startHour": "11:00",
      "endHour": "14:00"
    }
    ```

- ### **DELETE**
    ```/rooms/{id}```
    Deletes a room, if exists, by its id.
